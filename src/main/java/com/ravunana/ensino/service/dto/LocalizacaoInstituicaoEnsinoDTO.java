package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.LocalizacaoInstituicaoEnsino} entity.
 */
public class LocalizacaoInstituicaoEnsinoDTO implements Serializable {

    private Long id;

    @NotNull
    private String provincia;

    @NotNull
    private String municipio;

    @NotNull
    private String bairro;

    @NotNull
    @Size(max = 200)
    private String rua;

    @NotNull
    @Size(max = 10)
    private String quarteirao;

    @Size(max = 10)
    private String numeroPorta;

    private String caixaPostal;


    private Long instituicaoEnsinoId;

    private String instituicaoEnsinoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumeroPorta() {
        return numeroPorta;
    }

    public void setNumeroPorta(String numeroPorta) {
        this.numeroPorta = numeroPorta;
    }

    public String getCaixaPostal() {
        return caixaPostal;
    }

    public void setCaixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
    }

    public Long getInstituicaoEnsinoId() {
        return instituicaoEnsinoId;
    }

    public void setInstituicaoEnsinoId(Long instituicaoEnsinoId) {
        this.instituicaoEnsinoId = instituicaoEnsinoId;
    }

    public String getInstituicaoEnsinoNome() {
        return instituicaoEnsinoNome;
    }

    public void setInstituicaoEnsinoNome(String instituicaoEnsinoNome) {
        this.instituicaoEnsinoNome = instituicaoEnsinoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = (LocalizacaoInstituicaoEnsinoDTO) o;
        if (localizacaoInstituicaoEnsinoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localizacaoInstituicaoEnsinoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalizacaoInstituicaoEnsinoDTO{" +
            "id=" + getId() +
            ", provincia='" + getProvincia() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", quarteirao='" + getQuarteirao() + "'" +
            ", numeroPorta='" + getNumeroPorta() + "'" +
            ", caixaPostal='" + getCaixaPostal() + "'" +
            ", instituicaoEnsinoId=" + getInstituicaoEnsinoId() +
            ", instituicaoEnsinoNome='" + getInstituicaoEnsinoNome() + "'" +
            "}";
    }
}
