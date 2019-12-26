package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.AreaFormacao} entity.
 */
public class AreaFormacaoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10)
    private String nome;

    @Lob
    private String competencias;


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

    public String getCompetencias() {
        return competencias;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AreaFormacaoDTO areaFormacaoDTO = (AreaFormacaoDTO) o;
        if (areaFormacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), areaFormacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AreaFormacaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", competencias='" + getCompetencias() + "'" +
            "}";
    }
}
