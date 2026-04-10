package br.com.infnet.registroguilda.busca.controller;

import br.com.infnet.registroguilda.busca.dto.BucketResponse;
import br.com.infnet.registroguilda.busca.dto.FaixaPrecoResponse;
import br.com.infnet.registroguilda.busca.dto.PrecoMedioResponse;
import br.com.infnet.registroguilda.busca.dto.ProdutoResponse;
import br.com.infnet.registroguilda.busca.service.ProdutoBuscaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProdutoBuscaController {

    private final ProdutoBuscaService service;

    @GetMapping("/produtos/busca/nome")
    public List<ProdutoResponse> buscarPorNome(@RequestParam String termo) throws IOException {
        return service.buscarPorNome(termo);
    }

    @GetMapping("/produtos/busca/descricao")
    public List<ProdutoResponse> buscarPorDescricao(@RequestParam String termo) throws IOException {
        return service.buscarPorDescricao(termo);
    }

    @GetMapping("/produtos/busca/frase")
    public List<ProdutoResponse> buscarPorFrase(@RequestParam String termo) throws IOException {
        return service.buscarPorFraseExata(termo);
    }

    @GetMapping("/produtos/busca/fuzzy")
    public List<ProdutoResponse> buscarFuzzy(@RequestParam String termo) throws IOException {
        return service.buscarFuzzyPorNome(termo);
    }

    @GetMapping("/produtos/busca/multicampos")
    public List<ProdutoResponse> buscarMultiCampos(@RequestParam String termo) throws IOException {
        return service.buscarMultiCampos(termo);
    }

    @GetMapping("/produtos/busca/com-filtro")
    public List<ProdutoResponse> buscarComFiltro(@RequestParam String termo, @RequestParam String categoria) throws IOException {
        return service.buscarComFiltro(termo, categoria);
    }

    @GetMapping("/produtos/busca/faixa-preco")
    public List<ProdutoResponse> buscarPorFaixaPreco(@RequestParam BigDecimal min, @RequestParam BigDecimal max) throws IOException {
        return service.buscarPorFaixaPreco(min, max);
    }

    @GetMapping("/produtos/busca/avancada")
    public List<ProdutoResponse> buscarAvancada(@RequestParam String categoria, @RequestParam String raridade, @RequestParam BigDecimal min, @RequestParam BigDecimal max) throws IOException {
        return service.buscarAvancada(categoria, raridade, min, max);
    }

    @GetMapping("/produtos/agregacoes/por-categoria")
    public List<BucketResponse> quantidadePorCategoria() throws IOException {
        return service.quantidadePorCategoria();
    }

    @GetMapping("/produtos/agregacoes/por-raridade")
    public List<BucketResponse> quantidadePorRaridade() throws IOException {
        return service.quantidadePorRaridade();
    }

    @GetMapping("/produtos/agregacoes/preco-medio")
    public PrecoMedioResponse precoMedio() throws IOException {
        return service.precoMedio();
    }

    @GetMapping("/produtos/agregacoes/faixas-preco")
    public List<FaixaPrecoResponse> faixasPreco() throws IOException {
        return service.faixasPreco();
    }
}
