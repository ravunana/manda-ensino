package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Professor.
 */
@Entity
@Table(name = "pdg_professor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "professor")
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero_agente", nullable = false, unique = true)
    private String numeroAgente;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "professor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Horario> horarios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("professors")
    private User utilizador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroAgente() {
        return numeroAgente;
    }

    public Professor numeroAgente(String numeroAgente) {
        this.numeroAgente = numeroAgente;
        return this;
    }

    public void setNumeroAgente(String numeroAgente) {
        this.numeroAgente = numeroAgente;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Professor ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Professor pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public Professor horarios(Set<Horario> horarios) {
        this.horarios = horarios;
        return this;
    }

    public Professor addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setProfessor(this);
        return this;
    }

    public Professor removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setProfessor(null);
        return this;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Professor utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professor)) {
            return false;
        }
        return id != null && id.equals(((Professor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Professor{" +
            "id=" + getId() +
            ", numeroAgente='" + getNumeroAgente() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
