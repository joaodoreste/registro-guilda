package br.com.infnet.registroguilda.aventura;

import br.com.infnet.registroguilda.aventura.dto.AventureiroPerfilDto;
import br.com.infnet.registroguilda.aventura.dto.AventureiroResumoDto;
import br.com.infnet.registroguilda.aventura.entity.Aventureiro;
import br.com.infnet.registroguilda.aventura.entity.Companheiro;
import br.com.infnet.registroguilda.aventura.entity.Missao;
import br.com.infnet.registroguilda.aventura.entity.ParticipacaoMissao;
import br.com.infnet.registroguilda.aventura.repository.AventureiroRepository;
import br.com.infnet.registroguilda.aventura.repository.CompanheiroRepository;
import br.com.infnet.registroguilda.aventura.repository.MissaoRepository;
import br.com.infnet.registroguilda.aventura.repository.ParticipacaoMissaoRepository;
import br.com.infnet.registroguilda.aventura.service.AventureiroConsultaService;
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
class AventureiroConsultaServiceTest {

    @Autowired
    private AventureiroConsultaService aventureiroConsultaService;

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private CompanheiroRepository companheiroRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Aventureiro aventureiroSalvo;

    @BeforeEach
    void setup() {
        participacaoMissaoRepository.deleteAll();
        companheiroRepository.deleteAll();
        missaoRepository.deleteAll();
        aventureiroRepository.deleteAll();

        Organizacao organizacao = organizacaoRepository.findById(1L).orElseThrow();
        Usuario usuario = usuarioRepository.findById(1L).orElseThrow();

        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setOrganizacao(organizacao);
        aventureiro.setUsuarioResponsavel(usuario);
        aventureiro.setNome("Arthas");
        aventureiro.setClasse("Paladino");
        aventureiro.setNivel(15);
        aventureiro.setAtivo(true);
        aventureiro.setCreatedAt(OffsetDateTime.now());
        aventureiro.setUpdatedAt(OffsetDateTime.now());
        aventureiroSalvo = aventureiroRepository.save(aventureiro);

        Companheiro companheiro = new Companheiro();
        companheiro.setAventureiro(aventureiroSalvo);
        companheiro.setNome("Lobo Branco");
        companheiro.setEspecie("Lobo");
        companheiro.setIndiceLealdade(95);
        companheiroRepository.save(companheiro);

        Missao missao = new Missao();
        missao.setOrganizacao(organizacao);
        missao.setTitulo("Defesa da Fortaleza");
        missao.setNivelPerigo("ALTO");
        missao.setStatus("ABERTA");
        missao.setCreatedAt(OffsetDateTime.now());
        missao.setDataInicio(OffsetDateTime.now().minusDays(1));
        missao.setDataTermino(OffsetDateTime.now().plusDays(2));
        Missao missaoSalva = missaoRepository.save(missao);

        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setMissao(missaoSalva);
        participacao.setAventureiro(aventureiroSalvo);
        participacao.setPapelNaMissao("Tanque");
        participacao.setRecompensaOuro(new BigDecimal("150.00"));
        participacao.setDestaque(true);
        participacao.setDataRegistro(OffsetDateTime.now());
        participacaoMissaoRepository.save(participacao);
    }

    @Test
    void deveListarAventureirosComFiltros() {
        Page<AventureiroResumoDto> pagina = aventureiroConsultaService.listar(
                true, "Paladino", 10, PageRequest.of(0, 10)
        );

        assertFalse(pagina.isEmpty());
        assertEquals("Arthas", pagina.getContent().getFirst().nome());
    }

    @Test
    void deveBuscarAventureiroPorNome() {
        Page<AventureiroResumoDto> pagina = aventureiroConsultaService.buscarPorNome(
                "Arth", PageRequest.of(0, 10)
        );

        assertFalse(pagina.isEmpty());
        assertEquals("Arthas", pagina.getContent().getFirst().nome());
    }

    @Test
    void deveRetornarPerfilCompletoDoAventureiro() {
        AventureiroPerfilDto perfil = aventureiroConsultaService.buscarPerfil(aventureiroSalvo.getId());

        assertEquals("Arthas", perfil.nome());
        assertEquals("Lobo Branco", perfil.nomeCompanheiro());
        assertEquals(1L, perfil.totalParticipacoes());
        assertEquals("Defesa da Fortaleza", perfil.ultimaMissao());
    }
}