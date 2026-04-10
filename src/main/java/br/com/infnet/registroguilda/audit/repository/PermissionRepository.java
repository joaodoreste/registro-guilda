package br.com.infnet.registroguilda.audit.repository;

import br.com.infnet.registroguilda.audit.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}