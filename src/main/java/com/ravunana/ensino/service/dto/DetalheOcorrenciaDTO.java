package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.DetalheOcorrencia} entity.
 */
public class DetalheOcorrenciaDTO implements Serializable {

    private Long id;

    private LocalDate de;

    private LocalDate ate;

    
    @Lob
    private String motivo;


    private Long ocorrenciaId;

    private String ocorrenciaNumero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getOcorrenciaId() {
        return ocorrenciaId;
    }

    public void setOcorrenciaId(Long ocorrenciaId) {
        this.ocorrenciaId = ocorrenciaId;
    }

    public String getOcorrenciaNumero() {
        return ocorrenciaNumero;
    }

    public void setOcorrenciaNumero(String ocorrenciaNumero) {
        this.ocorrenciaNumero = ocorrenciaNumero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetalheOcorrenciaDTO detalheOcorrenciaDTO = (DetalheOcorrenciaDTO) o;
        if (detalheOcorrenciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalheOcorrenciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalheOcorrenciaDTO{" +
            "id=" + getId() +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", motivo='" + getMotivo() + "'" +
            ", ocorrenciaId=" + getOcorrenciaId() +
            ", ocorrenciaNumero='" + getOcorrenciaNumero() + "'" +
            "}";
    }
}
