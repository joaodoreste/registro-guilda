package br.com.infnet.registroguilda.aventura.controller;

import br.com.infnet.registroguilda.aventura.dto.AventureiroPerfilDto;
import br.com.infnet.registroguilda.aventura.dto.AventureiroResumoDto;
import br.com.infnet.registroguilda.aventura.service.AventureiroConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aventura/aventureiros")
@RequiredArgsConstructor
public class AventureiroConsultaController {

    private final AventureiroConsultaService aventureiroConsultaService;

    @GetMapping
    public Page<AventureiroResumoDto> listar(@RequestParam(required = false) Boolean ativo, @RequestParam(required = false) String classe, @RequestParam(required = false) Integer nivelMinimo, Pageable pageable
    ) {
        return aventureiroConsultaService.listar(ativo, classe, nivelMinimo, pageable);
    }

    @GetMapping("/buscar")
    public Page<AventureiroResumoDto> buscarPorNome(@RequestParam String nome, Pageable pageable
    ) {
        return aventureiroConsultaService.buscarPorNome(nome, pageable);
    }

    @GetMapping("/{id}")
    public AventureiroPerfilDto buscarPerfil(@PathVariable Long id) {
        return aventureiroConsultaService.buscarPerfil(id);
    }
}