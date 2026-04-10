package br.com.infnet.registroguilda.aventura.repository;

import br.com.infnet.registroguilda.aventura.dto.RankingParticipacaoDto;
import br.com.infnet.registroguilda.aventura.dto.RelatorioMissaoDto;
import br.com.infnet.registroguilda.aventura.entity.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {

    long countByAventureiroId(Long aventureiroId);

    List<ParticipacaoMissao> findByMissaoId(Long missaoId);

    Optional<ParticipacaoMissao> findFirstByAventureiroIdOrderByDataRegistroDesc(Long aventureiroId);

    @Query("""
        select new br.com.infnet.registroguilda.aventura.dto.RankingParticipacaoDto(
            p.aventureiro.id,
            p.aventureiro.nome,
            count(p),
            coalesce(sum(p.recompensaOuro), 0),
            count(case when p.destaque = true then 1 else null end)
        )
        from ParticipacaoMissao p
        where p.dataRegistro between :inicio and :fim
        group by p.aventureiro.id, p.aventureiro.nome
        order by count(p) desc, coalesce(sum(p.recompensaOuro), 0) desc
    """)
    List<RankingParticipacaoDto> gerarRanking(OffsetDateTime inicio, OffsetDateTime fim);

    @Query("""
        select new br.com.infnet.registroguilda.aventura.dto.RelatorioMissaoDto(
            p.missao.id,
            p.missao.titulo,
            p.missao.status,
            p.missao.nivelPerigo,
            count(p),
            coalesce(sum(p.recompensaOuro), 0)
        )
        from ParticipacaoMissao p
        where p.missao.dataInicio between :inicio and :fim
        group by p.missao.id, p.missao.titulo, p.missao.status, p.missao.nivelPerigo
        order by p.missao.titulo asc
    """)
    List<RelatorioMissaoDto> gerarRelatorioMissoes(OffsetDateTime inicio, OffsetDateTime fim);
}