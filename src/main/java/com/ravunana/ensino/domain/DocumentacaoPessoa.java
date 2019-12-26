package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DocumentacaoPessoa.
 */
@Entity
@Table(name = "core_documento_pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "documentacaopessoa")
public class DocumentacaoPessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "emissao")
    private LocalDate emissao;

    @Column(name = "validade")
    private LocalDate validade;

    @Column(name = "naturalidade")
    private String naturalidade;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Column(name = "local_emissao")
    private String localEmissao;

    
    @Column(name = "nif", unique = true)
    private String nif;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("documentacaoPessoas")
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public DocumentacaoPessoa tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public DocumentacaoPessoa numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getEmissao() {
        return emissao;
    }

    public DocumentacaoPessoa emissao(LocalDate emissao) {
        this.emissao = emissao;
        return this;
    }

    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public DocumentacaoPessoa validade(LocalDate validade) {
        this.validade = validade;
        return this;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public DocumentacaoPessoa naturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
        return this;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public DocumentacaoPessoa nacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
        return this;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getLocalEmissao() {
        return localEmissao;
    }

    public DocumentacaoPessoa localEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
        return this;
    }

    public void setLocalEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
    }

    public String getNif() {
        return nif;
    }

    public DocumentacaoPessoa nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public DocumentacaoPessoa pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentacaoPessoa)) {
            return false;
        }
        return id != null && id.equals(((DocumentacaoPessoa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentacaoPessoa{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", numero='" + getNumero() + "'" +
            ", emissao='" + getEmissao() + "'" +
            ", validade='" + getValidade() + "'" +
            ", naturalidade='" + getNaturalidade() + "'" +
            ", nacionalidade='" + getNacionalidade() + "'" +
            ", localEmissao='" + getLocalEmissao() + "'" +
            ", nif='" + getNif() + "'" +
            "}";
    }
}
