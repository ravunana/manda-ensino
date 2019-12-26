package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Software} entity.
 */
public class SoftwareDTO implements Serializable {

    private Long id;

    @NotNull
    private String instituicaoEnsino;

    @NotNull
    private String tipoSistema;

    @NotNull
    private String nif;

    @NotNull
    private Integer numeroValidacaoAGT;

    @NotNull
    @Size(max = 255)
    private String nome;

    @NotNull
    @Size(max = 30)
    private String versao;

    @NotNull
    private String hashCode;

    @NotNull
    private String hashControl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    public void setInstituicaoEnsino(String instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public String getTipoSistema() {
        return tipoSistema;
    }

    public void setTipoSistema(String tipoSistema) {
        this.tipoSistema = tipoSistema;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Integer getNumeroValidacaoAGT() {
        return numeroValidacaoAGT;
    }

    public void setNumeroValidacaoAGT(Integer numeroValidacaoAGT) {
        this.numeroValidacaoAGT = numeroValidacaoAGT;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getHashControl() {
        return hashControl;
    }

    public void setHashControl(String hashControl) {
        this.hashControl = hashControl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SoftwareDTO softwareDTO = (SoftwareDTO) o;
        if (softwareDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), softwareDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SoftwareDTO{" +
            "id=" + getId() +
            ", instituicaoEnsino='" + getInstituicaoEnsino() + "'" +
            ", tipoSistema='" + getTipoSistema() + "'" +
            ", nif='" + getNif() + "'" +
            ", numeroValidacaoAGT=" + getNumeroValidacaoAGT() +
            ", nome='" + getNome() + "'" +
            ", versao='" + getVersao() + "'" +
            ", hashCode='" + getHashCode() + "'" +
            ", hashControl='" + getHashControl() + "'" +
            "}";
    }
}
