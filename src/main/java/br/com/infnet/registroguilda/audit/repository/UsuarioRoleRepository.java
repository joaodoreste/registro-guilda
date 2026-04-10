package br.com.infnet.registroguilda.audit.repository;

import br.com.infnet.registroguilda.audit.entity.UsuarioRole;
import br.com.infnet.registroguilda.audit.entity.UsuarioRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRoleRepository extends JpaRepository<UsuarioRole, UsuarioRoleId> {
}