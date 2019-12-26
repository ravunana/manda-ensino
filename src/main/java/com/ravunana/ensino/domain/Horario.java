package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Horario.
 */
@Entity
@Table(name = "pdg_horario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "horario")
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "inicio_aula", nullable = false)
    private ZonedDateTime inicioAula;

    @NotNull
    @Column(name = "termino_alua", nullable = false)
    private ZonedDateTime terminoAlua;

    @NotNull
    @Column(name = "intervalo", nullable = false)
    private ZonedDateTime intervalo;

    @NotNull
    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    @NotNull
    @Column(name = "regime_curricular", nullable = false)
    private String regimeCurricular;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @Column(name = "ano_lectivo")
    private LocalDate anoLectivo;

    @Column(name = "categoria")
    private String categoria;

    @ManyToOne
    @JsonIgnoreProperties("horarios")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("horarios")
    private Turma turma;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("horarios")
    private Professor professor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("horarios")
    private Disciplina disciplina;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getInicioAula() {
        return inicioAula;
    }

    public Horario inicioAula(ZonedDateTime inicioAula) {
        this.inicioAula = inicioAula;
        return this;
    }

    public void setInicioAula(ZonedDateTime inicioAula) {
        this.inicioAula = inicioAula;
    }

    public ZonedDateTime getTerminoAlua() {
        return terminoAlua;
    }

    public Horario terminoAlua(ZonedDateTime terminoAlua) {
        this.terminoAlua = terminoAlua;
        return this;
    }

    public void setTerminoAlua(ZonedDateTime terminoAlua) {
        this.terminoAlua = terminoAlua;
    }

    public ZonedDateTime getIntervalo() {
        return intervalo;
    }

    public Horario intervalo(ZonedDateTime intervalo) {
        this.intervalo = intervalo;
        return this;
    }

    public void setIntervalo(ZonedDateTime intervalo) {
        this.intervalo = intervalo;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public Horario diaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getRegimeCurricular() {
        return regimeCurricular;
    }

    public Horario regimeCurricular(String regimeCurricular) {
        this.regimeCurricular = regimeCurricular;
        return this;
    }

    public void setRegimeCurricular(String regimeCurricular) {
        this.regimeCurricular = regimeCurricular;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Horario data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public Horario anoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
        return this;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getCategoria() {
        return categoria;
    }

    public Horario categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Horario utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Turma getTurma() {
        return turma;
    }

    public Horario turma(Turma turma) {
        this.turma = turma;
        return this;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Horario professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Horario disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Horario)) {
            return false;
        }
        return id != null && id.equals(((Horario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Horario{" +
            "id=" + getId() +
            ", inicioAula='" + getInicioAula() + "'" +
            ", terminoAlua='" + getTerminoAlua() + "'" +
            ", intervalo='" + getIntervalo() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", regimeCurricular='" + getRegimeCurricular() + "'" +
            ", data='" + getData() + "'" +
            ", anoLectivo='" + getAnoLectivo() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
