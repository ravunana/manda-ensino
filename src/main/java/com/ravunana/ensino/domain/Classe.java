package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Classe.
 */
@Entity
@Table(name = "pdg_classe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "classe")
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private Integer descricao;

    @Column(name = "tipo_ensino")
    private String tipoEnsino;

    @OneToMany(mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turma> turmas = new HashSet<>();

    @OneToMany(mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoCurricular> planoCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UnidadeCurricular> unidadeCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Emolumento> emolumentos = new HashSet<>();

    @ManyToMany(mappedBy = "classes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Dossificacao> dossificacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDescricao() {
        return descricao;
    }

    public Classe descricao(Integer descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(Integer descricao) {
        this.descricao = descricao;
    }

    public String getTipoEnsino() {
        return tipoEnsino;
    }

    public Classe tipoEnsino(String tipoEnsino) {
        this.tipoEnsino = tipoEnsino;
        return this;
    }

    public void setTipoEnsino(String tipoEnsino) {
        this.tipoEnsino = tipoEnsino;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public Classe turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public Classe addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.setClasse(this);
        return this;
    }

    public Classe removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.setClasse(null);
        return this;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    public Set<PlanoCurricular> getPlanoCurriculars() {
        return planoCurriculars;
    }

    public Classe planoCurriculars(Set<PlanoCurricular> planoCurriculars) {
        this.planoCurriculars = planoCurriculars;
        return this;
    }

    public Classe addPlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurriculars.add(planoCurricular);
        planoCurricular.setClasse(this);
        return this;
    }

    public Classe removePlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurriculars.remove(planoCurricular);
        planoCurricular.setClasse(null);
        return this;
    }

    public void setPlanoCurriculars(Set<PlanoCurricular> planoCurriculars) {
        this.planoCurriculars = planoCurriculars;
    }

    public Set<UnidadeCurricular> getUnidadeCurriculars() {
        return unidadeCurriculars;
    }

    public Classe unidadeCurriculars(Set<UnidadeCurricular> unidadeCurriculars) {
        this.unidadeCurriculars = unidadeCurriculars;
        return this;
    }

    public Classe addUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurriculars.add(unidadeCurricular);
        unidadeCurricular.setClasse(this);
        return this;
    }

    public Classe removeUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurriculars.remove(unidadeCurricular);
        unidadeCurricular.setClasse(null);
        return this;
    }

    public void setUnidadeCurriculars(Set<UnidadeCurricular> unidadeCurriculars) {
        this.unidadeCurriculars = unidadeCurriculars;
    }

    public Set<Emolumento> getEmolumentos() {
        return emolumentos;
    }

    public Classe emolumentos(Set<Emolumento> emolumentos) {
        this.emolumentos = emolumentos;
        return this;
    }

    public Classe addEmolumento(Emolumento emolumento) {
        this.emolumentos.add(emolumento);
        emolumento.setClasse(this);
        return this;
    }

    public Classe removeEmolumento(Emolumento emolumento) {
        this.emolumentos.remove(emolumento);
        emolumento.setClasse(null);
        return this;
    }

    public void setEmolumentos(Set<Emolumento> emolumentos) {
        this.emolumentos = emolumentos;
    }

    public Set<Dossificacao> getDossificacaos() {
        return dossificacaos;
    }

    public Classe dossificacaos(Set<Dossificacao> dossificacaos) {
        this.dossificacaos = dossificacaos;
        return this;
    }

    public Classe addDossificacao(Dossificacao dossificacao) {
        this.dossificacaos.add(dossificacao);
        dossificacao.getClasses().add(this);
        return this;
    }

    public Classe removeDossificacao(Dossificacao dossificacao) {
        this.dossificacaos.remove(dossificacao);
        dossificacao.getClasses().remove(this);
        return this;
    }

    public void setDossificacaos(Set<Dossificacao> dossificacaos) {
        this.dossificacaos = dossificacaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classe)) {
            return false;
        }
        return id != null && id.equals(((Classe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", descricao=" + getDescricao() +
            ", tipoEnsino='" + getTipoEnsino() + "'" +
            "}";
    }
}
