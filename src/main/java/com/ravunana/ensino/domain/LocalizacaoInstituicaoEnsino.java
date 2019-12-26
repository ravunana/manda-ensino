package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A LocalizacaoInstituicaoEnsino.
 */
@Entity
@Table(name = "core_localizacao_instituicao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "localizacaoinstituicaoensino")
public class LocalizacaoInstituicaoEnsino implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "provincia", nullable = false)
    private String provincia;

    @NotNull
    @Column(name = "municipio", nullable = false)
    private String municipio;

    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull
    @Size(max = 200)
    @Column(name = "rua", length = 200, nullable = false)
    private String rua;

    @NotNull
    @Size(max = 10)
    @Column(name = "quarteirao", length = 10, nullable = false)
    private String quarteirao;

    @Size(max = 10)
    @Column(name = "numero_porta", length = 10)
    private String numeroPorta;

    @Column(name = "caixa_postal")
    private String caixaPostal;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("localizacaoInstituicaoEnsinos")
    private InstituicaoEnsino instituicaoEnsino;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvincia() {
        return provincia;
    }

    public LocalizacaoInstituicaoEnsino provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public LocalizacaoInstituicaoEnsino municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public LocalizacaoInstituicaoEnsino bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public LocalizacaoInstituicaoEnsino rua(String rua) {
        this.rua = rua;
        return this;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public LocalizacaoInstituicaoEnsino quarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
        return this;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumeroPorta() {
        return numeroPorta;
    }

    public LocalizacaoInstituicaoEnsino numeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
        return this;
    }

    public void setNumeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
    }

    public String getCaixaPostal() {
        return caixaPostal;
    }

    public LocalizacaoInstituicaoEnsino caixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
        return this;
    }

    public void setCaixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
    }

    public InstituicaoEnsino getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    public LocalizacaoInstituicaoEnsino instituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
        return this;
    }

    public void setInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalizacaoInstituicaoEnsino)) {
            return false;
        }
        return id != null && id.equals(((LocalizacaoInstituicaoEnsino) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LocalizacaoInstituicaoEnsino{" +
            "id=" + getId() +
            ", provincia='" + getProvincia() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", quarteirao='" + getQuarteirao() + "'" +
            ", numeroPorta='" + getNumeroPorta() + "'" +
            ", caixaPostal='" + getCaixaPostal() + "'" +
            "}";
    }
}
