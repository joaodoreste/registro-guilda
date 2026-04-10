package br.com.infnet.registroguilda.aventura;

import br.com.infnet.registroguilda.aventura.dto.MissaoDetalheDto;
import br.com.infnet.registroguilda.aventura.dto.MissaoResumoDto;
import br.com.infnet.registroguilda.aventura.entity.Aventureiro;
import br.com.infnet.registroguilda.aventura.entity.Missao;
import br.com.infnet.registroguilda.aventura.entity.ParticipacaoMissao;
import br.com.infnet.registroguilda.aventura.repository.AventureiroRepository;
import br.com.infnet.registroguilda.aventura.repository.MissaoRepository;
import br.com.infnet.registroguilda.aventura.repository.ParticipacaoMissaoRepository;
import br.com.infnet.registroguilda.aventura.service.MissaoConsultaService;
import br.com.infnet.registroguilda.audit.entity.Organizacao;
import br.com.infnet.registroguilda.audit.entity.Usuario;
import br.com.infnet.registroguilda.audit.repository.OrganizacaoRepository;
import br.com.infnet.registroguilda.audit.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MissaoConsultaServiceTest {

    @Autowired
    private MissaoConsultaService missaoConsultaService;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Missao missaoSalva;

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
        aventureiro.setNome("Jaina");
        aventureiro.setClasse("Maga");
        aventureiro.setNivel(18);
        aventureiro.setAtivo(true);
        aventureiro.setCreatedAt(OffsetDateTime.now());
        aventureiro.setUpdatedAt(OffsetDateTime.now());
        Aventureiro aventureiroSalvo = aventureiroRepository.save(aventureiro);

        Missao missao = new Missao();
        missao.setOrganizacao(organizacao);
        missao.setTitulo("Ruínas Antigas");
        missao.setNivelPerigo("MEDIO");
        missao.setStatus("EM_ANDAMENTO");
        missao.setCreatedAt(OffsetDateTime.now());
        missao.setDataInicio(OffsetDateTime.now().minusDays(3));
        missao.setDataTermino(OffsetDateTime.now().plusDays(1));
        missaoSalva = missaoRepository.save(missao);

        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setMissao(missaoSalva);
        participacao.setAventureiro(aventureiroSalvo);
        participacao.setPapelNaMissao("DPS");
        participacao.setRecompensaOuro(new BigDecimal("200.00"));
        participacao.setDestaque(false);
        participacao.setDataRegistro(OffsetDateTime.now());
        participacaoMissaoRepository.save(participacao);
    }

    @Test
    void deveListarMissoesComFiltros() {
        Page<MissaoResumoDto> pagina = missaoConsultaService.listar(
                "EM_ANDAMENTO", "MEDIO", null, null, PageRequest.of(0, 10)
        );

        assertFalse(pagina.isEmpty());
        assertEquals("Ruínas Antigas", pagina.getContent().getFirst().titulo());
    }

    @Test
    void deveDetalharMissaoComParticipantes() {
        MissaoDetalheDto detalhe = missaoConsultaService.detalhar(missaoSalva.getId());

        assertEquals("Ruínas Antigas", detalhe.titulo());
        assertFalse(detalhe.participantes().isEmpty());
        assertEquals("Jaina", detalhe.participantes().getFirst().nomeAventureiro());
    }
}