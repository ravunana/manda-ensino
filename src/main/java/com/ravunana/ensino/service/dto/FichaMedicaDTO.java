package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.FichaMedica} entity.
 */
public class FichaMedicaDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean fazEducacaoFisica;

    private String grupoSanguinio;

    private Integer altura;

    private Double peso;

    @NotNull
    private Boolean autorizaMedicamento;

    @Lob
    private String observacao;

    private String nomeMedico;

    private String contactoMedico;

    private Boolean desmaioConstante;

    @Lob
    private String complicacoesSaude;


    private Long alunoId;

    private String alunoNumeroProcesso;

    private Long utilizadorId;

    private String utilizadorLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFazEducacaoFisica() {
        return fazEducacaoFisica;
    }

    public void setFazEducacaoFisica(Boolean fazEducacaoFisica) {
        this.fazEducacaoFisica = fazEducacaoFisica;
    }

    public String getGrupoSanguinio() {
        return grupoSanguinio;
    }

    public void setGrupoSanguinio(String grupoSanguinio) {
        this.grupoSanguinio = grupoSanguinio;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean isAutorizaMedicamento() {
        return autorizaMedicamento;
    }

    public void setAutorizaMedicamento(Boolean autorizaMedicamento) {
        this.autorizaMedicamento = autorizaMedicamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getContactoMedico() {
        return contactoMedico;
    }

    public void setContactoMedico(String contactoMedico) {
        this.contactoMedico = contactoMedico;
    }

    public Boolean isDesmaioConstante() {
        return desmaioConstante;
    }

    public void setDesmaioConstante(Boolean desmaioConstante) {
        this.desmaioConstante = desmaioConstante;
    }

    public String getComplicacoesSaude() {
        return complicacoesSaude;
    }

    public void setComplicacoesSaude(String complicacoesSaude) {
        this.complicacoesSaude = complicacoesSaude;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoNumeroProcesso() {
        return alunoNumeroProcesso;
    }

    public void setAlunoNumeroProcesso(String alunoNumeroProcesso) {
        this.alunoNumeroProcesso = alunoNumeroProcesso;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FichaMedicaDTO fichaMedicaDTO = (FichaMedicaDTO) o;
        if (fichaMedicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichaMedicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichaMedicaDTO{" +
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
            ", alunoId=" + getAlunoId() +
            ", alunoNumeroProcesso='" + getAlunoNumeroProcesso() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            "}";
    }
}
