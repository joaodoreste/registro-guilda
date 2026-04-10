package br.com.infnet.registroguilda.busca.service;

import br.com.infnet.registroguilda.busca.dto.BucketResponse;
import br.com.infnet.registroguilda.busca.dto.FaixaPrecoResponse;
import br.com.infnet.registroguilda.busca.dto.PrecoMedioResponse;
import br.com.infnet.registroguilda.busca.dto.ProdutoResponse;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoBuscaService {

    private static final String INDEX = "guilda_loja";

    private final ElasticsearchClient client;

    public List<ProdutoResponse> buscarPorNome(String termo) throws IOException {
        SearchResponse<ProdutoResponse> busca = client.search(s -> s
                .index(INDEX)
                .query(q -> q.match(m -> m.field("nome").query(termo))), ProdutoResponse.class);

        return extrairProdutos(busca);
    }

    public List<ProdutoResponse> buscarPorDescricao(String termo) throws IOException {
        SearchResponse<ProdutoResponse> busca = client.search(s -> s
                .index(INDEX)
                .query(q -> q.match(m -> m.field("descricao").query(termo))), ProdutoResponse.class);

        return extrairProdutos(busca);
    }

    public List<ProdutoResponse> buscarPorFraseExata(String termo) throws IOException {
        SearchResponse<ProdutoResponse> busca = client.search(s -> s
                .index(INDEX)
                .query(q -> q.matchPhrase(m -> m.field("descricao").query(termo))), ProdutoResponse.class);

        return extrairProdutos(busca);
    }

    public List<ProdutoResponse> buscarFuzzyPorNome(String termo) throws IOException {
        SearchResponse<ProdutoResponse> busca = client.search(s -> s
                .index(INDEX)
                .query(q -> q.fuzzy(f -> f.field("nome").value(termo))), ProdutoResponse.class);

        return extrairProdutos(busca);
    }

    public List<ProdutoResponse> buscarMultiCampos(String termo) throws IOException {
        SearchResponse<ProdutoResponse> busca = client.search(s -> s
                .index(INDEX)
                .query(q -> q.multiMatch(m -> m.fields("nome", "descricao").query(termo))), ProdutoResponse.class);

        return extrairProdutos(busca);
    }

    public List<ProdutoResponse> buscarComFiltro(String termo, String categoria) throws IOException {
        SearchResponse<ProdutoResponse> busca = client.search(s -> s
                .index(INDEX)
                .query(q -> q.bool(b -> b
                        .must(m -> m.match(mm -> mm.field("descricao").query(termo)))
                        .must(m -> m.match(mm -> mm.field("categoria").query(categoria)))
                )), ProdutoResponse.class);

        return extrairProdutos(busca);
    }

    public List<ProdutoResponse> buscarPorFaixaPreco(BigDecimal min, BigDecimal max) throws IOException {
        SearchResponse<ProdutoResponse> busca = client.search(s -> s
                .index(INDEX)
                .query(q -> q.range(r -> r.number(n -> n
                        .field("preco")
                        .gte(min.doubleValue())
                        .lte(max.doubleValue())
                ))), ProdutoResponse.class);

        return extrairProdutos(busca);
    }

    public List<ProdutoResponse> buscarAvancada(String categoria, String raridade, BigDecimal min, BigDecimal max) throws IOException {
        SearchResponse<ProdutoResponse> busca = client.search(s -> s
                .index(INDEX)
                .query(q -> q.bool(b -> b
                        .must(m -> m.match(mm -> mm.field("categoria").query(categoria)))
                        .must(m -> m.match(mm -> mm.field("raridade").query(raridade)))
                        .filter(f -> f.range(r -> r.number(n -> n
                                .field("preco")
                                .gte(min.doubleValue())
                                .lte(max.doubleValue())
                        )))
                )), ProdutoResponse.class);

        return extrairProdutos(busca);
    }

    public List<BucketResponse> quantidadePorCategoria() throws IOException {
        SearchResponse<Void> busca = client.search(s -> s
                .index(INDEX)
                .size(0)
                .aggregations("por_categoria", a -> a.terms(t -> t.field("categoria"))), Void.class);

        List<StringTermsBucket> buckets = busca.aggregations()
                .get("por_categoria")
                .sterms()
                .buckets()
                .array();

        List<BucketResponse> resposta = new ArrayList<>();
        for (StringTermsBucket bucket : buckets) {
            resposta.add(new BucketResponse(bucket.key().stringValue(), bucket.docCount()));
        }

        return resposta;
    }

    public List<BucketResponse> quantidadePorRaridade() throws IOException {
        SearchResponse<Void> busca = client.search(s -> s
                .index(INDEX)
                .size(0)
                .aggregations("por_raridade", a -> a.terms(t -> t.field("raridade"))), Void.class);

        List<StringTermsBucket> buckets = busca.aggregations()
                .get("por_raridade")
                .sterms()
                .buckets()
                .array();

        List<BucketResponse> resposta = new ArrayList<>();
        for (StringTermsBucket bucket : buckets) {
            resposta.add(new BucketResponse(bucket.key().stringValue(), bucket.docCount()));
        }

        return resposta;
    }

    public PrecoMedioResponse precoMedio() throws IOException {
        SearchResponse<Void> busca = client.search(s -> s
                .index(INDEX)
                .size(0)
                .aggregations("preco_medio", a -> a.avg(avg -> avg.field("preco"))), Void.class);

        Double media = busca.aggregations()
                .get("preco_medio")
                .avg()
                .value();

        return new PrecoMedioResponse(media);
    }

    public List<FaixaPrecoResponse> faixasPreco() throws IOException {
        SearchResponse<Void> busca = client.search(s -> s
                .index(INDEX)
                .size(0)
                .aggregations("faixas_preco", a -> a.range(r -> r
                        .field("preco")
                        .ranges(rr -> rr.to(100.0))
                        .ranges(rr -> rr.from(100.0).to(300.0))
                        .ranges(rr -> rr.from(300.0).to(700.0))
                        .ranges(rr -> rr.from(700.0))
                )), Void.class);

        var buckets = busca.aggregations()
                .get("faixas_preco")
                .range()
                .buckets()
                .array();

        List<FaixaPrecoResponse> resposta = new ArrayList<>();
        resposta.add(new FaixaPrecoResponse("Abaixo de 100", buckets.get(0).docCount()));
        resposta.add(new FaixaPrecoResponse("De 100 a 300", buckets.get(1).docCount()));
        resposta.add(new FaixaPrecoResponse("De 300 a 700", buckets.get(2).docCount()));
        resposta.add(new FaixaPrecoResponse("Acima de 700", buckets.get(3).docCount()));

        return resposta;
    }

    private List<ProdutoResponse> extrairProdutos(SearchResponse<ProdutoResponse> busca) {
        List<ProdutoResponse> produtos = new ArrayList<>();

        for (Hit<ProdutoResponse> hit : busca.hits().hits()) {
            if (hit.source() != null) {
                produtos.add(hit.source());
            }
        }

        return produtos;
    }
}