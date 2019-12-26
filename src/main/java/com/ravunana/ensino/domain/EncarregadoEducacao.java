package com.ravunana.ensino.domain;
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
 * A EncarregadoEducacao.
 */
@Entity
@Table(name = "sec_encarregado_educacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "encarregadoeducacao")
public class EncarregadoEducacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "faixa_salarial", precision = 21, scale = 2)
    private BigDecimal faixaSalarial;

    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Column(name = "contacto_empresa")
    private String contactoEmpresa;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "encarregadoEducacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aluno> alunos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfissao() {
        return profissao;
    }

    public EncarregadoEducacao profissao(String profissao) {
        this.profissao = profissao;
        return this;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getCargo() {
        return cargo;
    }

    public EncarregadoEducacao cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getFaixaSalarial() {
        return faixaSalarial;
    }

    public EncarregadoEducacao faixaSalarial(BigDecimal faixaSalarial) {
        this.faixaSalarial = faixaSalarial;
        return this;
    }

    public void setFaixaSalarial(BigDecimal faixaSalarial) {
        this.faixaSalarial = faixaSalarial;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public EncarregadoEducacao nomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
        return this;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getContactoEmpresa() {
        return contactoEmpresa;
    }

    public EncarregadoEducacao contactoEmpresa(String contactoEmpresa) {
        this.contactoEmpresa = contactoEmpresa;
        return this;
    }

    public void setContactoEmpresa(String contactoEmpresa) {
        this.contactoEmpresa = contactoEmpresa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public EncarregadoEducacao pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public EncarregadoEducacao alunos(Set<Aluno> alunos) {
        this.alunos = alunos;
        return this;
    }

    public EncarregadoEducacao addAluno(Aluno aluno) {
        this.alunos.add(aluno);
        aluno.setEncarregadoEducacao(this);
        return this;
    }

    public EncarregadoEducacao removeAluno(Aluno aluno) {
        this.alunos.remove(aluno);
        aluno.setEncarregadoEducacao(null);
        return this;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncarregadoEducacao)) {
            return false;
        }
        return id != null && id.equals(((EncarregadoEducacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EncarregadoEducacao{" +
            "id=" + getId() +
            ", profissao='" + getProfissao() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", faixaSalarial=" + getFaixaSalarial() +
            ", nomeEmpresa='" + getNomeEmpresa() + "'" +
            ", contactoEmpresa='" + getContactoEmpresa() + "'" +
            "}";
    }
}
