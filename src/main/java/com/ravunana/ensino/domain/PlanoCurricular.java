package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A PlanoCurricular.
 */
@Entity
@Table(name = "pdg_plano_curricular")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "planocurricular")
public class PlanoCurricular implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "terminal", nullable = false)
    private Boolean terminal;

    @NotNull
    @Column(name = "regime_curricular", nullable = false)
    private String regimeCurricular;

    @NotNull
    @Column(name = "componente", nullable = false)
    private String componente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("planoCurriculars")
    private Disciplina disciplina;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("planoCurriculars")
    private Classe classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public PlanoCurricular cargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
        return this;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getDescricao() {
        return descricao;
    }

    public PlanoCurricular descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isTerminal() {
        return terminal;
    }

    public PlanoCurricular terminal(Boolean terminal) {
        this.terminal = terminal;
        return this;
    }

    public void setTerminal(Boolean terminal) {
        this.terminal = terminal;
    }

    public String getRegimeCurricular() {
        return regimeCurricular;
    }

    public PlanoCurricular regimeCurricular(String regimeCurricular) {
        this.regimeCurricular = regimeCurricular;
        return this;
    }

    public void setRegimeCurricular(String regimeCurricular) {
        this.regimeCurricular = regimeCurricular;
    }

    public String getComponente() {
        return componente;
    }

    public PlanoCurricular componente(String componente) {
        this.componente = componente;
        return this;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public PlanoCurricular disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Classe getClasse() {
        return classe;
    }

    public PlanoCurricular classe(Classe classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoCurricular)) {
            return false;
        }
        return id != null && id.equals(((PlanoCurricular) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlanoCurricular{" +
            "id=" + getId() +
            ", cargaHoraria=" + getCargaHoraria() +
            ", descricao='" + getDescricao() + "'" +
            ", terminal='" + isTerminal() + "'" +
            ", regimeCurricular='" + getRegimeCurricular() + "'" +
            ", componente='" + getComponente() + "'" +
            "}";
    }
}
