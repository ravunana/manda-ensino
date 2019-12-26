package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ContactoInstituicaoEnsino.
 */
@Entity
@Table(name = "core_contacto_instituicao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "contactoinstituicaoensino")
public class ContactoInstituicaoEnsino implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "tipo_contacto", nullable = false)
    private String tipoContacto;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "contacto", nullable = false, unique = true)
    private String contacto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contactoInstituicaoEnsinos")
    private InstituicaoEnsino instituicaoEnsino;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoContacto() {
        return tipoContacto;
    }

    public ContactoInstituicaoEnsino tipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
        return this;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getDescricao() {
        return descricao;
    }

    public ContactoInstituicaoEnsino descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContacto() {
        return contacto;
    }

    public ContactoInstituicaoEnsino contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public InstituicaoEnsino getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    public ContactoInstituicaoEnsino instituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
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
        if (!(o instanceof ContactoInstituicaoEnsino)) {
            return false;
        }
        return id != null && id.equals(((ContactoInstituicaoEnsino) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContactoInstituicaoEnsino{" +
            "id=" + getId() +
            ", tipoContacto='" + getTipoContacto() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", contacto='" + getContacto() + "'" +
            "}";
    }
}
