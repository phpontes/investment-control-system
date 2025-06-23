package com.itau.investments.investmentcontrolsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PosicaoId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "ativo_id")
    private Long ativoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PosicaoId)) return false;
        PosicaoId that = (PosicaoId) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
               Objects.equals(ativoId, that.ativoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, ativoId);
    }
}