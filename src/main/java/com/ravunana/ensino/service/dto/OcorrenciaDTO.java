package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Ocorrencia} entity.
 */
public class OcorrenciaDTO implements Serializable {

    private Long id;

    private String tipo;

    @NotNull
    private ZonedDateTime data;

    @NotNull
    private String numero;

    @NotNull
    private Boolean reportarEncarregado;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long matriculaId;

    private String matriculaNumero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isReportarEncarregado() {
        return reportarEncarregado;
    }

    public void setReportarEncarregado(Boolean reportarEncarregado) {
        this.reportarEncarregado = reportarEncarregado;
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

    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long matriculaId) {
        this.matriculaId = matriculaId;
    }

    public String getMatriculaNumero() {
        return matriculaNumero;
    }

    public void setMatriculaNumero(String matriculaNumero) {
        this.matriculaNumero = matriculaNumero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OcorrenciaDTO ocorrenciaDTO = (OcorrenciaDTO) o;
        if (ocorrenciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ocorrenciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OcorrenciaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", data='" + getData() + "'" +
            ", numero='" + getNumero() + "'" +
            ", reportarEncarregado='" + isReportarEncarregado() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", matriculaId=" + getMatriculaId() +
            ", matriculaNumero='" + getMatriculaNumero() + "'" +
            "}";
    }
}
