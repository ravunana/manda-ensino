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
 * A Contrato.
 */
@Entity
@Table(name = "sec_contrato")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "contrato")
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "de", nullable = false)
    private LocalDate de;

    @NotNull
    @Column(name = "ate", nullable = false)
    private LocalDate ate;

    @NotNull
    @Column(name = "contrato", nullable = false)
    private String contrato;

    @NotNull
    @Column(name = "aceita_termos", nullable = false)
    private Boolean aceitaTermos;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @Lob
    @Column(name = "termos")
    private byte[] termos;

    @Column(name = "termos_content_type")
    private String termosContentType;

    @NotNull
    @Column(name = "em_vigor", nullable = false)
    private Boolean emVigor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contratoes")
    private Aluno aluno;

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

    public Contrato de(LocalDate de) {
        this.de = de;
        return this;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public Contrato ate(LocalDate ate) {
        this.ate = ate;
        return this;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getContrato() {
        return contrato;
    }

    public Contrato contrato(String contrato) {
        this.contrato = contrato;
        return this;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Boolean isAceitaTermos() {
        return aceitaTermos;
    }

    public Contrato aceitaTermos(Boolean aceitaTermos) {
        this.aceitaTermos = aceitaTermos;
        return this;
    }

    public void setAceitaTermos(Boolean aceitaTermos) {
        this.aceitaTermos = aceitaTermos;
    }

    public String getObservacao() {
        return observacao;
    }

    public Contrato observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public byte[] getTermos() {
        return termos;
    }

    public Contrato termos(byte[] termos) {
        this.termos = termos;
        return this;
    }

    public void setTermos(byte[] termos) {
        this.termos = termos;
    }

    public String getTermosContentType() {
        return termosContentType;
    }

    public Contrato termosContentType(String termosContentType) {
        this.termosContentType = termosContentType;
        return this;
    }

    public void setTermosContentType(String termosContentType) {
        this.termosContentType = termosContentType;
    }

    public Boolean isEmVigor() {
        return emVigor;
    }

    public Contrato emVigor(Boolean emVigor) {
        this.emVigor = emVigor;
        return this;
    }

    public void setEmVigor(Boolean emVigor) {
        this.emVigor = emVigor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Contrato aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contrato)) {
            return false;
        }
        return id != null && id.equals(((Contrato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contrato{" +
            "id=" + getId() +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", contrato='" + getContrato() + "'" +
            ", aceitaTermos='" + isAceitaTermos() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", termos='" + getTermos() + "'" +
            ", termosContentType='" + getTermosContentType() + "'" +
            ", emVigor='" + isEmVigor() + "'" +
            "}";
    }
}
