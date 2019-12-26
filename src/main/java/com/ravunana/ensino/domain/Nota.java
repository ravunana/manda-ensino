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
 * A Nota.
 */
@Entity
@Table(name = "prof_nota")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "nota")
public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "ano_lectivo")
    private LocalDate anoLectivo;

    @Column(name = "periodo_lectivo")
    private String periodoLectivo;

    @ManyToOne
    @JsonIgnoreProperties("notas")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("notas")
    private Disciplina disciplina;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("notas")
    private Turma turma;

    @ManyToOne
    @JsonIgnoreProperties("notas")
    private CategoriaValiacao categoriaAvaliacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("notas")
    private Matricula matricula;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public Nota valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Nota data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public Nota anoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
        return this;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getPeriodoLectivo() {
        return periodoLectivo;
    }

    public Nota periodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
        return this;
    }

    public void setPeriodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Nota utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Nota disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Turma getTurma() {
        return turma;
    }

    public Nota turma(Turma turma) {
        this.turma = turma;
        return this;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public CategoriaValiacao getCategoriaAvaliacao() {
        return categoriaAvaliacao;
    }

    public Nota categoriaAvaliacao(CategoriaValiacao categoriaValiacao) {
        this.categoriaAvaliacao = categoriaValiacao;
        return this;
    }

    public void setCategoriaAvaliacao(CategoriaValiacao categoriaValiacao) {
        this.categoriaAvaliacao = categoriaValiacao;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public Nota matricula(Matricula matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nota)) {
            return false;
        }
        return id != null && id.equals(((Nota) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Nota{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            ", anoLectivo='" + getAnoLectivo() + "'" +
            ", periodoLectivo='" + getPeriodoLectivo() + "'" +
            "}";
    }
}
