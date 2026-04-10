package br.com.infnet.registroguilda.aventura.service;

import br.com.infnet.registroguilda.aventura.dto.AventureiroPerfilDto;
import br.com.infnet.registroguilda.aventura.dto.AventureiroResumoDto;
import br.com.infnet.registroguilda.aventura.entity.Aventureiro;
import br.com.infnet.registroguilda.aventura.repository.AventureiroRepository;
import br.com.infnet.registroguilda.aventura.repository.ParticipacaoMissaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AventureiroConsultaService {

    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    public Page<AventureiroResumoDto> listar(Boolean ativo, String classe, Integer nivelMinimo, Pageable pageable) {
        Page<Aventureiro> aventureiros;

        if (ativo != null && classe != null && nivelMinimo != null) {
            aventureiros = aventureiroRepository.findByAtivoAndClasseIgnoreCaseAndNivelGreaterThanEqual(
                    ativo, classe, nivelMinimo, pageable
            );
        } else if (ativo != null && classe != null) {
            aventureiros = aventureiroRepository.findByAtivoAndClasseIgnoreCase(ativo, classe, pageable);
        } else if (ativo != null && nivelMinimo != null) {
            aventureiros = aventureiroRepository.findByAtivoAndNivelGreaterThanEqual(ativo, nivelMinimo, pageable);
        } else if (classe != null && nivelMinimo != null) {
            aventureiros = aventureiroRepository.findByClasseIgnoreCaseAndNivelGreaterThanEqual(classe, nivelMinimo, pageable);
        } else if (ativo != null) {
            aventureiros = aventureiroRepository.findByAtivo(ativo, pageable);
        } else if (classe != null) {
            aventureiros = aventureiroRepository.findByClasseIgnoreCase(classe, pageable);
        } else if (nivelMinimo != null) {
            aventureiros = aventureiroRepository.findByNivelGreaterThanEqual(nivelMinimo, pageable);
        } else {
            aventureiros = aventureiroRepository.findAll(pageable);
        }

        return aventureiros.map(this::montarResumo);
    }

    public Page<AventureiroResumoDto> buscarPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::montarResumo);
    }

    @Transactional
    public AventureiroPerfilDto buscarPerfil(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id).orElseThrow();

        long totalParticipacoes = participacaoMissaoRepository.countByAventureiroId(id);

        String ultimaMissao = participacaoMissaoRepository
                .findFirstByAventureiroIdOrderByDataRegistroDesc(id)
                .map(participacao -> participacao.getMissao().getTitulo())
                .orElse(null);

        String nomeCompanheiro = null;
        String especieCompanheiro = null;

        if (aventureiro.getCompanheiro() != null) {
            nomeCompanheiro = aventureiro.getCompanheiro().getNome();
            especieCompanheiro = aventureiro.getCompanheiro().getEspecie();
        }

        return new AventureiroPerfilDto(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo(),
                nomeCompanheiro,
                especieCompanheiro,
                totalParticipacoes,
                ultimaMissao
        );
    }

    private AventureiroResumoDto montarResumo(Aventureiro aventureiro) {
        return new AventureiroResumoDto(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo()
        );
    }
}