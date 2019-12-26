package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.ravunana.ensino.domain.enumeration.LookupType;

/**
 * A LookupItem.
 */
@Entity
@Table(name = "core_lookup_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lookupitem")
public class LookupItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "valor")
    private String valor;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LookupType type;

    @ManyToOne
    @JsonIgnoreProperties("lookupItems")
    private Lookup lookup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public LookupItem valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public LookupItem nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LookupType getType() {
        return type;
    }

    public LookupItem type(LookupType type) {
        this.type = type;
        return this;
    }

    public void setType(LookupType type) {
        this.type = type;
    }

    public Lookup getLookup() {
        return lookup;
    }

    public LookupItem lookup(Lookup lookup) {
        this.lookup = lookup;
        return this;
    }

    public void setLookup(Lookup lookup) {
        this.lookup = lookup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LookupItem)) {
            return false;
        }
        return id != null && id.equals(((LookupItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LookupItem{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            ", nome='" + getNome() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
