package br.com.infnet.registroguilda.aventura;

import br.com.infnet.registroguilda.aventura.dto.RankingParticipacaoDto;
import br.com.infnet.registroguilda.aventura.dto.RelatorioMissaoDto;
import br.com.infnet.registroguilda.aventura.entity.Aventureiro;
import br.com.infnet.registroguilda.aventura.entity.Missao;
import br.com.infnet.registroguilda.aventura.entity.ParticipacaoMissao;
import br.com.infnet.registroguilda.aventura.repository.AventureiroRepository;
import br.com.infnet.registroguilda.aventura.repository.MissaoRepository;
import br.com.infnet.registroguilda.aventura.repository.ParticipacaoMissaoRepository;
import br.com.infnet.registroguilda.aventura.service.RelatorioAventuraService;
import br.com.infnet.registroguilda.audit.entity.Organizacao;
import br.com.infnet.registroguilda.audit.entity.Usuario;
import br.com.infnet.registroguilda.audit.repository.OrganizacaoRepository;
import br.com.infnet.registroguilda.audit.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RelatorioAventuraServiceTest {

    @Autowired
    private RelatorioAventuraService relatorioAventuraService;

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup() {
        participacaoMissaoRepository.deleteAll();
        missaoRepository.deleteAll();
        aventureiroRepository.deleteAll();

        Organizacao organizacao = organizacaoRepository.findById(1L).orElseThrow();
        Usuario usuario = usuarioRepository.findById(1L).orElseThrow();

        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setOrganizacao(organizacao);
        aventureiro.setUsuarioResponsavel(usuario);
        aventureiro.setNome("Thrall");
        aventureiro.setClasse("Xamã");
        aventureiro.setNivel(20);
        aventureiro.setAtivo(true);
        aventureiro.setCreatedAt(OffsetDateTime.now());
        aventureiro.setUpdatedAt(OffsetDateTime.now());
        Aventureiro aventureiroSalvo = aventureiroRepository.save(aventureiro);

        Missao missao = new Missao();
        missao.setOrganizacao(organizacao);
        missao.setTitulo("Cerco Final");
        missao.setNivelPerigo("ALTO");
        missao.setStatus("CONCLUIDA");
        missao.setCreatedAt(OffsetDateTime.now());
        missao.setDataInicio(OffsetDateTime.now().minusDays(5));
        missao.setDataTermino(OffsetDateTime.now().minusDays(1));
        Missao missaoSalva = missaoRepository.save(missao);

        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setMissao(missaoSalva);
        participacao.setAventureiro(aventureiroSalvo);
        participacao.setPapelNaMissao("Líder");
        participacao.setRecompensaOuro(new BigDecimal("500.00"));
        participacao.setDestaque(true);
        participacao.setDataRegistro(OffsetDateTime.now().minusDays(2));
        participacaoMissaoRepository.save(participacao);
    }

    @Test
    void deveGerarRankingParticipacao() {
        List<RankingParticipacaoDto> ranking = relatorioAventuraService.gerarRanking(
                OffsetDateTime.now().minusDays(10),
                OffsetDateTime.now()
        );

        assertFalse(ranking.isEmpty());
        assertEquals("Thrall", ranking.getFirst().getNomeAventureiro());
    }

    @Test
    void deveGerarRelatorioMissoes() {
        List<RelatorioMissaoDto> relatorio = relatorioAventuraService.gerarRelatorioMissoes(
                OffsetDateTime.now().minusDays(10),
                OffsetDateTime.now()
        );

        assertFalse(relatorio.isEmpty());
        assertEquals("Cerco Final", relatorio.getFirst().getTitulo());
    }
}