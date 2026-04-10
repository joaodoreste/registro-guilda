package br.com.infnet.registroguilda.aventura.repository;

import br.com.infnet.registroguilda.aventura.entity.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface MissaoRepository extends JpaRepository<Missao, Long> {

    Page<Missao> findByStatusIgnoreCase(String status, Pageable pageable);

    Page<Missao> findByNivelPerigoIgnoreCase(String nivelPerigo, Pageable pageable);

    Page<Missao> findByDataInicioBetween(OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable);

    Page<Missao> findByStatusIgnoreCaseAndNivelPerigoIgnoreCase(String status, String nivelPerigo, Pageable pageable
    );
}