package br.com.infnet.registroguilda.audit.repository;

import br.com.infnet.registroguilda.audit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
