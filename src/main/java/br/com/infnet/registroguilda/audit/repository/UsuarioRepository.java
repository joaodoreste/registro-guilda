package br.com.infnet.registroguilda.audit.repository;

import br.com.infnet.registroguilda.audit.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}