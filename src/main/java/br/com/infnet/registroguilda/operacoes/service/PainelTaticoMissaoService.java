package br.com.infnet.registroguilda.operacoes.service;

import br.com.infnet.registroguilda.operacoes.entity.PainelTaticoMissao;
import br.com.infnet.registroguilda.operacoes.repository.PainelTaticoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PainelTaticoMissaoService {

    private final PainelTaticoMissaoRepository repository;

    @Cacheable("topMissoes15Dias")
    public List<PainelTaticoMissao> buscarTop15Dias() {

        LocalDateTime dataLimite = LocalDateTime.now().minusDays(15);

        return repository.findByUltimaAtualizacaoGreaterThanEqualOrderByIndiceProntidaoDesc(
                dataLimite,
                PageRequest.of(0, 10)
        );
    }
}