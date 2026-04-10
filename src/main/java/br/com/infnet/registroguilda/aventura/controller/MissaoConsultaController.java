package br.com.infnet.registroguilda.aventura.controller;

import br.com.infnet.registroguilda.aventura.dto.MissaoDetalheDto;
import br.com.infnet.registroguilda.aventura.dto.MissaoResumoDto;
import br.com.infnet.registroguilda.aventura.service.MissaoConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/aventura/missoes")
@RequiredArgsConstructor
public class MissaoConsultaController {

    private final MissaoConsultaService missaoConsultaService;

    @GetMapping
    public Page<MissaoResumoDto> listar(@RequestParam(required = false) String status, @RequestParam(required = false) String nivelPerigo, @RequestParam(required = false) OffsetDateTime inicio, @RequestParam(required = false) OffsetDateTime fim, Pageable pageable
    ) {
        return missaoConsultaService.listar(status, nivelPerigo, inicio, fim, pageable);
    }

    @GetMapping("/{id}")
    public MissaoDetalheDto detalhar(@PathVariable Long id) {
        return missaoConsultaService.detalhar(id);
    }
}