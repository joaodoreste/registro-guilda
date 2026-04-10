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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoBuscaService {

    private final ElasticsearchClient client;
    private static final String INDEX = "guilda_loja";

    public List<ProdutoResponse> buscarPorNome(String termo) throws IOException {
        SearchResponse<ProdutoResponse> response = client.search(s -> s
                        .index(INDEX)
                        .query(q -> q
                                .match(m -> m
                                        .field("nome")
                                        .query(termo)
                                )
                        ),
                ProdutoResponse.class
        );
        return mapear(response);
    }

    public List<ProdutoResponse> buscarPorDescricao(String termo) throws IOException {
        SearchResponse<ProdutoResponse> response = client.search(s -> s
                        .index(INDEX)
                        .query(q -> q
                                .match(m -> m
                                        .field("descricao")
                                        .query(termo)
                                )
                        ),
                ProdutoResponse.class
        );
        return mapear(response);
    }

    public List<ProdutoResponse> buscarPorFraseExata(String termo) throws IOException {
        SearchResponse<ProdutoResponse> response = client.search(s -> s
                        .index(INDEX)
                        .query(q -> q
                                .matchPhrase(mp -> mp
                                        .field("descricao")
                                        .query(termo)
                                )
                        ),
                ProdutoResponse.class
        );
        return mapear(response);
    }

    public List<ProdutoResponse> buscarFuzzyPorNome(String termo) throws IOException {
        SearchResponse<ProdutoResponse> response = client.search(s -> s
                        .index(INDEX)
                        .query(q -> q
                                .fuzzy(f -> f
                                        .field("nome")
                                        .value(termo)
                                )
                        ),
                ProdutoResponse.class
        );
        return mapear(response);
    }

    public List<ProdutoResponse> buscarMultiCampos(String termo) throws IOException {
        SearchResponse<ProdutoResponse> response = client.search(s -> s
                        .index(INDEX)
                        .query(q -> q
                                .multiMatch(mm -> mm
                                        .fields("nome", "descricao")
                                        .query(termo)
                                )
                        ),
                ProdutoResponse.class
        );
        return mapear(response);
    }

    public List<ProdutoResponse> buscarComFiltro(String termo, String categoria) throws IOException {
        SearchResponse<ProdutoResponse> response = client.search(s -> s
                        .index(INDEX)
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m
                                                .match(mm -> mm
                                                        .field("descricao")
                                                        .query(termo)
                                                )
                                        )
                                        .must(m -> m
                                                .match(mm -> mm
                                                        .field("categoria")
                                                        .query(categoria)
                                                )
                                        )
                                )
                        ),
                ProdutoResponse.class
        );
        return mapear(response);
    }

    public List<ProdutoResponse> buscarPorFaixaPreco(BigDecimal min, BigDecimal max) throws IOException {
        SearchResponse<ProdutoResponse> response = client.search(s -> s
                        .index(INDEX)
                        .query(q -> q
                                .range(r -> r
                                        .number(n -> n
                                                .field("preco")
                                                .gte(min.doubleValue())
                                                .lte(max.doubleValue())
                                        )
                                )
                        ),
                ProdutoResponse.class
        );
        return mapear(response);
    }

    public List<ProdutoResponse> buscarAvancada(String categoria, String raridade, BigDecimal min, BigDecimal max) throws IOException {
        SearchResponse<ProdutoResponse> response = client.search(s -> s
                        .index(INDEX)
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m
                                                .match(mm -> mm
                                                        .field("categoria")
                                                        .query(categoria)
                                                )
                                        )
                                        .must(m -> m
                                                .match(mm -> mm
                                                        .field("raridade")
                                                        .query(raridade)
                                                )
                                        )
                                        .filter(f -> f
                                                .range(r -> r
                                                        .number(n -> n
                                                                .field("preco")
                                                                .gte(min.doubleValue())
                                                                .lte(max.doubleValue())
                                                        )
                                                )
                                        )
                                )
                        ),
                ProdutoResponse.class
        );
        return mapear(response);
    }

    public List<BucketResponse> quantidadePorCategoria() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index(INDEX)
                        .size(0)
                        .aggregations("por_categoria", a -> a
                                .terms(t -> t.field("categoria"))
                        ),
                Void.class
        );

        List<StringTermsBucket> buckets = response.aggregations()
                .get("por_categoria")
                .sterms()
                .buckets()
                .array();

        return buckets.stream()
                .map(bucket -> new BucketResponse(bucket.key().stringValue(), bucket.docCount()))
                .collect(Collectors.toList());
    }

    public List<BucketResponse> quantidadePorRaridade() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index(INDEX)
                        .size(0)
                        .aggregations("por_raridade", a -> a
                                .terms(t -> t.field("raridade"))
                        ),
                Void.class
        );

        List<StringTermsBucket> buckets = response.aggregations()
                .get("por_raridade")
                .sterms()
                .buckets()
                .array();

        return buckets.stream()
                .map(bucket -> new BucketResponse(bucket.key().stringValue(), bucket.docCount()))
                .collect(Collectors.toList());
    }

    public PrecoMedioResponse precoMedio() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index(INDEX)
                        .size(0)
                        .aggregations("preco_medio", a -> a
                                .avg(avg -> avg.field("preco"))
                        ),
                Void.class
        );

        Double valor = response.aggregations()
                .get("preco_medio")
                .avg()
                .value();

        return new PrecoMedioResponse(valor);
    }

    public List<FaixaPrecoResponse> faixasPreco() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index(INDEX)
                        .size(0)
                        .aggregations("faixas_preco", a -> a
                                .range(r -> r
                                        .field("preco")
                                        .ranges(rr -> rr.to(100.0))
                                        .ranges(rr -> rr.from(100.0).to(300.0))
                                        .ranges(rr -> rr.from(300.0).to(700.0))
                                        .ranges(rr -> rr.from(700.0))
                                )
                        ),
                Void.class
        );

        List<FaixaPrecoResponse> resultado = new ArrayList<>();

        var buckets = response.aggregations()
                .get("faixas_preco")
                .range()
                .buckets()
                .array();

        resultado.add(new FaixaPrecoResponse("Abaixo de 100", buckets.get(0).docCount()));
        resultado.add(new FaixaPrecoResponse("De 100 a 300", buckets.get(1).docCount()));
        resultado.add(new FaixaPrecoResponse("De 300 a 700", buckets.get(2).docCount()));
        resultado.add(new FaixaPrecoResponse("Acima de 700", buckets.get(3).docCount()));

        return resultado;
    }

    private List<ProdutoResponse> mapear(SearchResponse<ProdutoResponse> response) {
        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
