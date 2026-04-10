package br.com.infnet.registroguilda.busca.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoResponse {

    private String nome;
    private String descricao;
    private String categoria;
    private String raridade;
    private BigDecimal preco;
}