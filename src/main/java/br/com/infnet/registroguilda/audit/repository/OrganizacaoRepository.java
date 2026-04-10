package br.com.infnet.registroguilda.audit.repository;

import br.com.infnet.registroguilda.audit.entity.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {
}
