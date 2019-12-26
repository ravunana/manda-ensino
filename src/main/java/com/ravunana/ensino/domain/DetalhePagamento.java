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
import java.time.ZonedDateTime;

/**
 * A DetalhePagamento.
 */
@Entity
@Table(name = "fin_detalhe_pagamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "detalhepagamento")
public class DetalhePagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "mensalidade", nullable = false)
    private Boolean mensalidade;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @DecimalMin(value = "0")
    @Column(name = "desconto")
    private Double desconto;

    @DecimalMin(value = "0")
    @Column(name = "multa")
    private Double multa;

    @DecimalMin(value = "0")
    @Column(name = "juro")
    private Double juro;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "vencimento")
    private LocalDate vencimento;

    @NotNull
    @Column(name = "quitado", nullable = false)
    private Boolean quitado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("detalhePagamentos")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("detalhePagamentos")
    private Emolumento emolumento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("detalhePagamentos")
    private Deposito deposito;

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

    public DetalhePagamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isMensalidade() {
        return mensalidade;
    }

    public DetalhePagamento mensalidade(Boolean mensalidade) {
        this.mensalidade = mensalidade;
        return this;
    }

    public void setMensalidade(Boolean mensalidade) {
        this.mensalidade = mensalidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public DetalhePagamento quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public DetalhePagamento valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public DetalhePagamento desconto(Double desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getMulta() {
        return multa;
    }

    public DetalhePagamento multa(Double multa) {
        this.multa = multa;
        return this;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Double getJuro() {
        return juro;
    }

    public DetalhePagamento juro(Double juro) {
        this.juro = juro;
        return this;
    }

    public void setJuro(Double juro) {
        this.juro = juro;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public DetalhePagamento data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public DetalhePagamento vencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public Boolean isQuitado() {
        return quitado;
    }

    public DetalhePagamento quitado(Boolean quitado) {
        this.quitado = quitado;
        return this;
    }

    public void setQuitado(Boolean quitado) {
        this.quitado = quitado;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public DetalhePagamento utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Emolumento getEmolumento() {
        return emolumento;
    }

    public DetalhePagamento emolumento(Emolumento emolumento) {
        this.emolumento = emolumento;
        return this;
    }

    public void setEmolumento(Emolumento emolumento) {
        this.emolumento = emolumento;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public DetalhePagamento deposito(Deposito deposito) {
        this.deposito = deposito;
        return this;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalhePagamento)) {
            return false;
        }
        return id != null && id.equals(((DetalhePagamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetalhePagamento{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", mensalidade='" + isMensalidade() + "'" +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", desconto=" + getDesconto() +
            ", multa=" + getMulta() +
            ", juro=" + getJuro() +
            ", data='" + getData() + "'" +
            ", vencimento='" + getVencimento() + "'" +
            ", quitado='" + isQuitado() + "'" +
            "}";
    }
}
