package br.com.infnet.registroguilda.audit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UsuarioRoleId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "role_id")
    private Long roleId;

    public UsuarioRoleId() {
    }

    public UsuarioRoleId(Long usuarioId, Long roleId) {
        this.usuarioId = usuarioId;
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioRoleId that)) return false;
        return Objects.equals(usuarioId, that.usuarioId)
                && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, roleId);
    }
}