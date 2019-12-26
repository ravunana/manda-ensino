package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Deposito.
 */
@Entity
@Table(name = "fin_deposito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "deposito")
public class Deposito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero_talao", nullable = false, unique = true)
    private String numeroTalao;

    @NotNull
    @Column(name = "data_deposito", nullable = false)
    private LocalDate dataDeposito;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "saldo", precision = 21, scale = 2, nullable = false)
    private BigDecimal saldo;

    @OneToMany(mappedBy = "deposito")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalhePagamento> detalhePagamentos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("depositos")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("depositos")
    private MeioLiquidacao meioLiquidacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("depositos")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("depositos")
    private CoordenadaBancaria conta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTalao() {
        return numeroTalao;
    }

    public Deposito numeroTalao(String numeroTalao) {
        this.numeroTalao = numeroTalao;
        return this;
    }

    public void setNumeroTalao(String numeroTalao) {
        this.numeroTalao = numeroTalao;
    }

    public LocalDate getDataDeposito() {
        return dataDeposito;
    }

    public Deposito dataDeposito(LocalDate dataDeposito) {
        this.dataDeposito = dataDeposito;
        return this;
    }

    public void setDataDeposito(LocalDate dataDeposito) {
        this.dataDeposito = dataDeposito;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Deposito valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Deposito saldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Set<DetalhePagamento> getDetalhePagamentos() {
        return detalhePagamentos;
    }

    public Deposito detalhePagamentos(Set<DetalhePagamento> detalhePagamentos) {
        this.detalhePagamentos = detalhePagamentos;
        return this;
    }

    public Deposito addDetalhePagamento(DetalhePagamento detalhePagamento) {
        this.detalhePagamentos.add(detalhePagamento);
        detalhePagamento.setDeposito(this);
        return this;
    }

    public Deposito removeDetalhePagamento(DetalhePagamento detalhePagamento) {
        this.detalhePagamentos.remove(detalhePagamento);
        detalhePagamento.setDeposito(null);
        return this;
    }

    public void setDetalhePagamentos(Set<DetalhePagamento> detalhePagamentos) {
        this.detalhePagamentos = detalhePagamentos;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Deposito utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public MeioLiquidacao getMeioLiquidacao() {
        return meioLiquidacao;
    }

    public Deposito meioLiquidacao(MeioLiquidacao meioLiquidacao) {
        this.meioLiquidacao = meioLiquidacao;
        return this;
    }

    public void setMeioLiquidacao(MeioLiquidacao meioLiquidacao) {
        this.meioLiquidacao = meioLiquidacao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Deposito aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public CoordenadaBancaria getConta() {
        return conta;
    }

    public Deposito conta(CoordenadaBancaria coordenadaBancaria) {
        this.conta = coordenadaBancaria;
        return this;
    }

    public void setConta(CoordenadaBancaria coordenadaBancaria) {
        this.conta = coordenadaBancaria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deposito)) {
            return false;
        }
        return id != null && id.equals(((Deposito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Deposito{" +
            "id=" + getId() +
            ", numeroTalao='" + getNumeroTalao() + "'" +
            ", dataDeposito='" + getDataDeposito() + "'" +
            ", valor=" + getValor() +
            ", saldo=" + getSaldo() +
            "}";
    }
}
