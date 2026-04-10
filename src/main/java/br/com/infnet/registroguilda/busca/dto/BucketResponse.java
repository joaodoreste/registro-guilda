package br.com.infnet.registroguilda.busca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BucketResponse {

    private String chave;
    private Long quantidade;
}
