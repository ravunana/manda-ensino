package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Pagamento.
 */
@Entity
@Table(name = "fin_pagamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "pagamento")
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "data")
    private ZonedDateTime data;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pagamentos")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pagamentos")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pagamentos")
    private FormaLiquidacao formaLiquidacao;

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

    public Pagamento data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public Pagamento numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Pagamento utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Pagamento aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public FormaLiquidacao getFormaLiquidacao() {
        return formaLiquidacao;
    }

    public Pagamento formaLiquidacao(FormaLiquidacao formaLiquidacao) {
        this.formaLiquidacao = formaLiquidacao;
        return this;
    }

    public void setFormaLiquidacao(FormaLiquidacao formaLiquidacao) {
        this.formaLiquidacao = formaLiquidacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pagamento)) {
            return false;
        }
        return id != null && id.equals(((Pagamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
