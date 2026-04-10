package br.com.infnet.registroguilda;

import br.com.infnet.registroguilda.audit.entity.Organizacao;
import br.com.infnet.registroguilda.audit.entity.Role;
import br.com.infnet.registroguilda.audit.entity.Usuario;
import br.com.infnet.registroguilda.audit.repository.OrganizacaoRepository;
import br.com.infnet.registroguilda.audit.repository.RoleRepository;
import br.com.infnet.registroguilda.audit.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuditMappingTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Test
    void deveCarregarUsuarioComOrganizacaoERoles() {
        Usuario usuario = usuarioRepository.findById(1L).orElseThrow();

        assertNotNull(usuario.getOrganizacao());
        assertEquals("Guilda do Norte", usuario.getOrganizacao().getNome());
        assertFalse(usuario.getRoles().isEmpty());
    }

    @Test
    void deveCarregarRoleComPermissions() {
        Role role = roleRepository.findById(1L).orElseThrow();

        assertNotNull(role.getPermissions());
        assertFalse(role.getPermissions().isEmpty());
    }

    @Test
    void devePersistirNovoUsuarioAssociadoAOrganizacaoExistente() {
        Organizacao organizacao = organizacaoRepository.findById(1L).orElseThrow();

        Usuario usuario = new Usuario();
        usuario.setOrganizacao(organizacao);
        usuario.setNome("Novo Usuário");
        usuario.setEmail("novo@guildanorte.com");
        usuario.setSenhaHash("hash_novo");
        usuario.setStatus("ATIVO");
        usuario.setCreatedAt(OffsetDateTime.now());
        usuario.setUpdatedAt(OffsetDateTime.now());

        Usuario salvo = usuarioRepository.save(usuario);

        assertNotNull(salvo.getId());
        assertEquals("Novo Usuário", salvo.getNome());
        assertEquals(organizacao.getId(), salvo.getOrganizacao().getId());
    }
}