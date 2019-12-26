package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Aula.
 */
@Entity
@Table(name = "prof_aula")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "aula")
public class Aula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @NotNull
    @Column(name = "sumario", nullable = false)
    private String sumario;

    @NotNull
    @Column(name = "licao", nullable = false)
    private Integer licao;

    @NotNull
    @Column(name = "dada", nullable = false)
    private Boolean dada;

    @ManyToOne
    @JsonIgnoreProperties("aulas")
    private User utilizador;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "prof_aula_plano_aula",
               joinColumns = @JoinColumn(name = "aula_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "plano_aula_id", referencedColumnName = "id"))
    private Set<PlanoAula> planoAulas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("aulas")
    private Turma turma;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Aula data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getSumario() {
        return sumario;
    }

    public Aula sumario(String sumario) {
        this.sumario = sumario;
        return this;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Integer getLicao() {
        return licao;
    }

    public Aula licao(Integer licao) {
        this.licao = licao;
        return this;
    }

    public void setLicao(Integer licao) {
        this.licao = licao;
    }

    public Boolean isDada() {
        return dada;
    }

    public Aula dada(Boolean dada) {
        this.dada = dada;
        return this;
    }

    public void setDada(Boolean dada) {
        this.dada = dada;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Aula utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Set<PlanoAula> getPlanoAulas() {
        return planoAulas;
    }

    public Aula planoAulas(Set<PlanoAula> planoAulas) {
        this.planoAulas = planoAulas;
        return this;
    }

    public Aula addPlanoAula(PlanoAula planoAula) {
        this.planoAulas.add(planoAula);
        planoAula.getAulas().add(this);
        return this;
    }

    public Aula removePlanoAula(PlanoAula planoAula) {
        this.planoAulas.remove(planoAula);
        planoAula.getAulas().remove(this);
        return this;
    }

    public void setPlanoAulas(Set<PlanoAula> planoAulas) {
        this.planoAulas = planoAulas;
    }

    public Turma getTurma() {
        return turma;
    }

    public Aula turma(Turma turma) {
        this.turma = turma;
        return this;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aula)) {
            return false;
        }
        return id != null && id.equals(((Aula) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Aula{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", sumario='" + getSumario() + "'" +
            ", licao=" + getLicao() +
            ", dada='" + isDada() + "'" +
            "}";
    }
}
