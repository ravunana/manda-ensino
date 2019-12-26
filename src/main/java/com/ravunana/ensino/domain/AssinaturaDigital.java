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
 * A AssinaturaDigital.
 */
@Entity
@Table(name = "core_assinatura_digital")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "assinaturadigital")
public class AssinaturaDigital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Lob
    @Column(name = "assinatura")
    private byte[] assinatura;

    @Column(name = "assinatura_content_type")
    private String assinaturaContentType;

    
    @Column(name = "hashcode", unique = true)
    private String hashcode;

    @Column(name = "data")
    private ZonedDateTime data;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("assinaturaDigitals")
    private InstituicaoEnsino instituicao;

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

    public AssinaturaDigital tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getAssinatura() {
        return assinatura;
    }

    public AssinaturaDigital assinatura(byte[] assinatura) {
        this.assinatura = assinatura;
        return this;
    }

    public void setAssinatura(byte[] assinatura) {
        this.assinatura = assinatura;
    }

    public String getAssinaturaContentType() {
        return assinaturaContentType;
    }

    public AssinaturaDigital assinaturaContentType(String assinaturaContentType) {
        this.assinaturaContentType = assinaturaContentType;
        return this;
    }

    public void setAssinaturaContentType(String assinaturaContentType) {
        this.assinaturaContentType = assinaturaContentType;
    }

    public String getHashcode() {
        return hashcode;
    }

    public AssinaturaDigital hashcode(String hashcode) {
        this.hashcode = hashcode;
        return this;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public AssinaturaDigital data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public InstituicaoEnsino getInstituicao() {
        return instituicao;
    }

    public AssinaturaDigital instituicao(InstituicaoEnsino instituicaoEnsino) {
        this.instituicao = instituicaoEnsino;
        return this;
    }

    public void setInstituicao(InstituicaoEnsino instituicaoEnsino) {
        this.instituicao = instituicaoEnsino;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssinaturaDigital)) {
            return false;
        }
        return id != null && id.equals(((AssinaturaDigital) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssinaturaDigital{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", assinatura='" + getAssinatura() + "'" +
            ", assinaturaContentType='" + getAssinaturaContentType() + "'" +
            ", hashcode='" + getHashcode() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
