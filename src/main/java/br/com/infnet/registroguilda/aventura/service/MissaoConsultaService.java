package br.com.infnet.registroguilda.aventura.service;

import br.com.infnet.registroguilda.aventura.dto.MissaoDetalheDto;
import br.com.infnet.registroguilda.aventura.dto.MissaoResumoDto;
import br.com.infnet.registroguilda.aventura.dto.ParticipanteMissaoDto;
import br.com.infnet.registroguilda.aventura.entity.Missao;
import br.com.infnet.registroguilda.aventura.entity.ParticipacaoMissao;
import br.com.infnet.registroguilda.aventura.repository.MissaoRepository;
import br.com.infnet.registroguilda.aventura.repository.ParticipacaoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissaoConsultaService {

    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    public Page<MissaoResumoDto> listar(String status, String nivelPerigo, OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable) {
        Page<Missao> missoes;
        boolean filtrarPorPeriodo = inicio != null && fim != null;

        if (status != null && nivelPerigo != null && filtrarPorPeriodo) {
            missoes = missaoRepository.findByStatusIgnoreCaseAndNivelPerigoIgnoreCaseAndDataInicioBetween(
                    status, nivelPerigo, inicio, fim, pageable
            );
        } else if (status != null && nivelPerigo != null) {
            missoes = missaoRepository.findByStatusIgnoreCaseAndNivelPerigoIgnoreCase(status, nivelPerigo, pageable);
        } else if (status != null && filtrarPorPeriodo) {
            missoes = missaoRepository.findByStatusIgnoreCaseAndDataInicioBetween(status, inicio, fim, pageable);
        } else if (nivelPerigo != null && filtrarPorPeriodo) {
            missoes = missaoRepository.findByNivelPerigoIgnoreCaseAndDataInicioBetween(nivelPerigo, inicio, fim, pageable);
        } else if (status != null) {
            missoes = missaoRepository.findByStatusIgnoreCase(status, pageable);
        } else if (nivelPerigo != null) {
            missoes = missaoRepository.findByNivelPerigoIgnoreCase(nivelPerigo, pageable);
        } else if (filtrarPorPeriodo) {
            missoes = missaoRepository.findByDataInicioBetween(inicio, fim, pageable);
        } else {
            missoes = missaoRepository.findAll(pageable);
        }

        return missoes.map(this::montarResumo);
    }

    public MissaoDetalheDto detalhar(Long id) {
        Missao missao = missaoRepository.findById(id).orElseThrow();

        List<ParticipacaoMissao> registros = participacaoMissaoRepository.findByMissaoId(id);
        List<ParticipanteMissaoDto> participantes = new ArrayList<>();

        for (ParticipacaoMissao registro : registros) {
            participantes.add(montarParticipante(registro));
        }

        return new MissaoDetalheDto(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelPerigo(),
                missao.getCreatedAt(),
                missao.getDataInicio(),
                missao.getDataTermino(),
                participantes
        );
    }

    private MissaoResumoDto montarResumo(Missao missao) {
        return new MissaoResumoDto(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelPerigo(),
                missao.getDataInicio(),
                missao.getDataTermino()
        );
    }

    private ParticipanteMissaoDto montarParticipante(ParticipacaoMissao participacao) {
        return new ParticipanteMissaoDto(
                participacao.getAventureiro().getId(),
                participacao.getAventureiro().getNome(),
                participacao.getPapelNaMissao(),
                participacao.getRecompensaOuro(),
                participacao.getDestaque()
        );
    }
}