package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.UnidadeCurricular} entity.
 */
public class UnidadeCurricularDTO implements Serializable {

    private Long id;

    
    @Lob
    private String descricao;

    @NotNull
    private String unidade;

    @NotNull
    private Integer numero;


    private Long disciplinaId;

    private String disciplinaNome;

    private Long classeId;

    private String classeDescricao;

    private Long herarquiaId;

    private String herarquiaUnidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getDisciplinaNome() {
        return disciplinaNome;
    }

    public void setDisciplinaNome(String disciplinaNome) {
        this.disciplinaNome = disciplinaNome;
    }

    public Long getClasseId() {
        return classeId;
    }

    public void setClasseId(Long classeId) {
        this.classeId = classeId;
    }

    public String getClasseDescricao() {
        return classeDescricao;
    }

    public void setClasseDescricao(String classeDescricao) {
        this.classeDescricao = classeDescricao;
    }

    public Long getHerarquiaId() {
        return herarquiaId;
    }

    public void setHerarquiaId(Long unidadeCurricularId) {
        this.herarquiaId = unidadeCurricularId;
    }

    public String getHerarquiaUnidade() {
        return herarquiaUnidade;
    }

    public void setHerarquiaUnidade(String unidadeCurricularUnidade) {
        this.herarquiaUnidade = unidadeCurricularUnidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnidadeCurricularDTO unidadeCurricularDTO = (UnidadeCurricularDTO) o;
        if (unidadeCurricularDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidadeCurricularDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnidadeCurricularDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", unidade='" + getUnidade() + "'" +
            ", numero=" + getNumero() +
            ", disciplinaId=" + getDisciplinaId() +
            ", disciplinaNome='" + getDisciplinaNome() + "'" +
            ", classeId=" + getClasseId() +
            ", classeDescricao='" + getClasseDescricao() + "'" +
            ", herarquiaId=" + getHerarquiaId() +
            ", herarquiaUnidade='" + getHerarquiaUnidade() + "'" +
            "}";
    }
}
