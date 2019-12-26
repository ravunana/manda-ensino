package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.InstituicaoEnsino} entity.
 */
public class InstituicaoEnsinoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    
    @Lob
    private byte[] logotipo;

    private String logotipoContentType;
    private LocalDate fundacao;

    
    private String numero;

    private String tipoVinculo;

    private String unidadePagadora;

    private String unidadeOrganica;

    private String tipoInstalacao;

    private String dimensao;

    @Lob
    private byte[] carimbo;

    private String carimboContentType;
    @NotNull
    private Boolean sede;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long hierarquiaId;

    private String hierarquiaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return logotipoContentType;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
    }

    public LocalDate getFundacao() {
        return fundacao;
    }

    public void setFundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoVinculo() {
        return tipoVinculo;
    }

    public void setTipoVinculo(String tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    public String getUnidadePagadora() {
        return unidadePagadora;
    }

    public void setUnidadePagadora(String unidadePagadora) {
        this.unidadePagadora = unidadePagadora;
    }

    public String getUnidadeOrganica() {
        return unidadeOrganica;
    }

    public void setUnidadeOrganica(String unidadeOrganica) {
        this.unidadeOrganica = unidadeOrganica;
    }

    public String getTipoInstalacao() {
        return tipoInstalacao;
    }

    public void setTipoInstalacao(String tipoInstalacao) {
        this.tipoInstalacao = tipoInstalacao;
    }

    public String getDimensao() {
        return dimensao;
    }

    public void setDimensao(String dimensao) {
        this.dimensao = dimensao;
    }

    public byte[] getCarimbo() {
        return carimbo;
    }

    public void setCarimbo(byte[] carimbo) {
        this.carimbo = carimbo;
    }

    public String getCarimboContentType() {
        return carimboContentType;
    }

    public void setCarimboContentType(String carimboContentType) {
        this.carimboContentType = carimboContentType;
    }

    public Boolean isSede() {
        return sede;
    }

    public void setSede(Boolean sede) {
        this.sede = sede;
    }

    public Long getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Long userId) {
        this.utilizadorId = userId;
    }

    public String getUtilizadorLogin() {
        return utilizadorLogin;
    }

    public void setUtilizadorLogin(String userLogin) {
        this.utilizadorLogin = userLogin;
    }

    public Long getHierarquiaId() {
        return hierarquiaId;
    }

    public void setHierarquiaId(Long instituicaoEnsinoId) {
        this.hierarquiaId = instituicaoEnsinoId;
    }

    public String getHierarquiaNome() {
        return hierarquiaNome;
    }

    public void setHierarquiaNome(String instituicaoEnsinoNome) {
        this.hierarquiaNome = instituicaoEnsinoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstituicaoEnsinoDTO instituicaoEnsinoDTO = (InstituicaoEnsinoDTO) o;
        if (instituicaoEnsinoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instituicaoEnsinoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstituicaoEnsinoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", logotipo='" + getLogotipo() + "'" +
            ", fundacao='" + getFundacao() + "'" +
            ", numero='" + getNumero() + "'" +
            ", tipoVinculo='" + getTipoVinculo() + "'" +
            ", unidadePagadora='" + getUnidadePagadora() + "'" +
            ", unidadeOrganica='" + getUnidadeOrganica() + "'" +
            ", tipoInstalacao='" + getTipoInstalacao() + "'" +
            ", dimensao='" + getDimensao() + "'" +
            ", carimbo='" + getCarimbo() + "'" +
            ", sede='" + isSede() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", hierarquiaId=" + getHierarquiaId() +
            ", hierarquiaNome='" + getHierarquiaNome() + "'" +
            "}";
    }
}
