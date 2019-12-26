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
 * A DocumentoMatricula.
 */
@Entity
@Table(name = "sec_documento_matricula")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "documentomatricula")
public class DocumentoMatricula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "fotografia", nullable = false)
    private Boolean fotografia;

    @NotNull
    @Column(name = "certificado", nullable = false)
    private Boolean certificado;

    @NotNull
    @Column(name = "bilhete", nullable = false)
    private Boolean bilhete;

    @Column(name = "resenciamento_militar")
    private Boolean resenciamentoMilitar;

    @Column(name = "cartao_vacina")
    private Boolean cartaoVacina;

    @Column(name = "atestado_medico")
    private Boolean atestadoMedico;

    @Column(name = "ficha_trnasferencia")
    private Boolean fichaTrnasferencia;

    @Column(name = "historico_escolar")
    private Boolean historicoEscolar;

    @Column(name = "cedula")
    private Boolean cedula;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ano_lectivo")
    private Integer anoLectivo;

    @Column(name = "data")
    private ZonedDateTime data;

    @ManyToOne
    @JsonIgnoreProperties("documentoMatriculas")
    private Matricula matricula;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFotografia() {
        return fotografia;
    }

    public DocumentoMatricula fotografia(Boolean fotografia) {
        this.fotografia = fotografia;
        return this;
    }

    public void setFotografia(Boolean fotografia) {
        this.fotografia = fotografia;
    }

    public Boolean isCertificado() {
        return certificado;
    }

    public DocumentoMatricula certificado(Boolean certificado) {
        this.certificado = certificado;
        return this;
    }

    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

    public Boolean isBilhete() {
        return bilhete;
    }

    public DocumentoMatricula bilhete(Boolean bilhete) {
        this.bilhete = bilhete;
        return this;
    }

    public void setBilhete(Boolean bilhete) {
        this.bilhete = bilhete;
    }

    public Boolean isResenciamentoMilitar() {
        return resenciamentoMilitar;
    }

    public DocumentoMatricula resenciamentoMilitar(Boolean resenciamentoMilitar) {
        this.resenciamentoMilitar = resenciamentoMilitar;
        return this;
    }

    public void setResenciamentoMilitar(Boolean resenciamentoMilitar) {
        this.resenciamentoMilitar = resenciamentoMilitar;
    }

    public Boolean isCartaoVacina() {
        return cartaoVacina;
    }

    public DocumentoMatricula cartaoVacina(Boolean cartaoVacina) {
        this.cartaoVacina = cartaoVacina;
        return this;
    }

    public void setCartaoVacina(Boolean cartaoVacina) {
        this.cartaoVacina = cartaoVacina;
    }

    public Boolean isAtestadoMedico() {
        return atestadoMedico;
    }

    public DocumentoMatricula atestadoMedico(Boolean atestadoMedico) {
        this.atestadoMedico = atestadoMedico;
        return this;
    }

    public void setAtestadoMedico(Boolean atestadoMedico) {
        this.atestadoMedico = atestadoMedico;
    }

    public Boolean isFichaTrnasferencia() {
        return fichaTrnasferencia;
    }

    public DocumentoMatricula fichaTrnasferencia(Boolean fichaTrnasferencia) {
        this.fichaTrnasferencia = fichaTrnasferencia;
        return this;
    }

    public void setFichaTrnasferencia(Boolean fichaTrnasferencia) {
        this.fichaTrnasferencia = fichaTrnasferencia;
    }

    public Boolean isHistoricoEscolar() {
        return historicoEscolar;
    }

    public DocumentoMatricula historicoEscolar(Boolean historicoEscolar) {
        this.historicoEscolar = historicoEscolar;
        return this;
    }

    public void setHistoricoEscolar(Boolean historicoEscolar) {
        this.historicoEscolar = historicoEscolar;
    }

    public Boolean isCedula() {
        return cedula;
    }

    public DocumentoMatricula cedula(Boolean cedula) {
        this.cedula = cedula;
        return this;
    }

    public void setCedula(Boolean cedula) {
        this.cedula = cedula;
    }

    public String getDescricao() {
        return descricao;
    }

    public DocumentoMatricula descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAnoLectivo() {
        return anoLectivo;
    }

    public DocumentoMatricula anoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
        return this;
    }

    public void setAnoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public DocumentoMatricula data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public DocumentoMatricula matricula(Matricula matricula) {
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
        if (!(o instanceof DocumentoMatricula)) {
            return false;
        }
        return id != null && id.equals(((DocumentoMatricula) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentoMatricula{" +
            "id=" + getId() +
            ", fotografia='" + isFotografia() + "'" +
            ", certificado='" + isCertificado() + "'" +
            ", bilhete='" + isBilhete() + "'" +
            ", resenciamentoMilitar='" + isResenciamentoMilitar() + "'" +
            ", cartaoVacina='" + isCartaoVacina() + "'" +
            ", atestadoMedico='" + isAtestadoMedico() + "'" +
            ", fichaTrnasferencia='" + isFichaTrnasferencia() + "'" +
            ", historicoEscolar='" + isHistoricoEscolar() + "'" +
            ", cedula='" + isCedula() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", anoLectivo=" + getAnoLectivo() +
            ", data='" + getData() + "'" +
            "}";
    }
}
