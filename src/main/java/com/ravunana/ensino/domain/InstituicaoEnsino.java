package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A InstituicaoEnsino.
 */
@Entity
@Table(name = "core_insituicao_ensino")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "instituicaoensino")
public class InstituicaoEnsino implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    
    @Lob
    @Column(name = "logotipo", nullable = false)
    private byte[] logotipo;

    @Column(name = "logotipo_content_type", nullable = false)
    private String logotipoContentType;

    @Column(name = "fundacao")
    private LocalDate fundacao;

    
    @Column(name = "numero", unique = true)
    private String numero;

    @Column(name = "tipo_vinculo")
    private String tipoVinculo;

    @Column(name = "unidade_pagadora")
    private String unidadePagadora;

    @Column(name = "unidade_organica")
    private String unidadeOrganica;

    @Column(name = "tipo_instalacao")
    private String tipoInstalacao;

    @Column(name = "dimensao")
    private String dimensao;

    @Lob
    @Column(name = "carimbo")
    private byte[] carimbo;

    @Column(name = "carimbo_content_type")
    private String carimboContentType;

    @NotNull
    @Column(name = "sede", nullable = false)
    private Boolean sede;

    @OneToMany(mappedBy = "hierarquia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InstituicaoEnsino> instituicaoEnsinos = new HashSet<>();

    @OneToMany(mappedBy = "instituicaoEnsino")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinos = new HashSet<>();

    @OneToMany(mappedBy = "instituicaoEnsino")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactoInstituicaoEnsino> contactoInstituicaoEnsinos = new HashSet<>();

    @OneToMany(mappedBy = "instituicaoEnsino")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LicencaSoftware> licencaSoftwares = new HashSet<>();

    @OneToMany(mappedBy = "instituicao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AssinaturaDigital> assinaturaDigitals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("instituicaoEnsinos")
    private User utilizador;

    @ManyToOne
    @JsonIgnoreProperties("instituicaoEnsinos")
    private InstituicaoEnsino hierarquia;

    @ManyToMany(mappedBy = "instituicaoEnsinos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<CoordenadaBancaria> coordenadaBancarias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public InstituicaoEnsino nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public InstituicaoEnsino logotipo(byte[] logotipo) {
        this.logotipo = logotipo;
        return this;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return logotipoContentType;
    }

    public InstituicaoEnsino logotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
        return this;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
    }

    public LocalDate getFundacao() {
        return fundacao;
    }

    public InstituicaoEnsino fundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
        return this;
    }

    public void setFundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
    }

    public String getNumero() {
        return numero;
    }

    public InstituicaoEnsino numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoVinculo() {
        return tipoVinculo;
    }

    public InstituicaoEnsino tipoVinculo(String tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
        return this;
    }

    public void setTipoVinculo(String tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    public String getUnidadePagadora() {
        return unidadePagadora;
    }

    public InstituicaoEnsino unidadePagadora(String unidadePagadora) {
        this.unidadePagadora = unidadePagadora;
        return this;
    }

    public void setUnidadePagadora(String unidadePagadora) {
        this.unidadePagadora = unidadePagadora;
    }

    public String getUnidadeOrganica() {
        return unidadeOrganica;
    }

    public InstituicaoEnsino unidadeOrganica(String unidadeOrganica) {
        this.unidadeOrganica = unidadeOrganica;
        return this;
    }

    public void setUnidadeOrganica(String unidadeOrganica) {
        this.unidadeOrganica = unidadeOrganica;
    }

    public String getTipoInstalacao() {
        return tipoInstalacao;
    }

    public InstituicaoEnsino tipoInstalacao(String tipoInstalacao) {
        this.tipoInstalacao = tipoInstalacao;
        return this;
    }

    public void setTipoInstalacao(String tipoInstalacao) {
        this.tipoInstalacao = tipoInstalacao;
    }

    public String getDimensao() {
        return dimensao;
    }

    public InstituicaoEnsino dimensao(String dimensao) {
        this.dimensao = dimensao;
        return this;
    }

    public void setDimensao(String dimensao) {
        this.dimensao = dimensao;
    }

    public byte[] getCarimbo() {
        return carimbo;
    }

    public InstituicaoEnsino carimbo(byte[] carimbo) {
        this.carimbo = carimbo;
        return this;
    }

    public void setCarimbo(byte[] carimbo) {
        this.carimbo = carimbo;
    }

    public String getCarimboContentType() {
        return carimboContentType;
    }

    public InstituicaoEnsino carimboContentType(String carimboContentType) {
        this.carimboContentType = carimboContentType;
        return this;
    }

    public void setCarimboContentType(String carimboContentType) {
        this.carimboContentType = carimboContentType;
    }

    public Boolean isSede() {
        return sede;
    }

    public InstituicaoEnsino sede(Boolean sede) {
        this.sede = sede;
        return this;
    }

    public void setSede(Boolean sede) {
        this.sede = sede;
    }

    public Set<InstituicaoEnsino> getInstituicaoEnsinos() {
        return instituicaoEnsinos;
    }

    public InstituicaoEnsino instituicaoEnsinos(Set<InstituicaoEnsino> instituicaoEnsinos) {
        this.instituicaoEnsinos = instituicaoEnsinos;
        return this;
    }

    public InstituicaoEnsino addInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsinos.add(instituicaoEnsino);
        instituicaoEnsino.setHierarquia(this);
        return this;
    }

    public InstituicaoEnsino removeInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsinos.remove(instituicaoEnsino);
        instituicaoEnsino.setHierarquia(null);
        return this;
    }

    public void setInstituicaoEnsinos(Set<InstituicaoEnsino> instituicaoEnsinos) {
        this.instituicaoEnsinos = instituicaoEnsinos;
    }

    public Set<LocalizacaoInstituicaoEnsino> getLocalizacaoInstituicaoEnsinos() {
        return localizacaoInstituicaoEnsinos;
    }

    public InstituicaoEnsino localizacaoInstituicaoEnsinos(Set<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinos) {
        this.localizacaoInstituicaoEnsinos = localizacaoInstituicaoEnsinos;
        return this;
    }

    public InstituicaoEnsino addLocalizacaoInstituicaoEnsino(LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino) {
        this.localizacaoInstituicaoEnsinos.add(localizacaoInstituicaoEnsino);
        localizacaoInstituicaoEnsino.setInstituicaoEnsino(this);
        return this;
    }

    public InstituicaoEnsino removeLocalizacaoInstituicaoEnsino(LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino) {
        this.localizacaoInstituicaoEnsinos.remove(localizacaoInstituicaoEnsino);
        localizacaoInstituicaoEnsino.setInstituicaoEnsino(null);
        return this;
    }

    public void setLocalizacaoInstituicaoEnsinos(Set<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinos) {
        this.localizacaoInstituicaoEnsinos = localizacaoInstituicaoEnsinos;
    }

    public Set<ContactoInstituicaoEnsino> getContactoInstituicaoEnsinos() {
        return contactoInstituicaoEnsinos;
    }

    public InstituicaoEnsino contactoInstituicaoEnsinos(Set<ContactoInstituicaoEnsino> contactoInstituicaoEnsinos) {
        this.contactoInstituicaoEnsinos = contactoInstituicaoEnsinos;
        return this;
    }

    public InstituicaoEnsino addContactoInstituicaoEnsino(ContactoInstituicaoEnsino contactoInstituicaoEnsino) {
        this.contactoInstituicaoEnsinos.add(contactoInstituicaoEnsino);
        contactoInstituicaoEnsino.setInstituicaoEnsino(this);
        return this;
    }

    public InstituicaoEnsino removeContactoInstituicaoEnsino(ContactoInstituicaoEnsino contactoInstituicaoEnsino) {
        this.contactoInstituicaoEnsinos.remove(contactoInstituicaoEnsino);
        contactoInstituicaoEnsino.setInstituicaoEnsino(null);
        return this;
    }

    public void setContactoInstituicaoEnsinos(Set<ContactoInstituicaoEnsino> contactoInstituicaoEnsinos) {
        this.contactoInstituicaoEnsinos = contactoInstituicaoEnsinos;
    }

    public Set<LicencaSoftware> getLicencaSoftwares() {
        return licencaSoftwares;
    }

    public InstituicaoEnsino licencaSoftwares(Set<LicencaSoftware> licencaSoftwares) {
        this.licencaSoftwares = licencaSoftwares;
        return this;
    }

    public InstituicaoEnsino addLicencaSoftware(LicencaSoftware licencaSoftware) {
        this.licencaSoftwares.add(licencaSoftware);
        licencaSoftware.setInstituicaoEnsino(this);
        return this;
    }

    public InstituicaoEnsino removeLicencaSoftware(LicencaSoftware licencaSoftware) {
        this.licencaSoftwares.remove(licencaSoftware);
        licencaSoftware.setInstituicaoEnsino(null);
        return this;
    }

    public void setLicencaSoftwares(Set<LicencaSoftware> licencaSoftwares) {
        this.licencaSoftwares = licencaSoftwares;
    }

    public Set<AssinaturaDigital> getAssinaturaDigitals() {
        return assinaturaDigitals;
    }

    public InstituicaoEnsino assinaturaDigitals(Set<AssinaturaDigital> assinaturaDigitals) {
        this.assinaturaDigitals = assinaturaDigitals;
        return this;
    }

    public InstituicaoEnsino addAssinaturaDigital(AssinaturaDigital assinaturaDigital) {
        this.assinaturaDigitals.add(assinaturaDigital);
        assinaturaDigital.setInstituicao(this);
        return this;
    }

    public InstituicaoEnsino removeAssinaturaDigital(AssinaturaDigital assinaturaDigital) {
        this.assinaturaDigitals.remove(assinaturaDigital);
        assinaturaDigital.setInstituicao(null);
        return this;
    }

    public void setAssinaturaDigitals(Set<AssinaturaDigital> assinaturaDigitals) {
        this.assinaturaDigitals = assinaturaDigitals;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public InstituicaoEnsino utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public InstituicaoEnsino getHierarquia() {
        return hierarquia;
    }

    public InstituicaoEnsino hierarquia(InstituicaoEnsino instituicaoEnsino) {
        this.hierarquia = instituicaoEnsino;
        return this;
    }

    public void setHierarquia(InstituicaoEnsino instituicaoEnsino) {
        this.hierarquia = instituicaoEnsino;
    }

    public Set<CoordenadaBancaria> getCoordenadaBancarias() {
        return coordenadaBancarias;
    }

    public InstituicaoEnsino coordenadaBancarias(Set<CoordenadaBancaria> coordenadaBancarias) {
        this.coordenadaBancarias = coordenadaBancarias;
        return this;
    }

    public InstituicaoEnsino addCoordenadaBancaria(CoordenadaBancaria coordenadaBancaria) {
        this.coordenadaBancarias.add(coordenadaBancaria);
        coordenadaBancaria.getInstituicaoEnsinos().add(this);
        return this;
    }

    public InstituicaoEnsino removeCoordenadaBancaria(CoordenadaBancaria coordenadaBancaria) {
        this.coordenadaBancarias.remove(coordenadaBancaria);
        coordenadaBancaria.getInstituicaoEnsinos().remove(this);
        return this;
    }

    public void setCoordenadaBancarias(Set<CoordenadaBancaria> coordenadaBancarias) {
        this.coordenadaBancarias = coordenadaBancarias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstituicaoEnsino)) {
            return false;
        }
        return id != null && id.equals(((InstituicaoEnsino) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InstituicaoEnsino{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", logotipo='" + getLogotipo() + "'" +
            ", logotipoContentType='" + getLogotipoContentType() + "'" +
            ", fundacao='" + getFundacao() + "'" +
            ", numero='" + getNumero() + "'" +
            ", tipoVinculo='" + getTipoVinculo() + "'" +
            ", unidadePagadora='" + getUnidadePagadora() + "'" +
            ", unidadeOrganica='" + getUnidadeOrganica() + "'" +
            ", tipoInstalacao='" + getTipoInstalacao() + "'" +
            ", dimensao='" + getDimensao() + "'" +
            ", carimbo='" + getCarimbo() + "'" +
            ", carimboContentType='" + getCarimboContentType() + "'" +
            ", sede='" + isSede() + "'" +
            "}";
    }
}
