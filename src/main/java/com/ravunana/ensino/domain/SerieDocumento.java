package com.ravunana.ensino.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A SerieDocumento.
 */
@Entity
@Table(name = "core_serie_documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "seriedocumento")
public class SerieDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "serie")
    private String serie;

    @Column(name = "sequencia")
    private Integer sequencia;

    @Column(name = "entidade")
    private String entidade;

    @OneToOne
    @JoinColumn(unique = true)
    private InstituicaoEnsino instituicaoEnsino;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public SerieDocumento serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public SerieDocumento sequencia(Integer sequencia) {
        this.sequencia = sequencia;
        return this;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public String getEntidade() {
        return entidade;
    }

    public SerieDocumento entidade(String entidade) {
        this.entidade = entidade;
        return this;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public InstituicaoEnsino getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    public SerieDocumento instituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
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
        if (!(o instanceof SerieDocumento)) {
            return false;
        }
        return id != null && id.equals(((SerieDocumento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SerieDocumento{" +
            "id=" + getId() +
            ", serie='" + getSerie() + "'" +
            ", sequencia=" + getSequencia() +
            ", entidade='" + getEntidade() + "'" +
            "}";
    }
}
