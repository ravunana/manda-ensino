package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Disciplina} entity.
 */
public class DisciplinaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Size(min = 3)
    private String sigla;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DisciplinaDTO disciplinaDTO = (DisciplinaDTO) o;
        if (disciplinaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disciplinaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DisciplinaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
