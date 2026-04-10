package br.com.infnet.registroguilda.aventura.controller;

import br.com.infnet.registroguilda.aventura.dto.RankingParticipacaoDto;
import br.com.infnet.registroguilda.aventura.dto.RelatorioMissaoDto;
import br.com.infnet.registroguilda.aventura.service.RelatorioAventuraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/aventura/relatorios")
@RequiredArgsConstructor
public class RelatorioAventuraController {

    private final RelatorioAventuraService relatorioAventuraService;

    @GetMapping("/ranking-participacao")
    public List<RankingParticipacaoDto> gerarRanking(@RequestParam OffsetDateTime inicio, @RequestParam OffsetDateTime fim
    ) {
        return relatorioAventuraService.gerarRanking(inicio, fim);
    }

    @GetMapping("/missoes")
    public List<RelatorioMissaoDto> gerarRelatorioMissoes(@RequestParam OffsetDateTime inicio, @RequestParam OffsetDateTime fim
    ) {
        return relatorioAventuraService.gerarRelatorioMissoes(inicio, fim);
    }
}