package br.com.infnet.registroguilda.aventura.repository;

import br.com.infnet.registroguilda.aventura.entity.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    Page<Aventureiro> findByAtivo(Boolean ativo, Pageable pageable);

    Page<Aventureiro> findByClasseIgnoreCase(String classe, Pageable pageable);

    Page<Aventureiro> findByNivelGreaterThanEqual(Integer nivel, Pageable pageable);

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Aventureiro> findByAtivoAndClasseIgnoreCaseAndNivelGreaterThanEqual(Boolean ativo, String classe, Integer nivel, Pageable pageable
    );

    Page<Aventureiro> findByAtivoAndClasseIgnoreCase(Boolean ativo, String classe, Pageable pageable
    );

    Page<Aventureiro> findByAtivoAndNivelGreaterThanEqual(Boolean ativo, Integer nivel, Pageable pageable
    );

    Page<Aventureiro> findByClasseIgnoreCaseAndNivelGreaterThanEqual(String classe, Integer nivel, Pageable pageable
    );
}