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
 * A Ocorrencia.
 */
@Entity
@Table(name = "sec_ocorrencia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ocorrencia")
public class Ocorrencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "tipo")
    private String tipo;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @NotNull
    @Column(name = "reportar_encarregado", nullable = false)
    private Boolean reportarEncarregado;

    @ManyToOne
    @JsonIgnoreProperties("ocorrencias")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ocorrencias")
    private Matricula matricula;

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

    public Ocorrencia tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Ocorrencia data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public Ocorrencia numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isReportarEncarregado() {
        return reportarEncarregado;
    }

    public Ocorrencia reportarEncarregado(Boolean reportarEncarregado) {
        this.reportarEncarregado = reportarEncarregado;
        return this;
    }

    public void setReportarEncarregado(Boolean reportarEncarregado) {
        this.reportarEncarregado = reportarEncarregado;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Ocorrencia utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public Ocorrencia matricula(Matricula matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ocorrencia)) {
            return false;
        }
        return id != null && id.equals(((Ocorrencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ocorrencia{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", data='" + getData() + "'" +
            ", numero='" + getNumero() + "'" +
            ", reportarEncarregado='" + isReportarEncarregado() + "'" +
            "}";
    }
}
