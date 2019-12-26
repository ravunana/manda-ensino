package com.ravunana.ensino.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DetalheOcorrencia.
 */
@Entity
@Table(name = "sec_detalhe_ocorrencia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "detalheocorrencia")
public class DetalheOcorrencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "de")
    private LocalDate de;

    @Column(name = "ate")
    private LocalDate ate;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "motivo", nullable = false)
    private String motivo;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Ocorrencia ocorrencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDe() {
        return de;
    }

    public DetalheOcorrencia de(LocalDate de) {
        this.de = de;
        return this;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public DetalheOcorrencia ate(LocalDate ate) {
        this.ate = ate;
        return this;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getMotivo() {
        return motivo;
    }

    public DetalheOcorrencia motivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public DetalheOcorrencia ocorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
        return this;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalheOcorrencia)) {
            return false;
        }
        return id != null && id.equals(((DetalheOcorrencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetalheOcorrencia{" +
            "id=" + getId() +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", motivo='" + getMotivo() + "'" +
            "}";
    }
}
