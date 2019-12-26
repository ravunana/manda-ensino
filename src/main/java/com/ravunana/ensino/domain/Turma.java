package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Turma.
 */
@Entity
@Table(name = "pdg_turma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "turma")
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @Column(name = "ano_lectivo")
    private LocalDate anoLectivo;

    @NotNull
    @Column(name = "regime", nullable = false)
    private String regime;

    @NotNull
    @Column(name = "turno", nullable = false)
    private String turno;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nota> notas = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aula> aulas = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Matricula> matriculas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("turmas")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("turmas")
    private Sala sala;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("turmas")
    private Classe classe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("turmas")
    private Curso curso;

    @ManyToMany(mappedBy = "turmas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<PlanoAula> planoAulas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Turma descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public Turma anoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
        return this;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getRegime() {
        return regime;
    }

    public Turma regime(String regime) {
        this.regime = regime;
        return this;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getTurno() {
        return turno;
    }

    public Turma turno(String turno) {
        this.turno = turno;
        return this;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Turma data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Turma ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public Turma horarios(Set<Horario> horarios) {
        this.horarios = horarios;
        return this;
    }

    public Turma addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setTurma(this);
        return this;
    }

    public Turma removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setTurma(null);
        return this;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public Set<Nota> getNotas() {
        return notas;
    }

    public Turma notas(Set<Nota> notas) {
        this.notas = notas;
        return this;
    }

    public Turma addNota(Nota nota) {
        this.notas.add(nota);
        nota.setTurma(this);
        return this;
    }

    public Turma removeNota(Nota nota) {
        this.notas.remove(nota);
        nota.setTurma(null);
        return this;
    }

    public void setNotas(Set<Nota> notas) {
        this.notas = notas;
    }

    public Set<Aula> getAulas() {
        return aulas;
    }

    public Turma aulas(Set<Aula> aulas) {
        this.aulas = aulas;
        return this;
    }

    public Turma addAula(Aula aula) {
        this.aulas.add(aula);
        aula.setTurma(this);
        return this;
    }

    public Turma removeAula(Aula aula) {
        this.aulas.remove(aula);
        aula.setTurma(null);
        return this;
    }

    public void setAulas(Set<Aula> aulas) {
        this.aulas = aulas;
    }

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public Turma matriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
        return this;
    }

    public Turma addMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setTurma(this);
        return this;
    }

    public Turma removeMatricula(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setTurma(null);
        return this;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Turma utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Sala getSala() {
        return sala;
    }

    public Turma sala(Sala sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Classe getClasse() {
        return classe;
    }

    public Turma classe(Classe classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Curso getCurso() {
        return curso;
    }

    public Turma curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Set<PlanoAula> getPlanoAulas() {
        return planoAulas;
    }

    public Turma planoAulas(Set<PlanoAula> planoAulas) {
        this.planoAulas = planoAulas;
        return this;
    }

    public Turma addPlanoAula(PlanoAula planoAula) {
        this.planoAulas.add(planoAula);
        planoAula.getTurmas().add(this);
        return this;
    }

    public Turma removePlanoAula(PlanoAula planoAula) {
        this.planoAulas.remove(planoAula);
        planoAula.getTurmas().remove(this);
        return this;
    }

    public void setPlanoAulas(Set<PlanoAula> planoAulas) {
        this.planoAulas = planoAulas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Turma)) {
            return false;
        }
        return id != null && id.equals(((Turma) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Turma{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", anoLectivo='" + getAnoLectivo() + "'" +
            ", regime='" + getRegime() + "'" +
            ", turno='" + getTurno() + "'" +
            ", data='" + getData() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
