package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A FichaMedica.
 */
@Entity
@Table(name = "sec_ficha_medica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fichamedica")
public class FichaMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "faz_educacao_fisica", nullable = false)
    private Boolean fazEducacaoFisica;

    @Column(name = "grupo_sanguinio")
    private String grupoSanguinio;

    @Column(name = "altura")
    private Integer altura;

    @Column(name = "peso")
    private Double peso;

    @NotNull
    @Column(name = "autoriza_medicamento", nullable = false)
    private Boolean autorizaMedicamento;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @Column(name = "nome_medico")
    private String nomeMedico;

    @Column(name = "contacto_medico")
    private String contactoMedico;

    @Column(name = "desmaio_constante")
    private Boolean desmaioConstante;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "complicacoes_saude")
    private String complicacoesSaude;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Aluno aluno;

    @ManyToOne
    @JsonIgnoreProperties("fichaMedicas")
    private User utilizador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFazEducacaoFisica() {
        return fazEducacaoFisica;
    }

    public FichaMedica fazEducacaoFisica(Boolean fazEducacaoFisica) {
        this.fazEducacaoFisica = fazEducacaoFisica;
        return this;
    }

    public void setFazEducacaoFisica(Boolean fazEducacaoFisica) {
        this.fazEducacaoFisica = fazEducacaoFisica;
    }

    public String getGrupoSanguinio() {
        return grupoSanguinio;
    }

    public FichaMedica grupoSanguinio(String grupoSanguinio) {
        this.grupoSanguinio = grupoSanguinio;
        return this;
    }

    public void setGrupoSanguinio(String grupoSanguinio) {
        this.grupoSanguinio = grupoSanguinio;
    }

    public Integer getAltura() {
        return altura;
    }

    public FichaMedica altura(Integer altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public FichaMedica peso(Double peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean isAutorizaMedicamento() {
        return autorizaMedicamento;
    }

    public FichaMedica autorizaMedicamento(Boolean autorizaMedicamento) {
        this.autorizaMedicamento = autorizaMedicamento;
        return this;
    }

    public void setAutorizaMedicamento(Boolean autorizaMedicamento) {
        this.autorizaMedicamento = autorizaMedicamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public FichaMedica observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public FichaMedica nomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
        return this;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getContactoMedico() {
        return contactoMedico;
    }

    public FichaMedica contactoMedico(String contactoMedico) {
        this.contactoMedico = contactoMedico;
        return this;
    }

    public void setContactoMedico(String contactoMedico) {
        this.contactoMedico = contactoMedico;
    }

    public Boolean isDesmaioConstante() {
        return desmaioConstante;
    }

    public FichaMedica desmaioConstante(Boolean desmaioConstante) {
        this.desmaioConstante = desmaioConstante;
        return this;
    }

    public void setDesmaioConstante(Boolean desmaioConstante) {
        this.desmaioConstante = desmaioConstante;
    }

    public String getComplicacoesSaude() {
        return complicacoesSaude;
    }

    public FichaMedica complicacoesSaude(String complicacoesSaude) {
        this.complicacoesSaude = complicacoesSaude;
        return this;
    }

    public void setComplicacoesSaude(String complicacoesSaude) {
        this.complicacoesSaude = complicacoesSaude;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public FichaMedica aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public FichaMedica utilizador(User user) {
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
        if (!(o instanceof FichaMedica)) {
            return false;
        }
        return id != null && id.equals(((FichaMedica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FichaMedica{" +
            "id=" + getId() +
            ", fazEducacaoFisica='" + isFazEducacaoFisica() + "'" +
            ", grupoSanguinio='" + getGrupoSanguinio() + "'" +
            ", altura=" + getAltura() +
            ", peso=" + getPeso() +
            ", autorizaMedicamento='" + isAutorizaMedicamento() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", nomeMedico='" + getNomeMedico() + "'" +
            ", contactoMedico='" + getContactoMedico() + "'" +
            ", desmaioConstante='" + isDesmaioConstante() + "'" +
            ", complicacoesSaude='" + getComplicacoesSaude() + "'" +
            "}";
    }
}
