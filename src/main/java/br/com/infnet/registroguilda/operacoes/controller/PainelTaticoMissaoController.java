package br.com.infnet.registroguilda.operacoes.controller;

import br.com.infnet.registroguilda.operacoes.entity.PainelTaticoMissao;
import br.com.infnet.registroguilda.operacoes.service.PainelTaticoMissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PainelTaticoMissaoController {

    private final PainelTaticoMissaoService service;

    @GetMapping("/missoes/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> buscarTop15Dias() {
        return ResponseEntity.ok(service.buscarTop15Dias());
    }
}