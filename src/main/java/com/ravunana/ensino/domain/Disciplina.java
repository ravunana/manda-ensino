package com.ravunana.ensino.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Disciplina.
 */
@Entity
@Table(name = "pdg_disciplina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "disciplina")
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @Size(min = 3)
    @Column(name = "sigla", nullable = false, unique = true)
    private String sigla;

    @OneToMany(mappedBy = "disciplina")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoCurricular> planoCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "disciplina")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "disciplina")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nota> notas = new HashSet<>();

    @OneToMany(mappedBy = "disciplina")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoAula> planoAulas = new HashSet<>();

    @OneToMany(mappedBy = "disciplina")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dossificacao> dossificacaos = new HashSet<>();

    @OneToMany(mappedBy = "disciplina")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UnidadeCurricular> unidadeCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "disciplina")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SituacaoAcademica> situacaoAcademicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Disciplina nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public Disciplina sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Set<PlanoCurricular> getPlanoCurriculars() {
        return planoCurriculars;
    }

    public Disciplina planoCurriculars(Set<PlanoCurricular> planoCurriculars) {
        this.planoCurriculars = planoCurriculars;
        return this;
    }

    public Disciplina addPlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurriculars.add(planoCurricular);
        planoCurricular.setDisciplina(this);
        return this;
    }

    public Disciplina removePlanoCurricular(PlanoCurricular planoCurricular) {
        this.planoCurriculars.remove(planoCurricular);
        planoCurricular.setDisciplina(null);
        return this;
    }

    public void setPlanoCurriculars(Set<PlanoCurricular> planoCurriculars) {
        this.planoCurriculars = planoCurriculars;
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public Disciplina horarios(Set<Horario> horarios) {
        this.horarios = horarios;
        return this;
    }

    public Disciplina addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setDisciplina(this);
        return this;
    }

    public Disciplina removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setDisciplina(null);
        return this;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public Set<Nota> getNotas() {
        return notas;
    }

    public Disciplina notas(Set<Nota> notas) {
        this.notas = notas;
        return this;
    }

    public Disciplina addNota(Nota nota) {
        this.notas.add(nota);
        nota.setDisciplina(this);
        return this;
    }

    public Disciplina removeNota(Nota nota) {
        this.notas.remove(nota);
        nota.setDisciplina(null);
        return this;
    }

    public void setNotas(Set<Nota> notas) {
        this.notas = notas;
    }

    public Set<PlanoAula> getPlanoAulas() {
        return planoAulas;
    }

    public Disciplina planoAulas(Set<PlanoAula> planoAulas) {
        this.planoAulas = planoAulas;
        return this;
    }

    public Disciplina addPlanoAula(PlanoAula planoAula) {
        this.planoAulas.add(planoAula);
        planoAula.setDisciplina(this);
        return this;
    }

    public Disciplina removePlanoAula(PlanoAula planoAula) {
        this.planoAulas.remove(planoAula);
        planoAula.setDisciplina(null);
        return this;
    }

    public void setPlanoAulas(Set<PlanoAula> planoAulas) {
        this.planoAulas = planoAulas;
    }

    public Set<Dossificacao> getDossificacaos() {
        return dossificacaos;
    }

    public Disciplina dossificacaos(Set<Dossificacao> dossificacaos) {
        this.dossificacaos = dossificacaos;
        return this;
    }

    public Disciplina addDossificacao(Dossificacao dossificacao) {
        this.dossificacaos.add(dossificacao);
        dossificacao.setDisciplina(this);
        return this;
    }

    public Disciplina removeDossificacao(Dossificacao dossificacao) {
        this.dossificacaos.remove(dossificacao);
        dossificacao.setDisciplina(null);
        return this;
    }

    public void setDossificacaos(Set<Dossificacao> dossificacaos) {
        this.dossificacaos = dossificacaos;
    }

    public Set<UnidadeCurricular> getUnidadeCurriculars() {
        return unidadeCurriculars;
    }

    public Disciplina unidadeCurriculars(Set<UnidadeCurricular> unidadeCurriculars) {
        this.unidadeCurriculars = unidadeCurriculars;
        return this;
    }

    public Disciplina addUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurriculars.add(unidadeCurricular);
        unidadeCurricular.setDisciplina(this);
        return this;
    }

    public Disciplina removeUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurriculars.remove(unidadeCurricular);
        unidadeCurricular.setDisciplina(null);
        return this;
    }

    public void setUnidadeCurriculars(Set<UnidadeCurricular> unidadeCurriculars) {
        this.unidadeCurriculars = unidadeCurriculars;
    }

    public Set<SituacaoAcademica> getSituacaoAcademicas() {
        return situacaoAcademicas;
    }

    public Disciplina situacaoAcademicas(Set<SituacaoAcademica> situacaoAcademicas) {
        this.situacaoAcademicas = situacaoAcademicas;
        return this;
    }

    public Disciplina addSituacaoAcademica(SituacaoAcademica situacaoAcademica) {
        this.situacaoAcademicas.add(situacaoAcademica);
        situacaoAcademica.setDisciplina(this);
        return this;
    }

    public Disciplina removeSituacaoAcademica(SituacaoAcademica situacaoAcademica) {
        this.situacaoAcademicas.remove(situacaoAcademica);
        situacaoAcademica.setDisciplina(null);
        return this;
    }

    public void setSituacaoAcademicas(Set<SituacaoAcademica> situacaoAcademicas) {
        this.situacaoAcademicas = situacaoAcademicas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disciplina)) {
            return false;
        }
        return id != null && id.equals(((Disciplina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
