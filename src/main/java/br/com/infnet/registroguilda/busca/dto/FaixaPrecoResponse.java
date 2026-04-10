package br.com.infnet.registroguilda.busca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FaixaPrecoResponse {

    private String faixa;
    private Long quantidade;
}
