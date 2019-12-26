package com.ravunana.ensino.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.SerieDocumento} entity.
 */
public class SerieDocumentoDTO implements Serializable {

    private Long id;

    private String serie;

    private Integer sequencia;

    private String entidade;


    private Long instituicaoEnsinoId;

    private String instituicaoEnsinoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
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

        SerieDocumentoDTO serieDocumentoDTO = (SerieDocumentoDTO) o;
        if (serieDocumentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serieDocumentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SerieDocumentoDTO{" +
            "id=" + getId() +
            ", serie='" + getSerie() + "'" +
            ", sequencia=" + getSequencia() +
            ", entidade='" + getEntidade() + "'" +
            ", instituicaoEnsinoId=" + getInstituicaoEnsinoId() +
            ", instituicaoEnsinoNome='" + getInstituicaoEnsinoNome() + "'" +
            "}";
    }
}
