package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Curso.
 */
@Entity
@Table(name = "pdg_curso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 10)
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @Size(min = 3, max = 12)
    @Column(name = "sigla", length = 12, nullable = false, unique = true)
    private String sigla;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "competencias")
    private String competencias;

    @OneToMany(mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turma> turmas = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MatrizCurricular> matrizCurriculars = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Emolumento> emolumentos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cursos")
    private AreaFormacao areaFormacao;

    @ManyToMany(mappedBy = "cursos")
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

    public String getNome() {
        return nome;
    }

    public Curso nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public Curso sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCompetencias() {
        return competencias;
    }

    public Curso competencias(String competencias) {
        this.competencias = competencias;
        return this;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public Curso turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public Curso addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.setCurso(this);
        return this;
    }

    public Curso removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.setCurso(null);
        return this;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    public Set<MatrizCurricular> getMatrizCurriculars() {
        return matrizCurriculars;
    }

    public Curso matrizCurriculars(Set<MatrizCurricular> matrizCurriculars) {
        this.matrizCurriculars = matrizCurriculars;
        return this;
    }

    public Curso addMatrizCurricular(MatrizCurricular matrizCurricular) {
        this.matrizCurriculars.add(matrizCurricular);
        matrizCurricular.setCurso(this);
        return this;
    }

    public Curso removeMatrizCurricular(MatrizCurricular matrizCurricular) {
        this.matrizCurriculars.remove(matrizCurricular);
        matrizCurricular.setCurso(null);
        return this;
    }

    public void setMatrizCurriculars(Set<MatrizCurricular> matrizCurriculars) {
        this.matrizCurriculars = matrizCurriculars;
    }

    public Set<Emolumento> getEmolumentos() {
        return emolumentos;
    }

    public Curso emolumentos(Set<Emolumento> emolumentos) {
        this.emolumentos = emolumentos;
        return this;
    }

    public Curso addEmolumento(Emolumento emolumento) {
        this.emolumentos.add(emolumento);
        emolumento.setCurso(this);
        return this;
    }

    public Curso removeEmolumento(Emolumento emolumento) {
        this.emolumentos.remove(emolumento);
        emolumento.setCurso(null);
        return this;
    }

    public void setEmolumentos(Set<Emolumento> emolumentos) {
        this.emolumentos = emolumentos;
    }

    public AreaFormacao getAreaFormacao() {
        return areaFormacao;
    }

    public Curso areaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacao = areaFormacao;
        return this;
    }

    public void setAreaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public Set<Dossificacao> getDossificacaos() {
        return dossificacaos;
    }

    public Curso dossificacaos(Set<Dossificacao> dossificacaos) {
        this.dossificacaos = dossificacaos;
        return this;
    }

    public Curso addDossificacao(Dossificacao dossificacao) {
        this.dossificacaos.add(dossificacao);
        dossificacao.getCursos().add(this);
        return this;
    }

    public Curso removeDossificacao(Dossificacao dossificacao) {
        this.dossificacaos.remove(dossificacao);
        dossificacao.getCursos().remove(this);
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
        if (!(o instanceof Curso)) {
            return false;
        }
        return id != null && id.equals(((Curso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", competencias='" + getCompetencias() + "'" +
            "}";
    }
}
