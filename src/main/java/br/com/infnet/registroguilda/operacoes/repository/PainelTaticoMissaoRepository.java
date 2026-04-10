package br.com.infnet.registroguilda.operacoes.repository;

import br.com.infnet.registroguilda.operacoes.entity.PainelTaticoMissao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {

    List<PainelTaticoMissao> findByUltimaAtualizacaoGreaterThanEqualOrderByIndiceProntidaoDesc(
            LocalDateTime dataLimite,
            Pageable pageable
    );
}
