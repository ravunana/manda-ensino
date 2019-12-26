package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PlanoActividade.
 */
@Entity
@Table(name = "pdg_plano_actividade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "planoactividade")
public class PlanoActividade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Min(value = 1)
    @Column(name = "numero_actividade", unique = true)
    private Integer numeroActividade;

    @NotNull
    @Column(name = "atividade", nullable = false)
    private String atividade;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "objectivos", nullable = false)
    private String objectivos;

    @NotNull
    @Column(name = "de", nullable = false)
    private LocalDate de;

    @NotNull
    @Column(name = "ate", nullable = false)
    private LocalDate ate;

    @NotNull
    @Column(name = "responsavel", nullable = false)
    private String responsavel;

    @Column(name = "local")
    private String local;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @Column(name = "participantes")
    private String participantes;

    @Column(name = "co_responsavel")
    private String coResponsavel;

    @NotNull
    @Column(name = "status_actividade", nullable = false)
    private String statusActividade;

    @NotNull
    @Column(name = "ano_lectivo", nullable = false)
    private LocalDate anoLectivo;

    @Column(name = "periodo_lectivo")
    private String periodoLectivo;

    @ManyToOne
    @JsonIgnoreProperties("planoActividades")
    private User utilizador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroActividade() {
        return numeroActividade;
    }

    public PlanoActividade numeroActividade(Integer numeroActividade) {
        this.numeroActividade = numeroActividade;
        return this;
    }

    public void setNumeroActividade(Integer numeroActividade) {
        this.numeroActividade = numeroActividade;
    }

    public String getAtividade() {
        return atividade;
    }

    public PlanoActividade atividade(String atividade) {
        this.atividade = atividade;
        return this;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getObjectivos() {
        return objectivos;
    }

    public PlanoActividade objectivos(String objectivos) {
        this.objectivos = objectivos;
        return this;
    }

    public void setObjectivos(String objectivos) {
        this.objectivos = objectivos;
    }

    public LocalDate getDe() {
        return de;
    }

    public PlanoActividade de(LocalDate de) {
        this.de = de;
        return this;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public PlanoActividade ate(LocalDate ate) {
        this.ate = ate;
        return this;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public PlanoActividade responsavel(String responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getLocal() {
        return local;
    }

    public PlanoActividade local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getObservacao() {
        return observacao;
    }

    public PlanoActividade observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getParticipantes() {
        return participantes;
    }

    public PlanoActividade participantes(String participantes) {
        this.participantes = participantes;
        return this;
    }

    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }

    public String getCoResponsavel() {
        return coResponsavel;
    }

    public PlanoActividade coResponsavel(String coResponsavel) {
        this.coResponsavel = coResponsavel;
        return this;
    }

    public void setCoResponsavel(String coResponsavel) {
        this.coResponsavel = coResponsavel;
    }

    public String getStatusActividade() {
        return statusActividade;
    }

    public PlanoActividade statusActividade(String statusActividade) {
        this.statusActividade = statusActividade;
        return this;
    }

    public void setStatusActividade(String statusActividade) {
        this.statusActividade = statusActividade;
    }

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public PlanoActividade anoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
        return this;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getPeriodoLectivo() {
        return periodoLectivo;
    }

    public PlanoActividade periodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
        return this;
    }

    public void setPeriodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public PlanoActividade utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoActividade)) {
            return false;
        }
        return id != null && id.equals(((PlanoActividade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlanoActividade{" +
            "id=" + getId() +
            ", numeroActividade=" + getNumeroActividade() +
            ", atividade='" + getAtividade() + "'" +
            ", objectivos='" + getObjectivos() + "'" +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", responsavel='" + getResponsavel() + "'" +
            ", local='" + getLocal() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", participantes='" + getParticipantes() + "'" +
            ", coResponsavel='" + getCoResponsavel() + "'" +
            ", statusActividade='" + getStatusActividade() + "'" +
            ", anoLectivo='" + getAnoLectivo() + "'" +
            ", periodoLectivo='" + getPeriodoLectivo() + "'" +
            "}";
    }
}
