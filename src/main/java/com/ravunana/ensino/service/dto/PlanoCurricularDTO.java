package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.PlanoCurricular} entity.
 */
public class PlanoCurricularDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer cargaHoraria;

    @NotNull
    private String descricao;

    @NotNull
    private Boolean terminal;

    @NotNull
    private String regimeCurricular;

    @NotNull
    private String componente;


    private Long disciplinaId;

    private String disciplinaNome;

    private Long classeId;

    private String classeDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(Boolean terminal) {
        this.terminal = terminal;
    }

    public String getRegimeCurricular() {
        return regimeCurricular;
    }

    public void setRegimeCurricular(String regimeCurricular) {
        this.regimeCurricular = regimeCurricular;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlanoCurricularDTO planoCurricularDTO = (PlanoCurricularDTO) o;
        if (planoCurricularDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planoCurricularDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanoCurricularDTO{" +
            "id=" + getId() +
            ", cargaHoraria=" + getCargaHoraria() +
            ", descricao='" + getDescricao() + "'" +
            ", terminal='" + isTerminal() + "'" +
            ", regimeCurricular='" + getRegimeCurricular() + "'" +
            ", componente='" + getComponente() + "'" +
            ", disciplinaId=" + getDisciplinaId() +
            ", disciplinaNome='" + getDisciplinaNome() + "'" +
            ", classeId=" + getClasseId() +
            ", classeDescricao='" + getClasseDescricao() + "'" +
            "}";
    }
}
