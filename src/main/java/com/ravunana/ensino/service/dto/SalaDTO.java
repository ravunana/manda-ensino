package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Sala} entity.
 */
public class SalaDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer numero;

    @Lob
    private String descricao;

    @NotNull
    @Min(value = 30)
    private Integer lotacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLotacao() {
        return lotacao;
    }

    public void setLotacao(Integer lotacao) {
        this.lotacao = lotacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalaDTO salaDTO = (SalaDTO) o;
        if (salaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalaDTO{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", descricao='" + getDescricao() + "'" +
            ", lotacao=" + getLotacao() +
            "}";
    }
}
