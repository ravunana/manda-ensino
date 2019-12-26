package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Emolumento.
 */
@Entity
@Table(name = "fin_emolumento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "emolumento")
public class Emolumento implements Serializable {

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
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor_multa", nullable = false)
    private Double valorMulta;

    @NotNull
    @Min(value = 1)
    @Column(name = "tempo_multa", nullable = false)
    private Integer tempoMulta;

    @DecimalMin(value = "0")
    @Column(name = "quantidade")
    private Double quantidade;

    @OneToMany(mappedBy = "emolumento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalhePagamento> detalhePagamentos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("emolumentos")
    private Curso curso;

    @ManyToOne
    @JsonIgnoreProperties("emolumentos")
    private Classe classe;

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

    public Emolumento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Emolumento valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Double getValorMulta() {
        return valorMulta;
    }

    public Emolumento valorMulta(Double valorMulta) {
        this.valorMulta = valorMulta;
        return this;
    }

    public void setValorMulta(Double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public Integer getTempoMulta() {
        return tempoMulta;
    }

    public Emolumento tempoMulta(Integer tempoMulta) {
        this.tempoMulta = tempoMulta;
        return this;
    }

    public void setTempoMulta(Integer tempoMulta) {
        this.tempoMulta = tempoMulta;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public Emolumento quantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Set<DetalhePagamento> getDetalhePagamentos() {
        return detalhePagamentos;
    }

    public Emolumento detalhePagamentos(Set<DetalhePagamento> detalhePagamentos) {
        this.detalhePagamentos = detalhePagamentos;
        return this;
    }

    public Emolumento addDetalhePagamento(DetalhePagamento detalhePagamento) {
        this.detalhePagamentos.add(detalhePagamento);
        detalhePagamento.setEmolumento(this);
        return this;
    }

    public Emolumento removeDetalhePagamento(DetalhePagamento detalhePagamento) {
        this.detalhePagamentos.remove(detalhePagamento);
        detalhePagamento.setEmolumento(null);
        return this;
    }

    public void setDetalhePagamentos(Set<DetalhePagamento> detalhePagamentos) {
        this.detalhePagamentos = detalhePagamentos;
    }

    public Curso getCurso() {
        return curso;
    }

    public Emolumento curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Classe getClasse() {
        return classe;
    }

    public Emolumento classe(Classe classe) {
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
        if (!(o instanceof Emolumento)) {
            return false;
        }
        return id != null && id.equals(((Emolumento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Emolumento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", valorMulta=" + getValorMulta() +
            ", tempoMulta=" + getTempoMulta() +
            ", quantidade=" + getQuantidade() +
            "}";
    }
}
