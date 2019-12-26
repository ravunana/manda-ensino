package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Classe} entity.
 */
public class ClasseDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer descricao;

    private String tipoEnsino;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDescricao() {
        return descricao;
    }

    public void setDescricao(Integer descricao) {
        this.descricao = descricao;
    }

    public String getTipoEnsino() {
        return tipoEnsino;
    }

    public void setTipoEnsino(String tipoEnsino) {
        this.tipoEnsino = tipoEnsino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClasseDTO classeDTO = (ClasseDTO) o;
        if (classeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClasseDTO{" +
            "id=" + getId() +
            ", descricao=" + getDescricao() +
            ", tipoEnsino='" + getTipoEnsino() + "'" +
            "}";
    }
}
