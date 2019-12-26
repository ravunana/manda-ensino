package com.ravunana.ensino.domain;
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
 * /
 */
@Entity
@Table(name = "sec_categoria_aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "categoriaaluno")
public class CategoriaAluno implements Serializable {

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
    @Column(name = "desconto", nullable = false)
    private Double desconto;

    @Column(name = "paga_propina")
    private Boolean pagaPropina;

    @Column(name = "paga_multa")
    private Boolean pagaMulta;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Min(value = 1)
    @Column(name = "dia_pagamento")
    private Integer diaPagamento;

    @Column(name = "mes_atual")
    private Boolean mesAtual;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(mappedBy = "categoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Matricula> matriculas = new HashSet<>();

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

    public CategoriaAluno nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getDesconto() {
        return desconto;
    }

    public CategoriaAluno desconto(Double desconto) {
        this.desconto = desconto;
        return this;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Boolean isPagaPropina() {
        return pagaPropina;
    }

    public CategoriaAluno pagaPropina(Boolean pagaPropina) {
        this.pagaPropina = pagaPropina;
        return this;
    }

    public void setPagaPropina(Boolean pagaPropina) {
        this.pagaPropina = pagaPropina;
    }

    public Boolean isPagaMulta() {
        return pagaMulta;
    }

    public CategoriaAluno pagaMulta(Boolean pagaMulta) {
        this.pagaMulta = pagaMulta;
        return this;
    }

    public void setPagaMulta(Boolean pagaMulta) {
        this.pagaMulta = pagaMulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaAluno descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDiaPagamento() {
        return diaPagamento;
    }

    public CategoriaAluno diaPagamento(Integer diaPagamento) {
        this.diaPagamento = diaPagamento;
        return this;
    }

    public void setDiaPagamento(Integer diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    public Boolean isMesAtual() {
        return mesAtual;
    }

    public CategoriaAluno mesAtual(Boolean mesAtual) {
        this.mesAtual = mesAtual;
        return this;
    }

    public void setMesAtual(Boolean mesAtual) {
        this.mesAtual = mesAtual;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public CategoriaAluno ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public CategoriaAluno matriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
        return this;
    }

    public CategoriaAluno addMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setCategoria(this);
        return this;
    }

    public CategoriaAluno removeMatricula(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setCategoria(null);
        return this;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaAluno)) {
            return false;
        }
        return id != null && id.equals(((CategoriaAluno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoriaAluno{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", desconto=" + getDesconto() +
            ", pagaPropina='" + isPagaPropina() + "'" +
            ", pagaMulta='" + isPagaMulta() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", diaPagamento=" + getDiaPagamento() +
            ", mesAtual='" + isMesAtual() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
