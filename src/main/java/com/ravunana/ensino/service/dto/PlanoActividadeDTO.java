package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.PlanoActividade} entity.
 */
public class PlanoActividadeDTO implements Serializable {

    private Long id;

    @Min(value = 1)
    private Integer numeroActividade;

    @NotNull
    private String atividade;

    
    @Lob
    private String objectivos;

    @NotNull
    private LocalDate de;

    @NotNull
    private LocalDate ate;

    @NotNull
    private String responsavel;

    private String local;

    @Lob
    private String observacao;

    private String participantes;

    private String coResponsavel;

    @NotNull
    private String statusActividade;

    @NotNull
    private LocalDate anoLectivo;

    private String periodoLectivo;


    private Long utilizadorId;

    private String utilizadorLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroActividade() {
        return numeroActividade;
    }

    public void setNumeroActividade(Integer numeroActividade) {
        this.numeroActividade = numeroActividade;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getObjectivos() {
        return objectivos;
    }

    public void setObjectivos(String objectivos) {
        this.objectivos = objectivos;
    }

    public LocalDate getDe() {
        return de;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getParticipantes() {
        return participantes;
    }

    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }

    public String getCoResponsavel() {
        return coResponsavel;
    }

    public void setCoResponsavel(String coResponsavel) {
        this.coResponsavel = coResponsavel;
    }

    public String getStatusActividade() {
        return statusActividade;
    }

    public void setStatusActividade(String statusActividade) {
        this.statusActividade = statusActividade;
    }

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getPeriodoLectivo() {
        return periodoLectivo;
    }

    public void setPeriodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
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

        PlanoActividadeDTO planoActividadeDTO = (PlanoActividadeDTO) o;
        if (planoActividadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planoActividadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanoActividadeDTO{" +
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
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            "}";
    }
}
