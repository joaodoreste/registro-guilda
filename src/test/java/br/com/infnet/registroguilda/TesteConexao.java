package br.com.infnet.registroguilda;

import br.com.infnet.registroguilda.audit.entity.Organizacao;
import br.com.infnet.registroguilda.audit.repository.OrganizacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TesteConexao {

    @Autowired
    private OrganizacaoRepository repository;

    @Test
    void deveListarOrganizacoes() {
        List<Organizacao> lista = repository.findAll();

        assertFalse(lista.isEmpty());

        lista.forEach(o -> System.out.println(o.getNome()));
    }
}