package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.LicencaSoftware} entity.
 */
public class LicencaSoftwareDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoSubscricao;

    @NotNull
    private ZonedDateTime inicio;

    @NotNull
    private ZonedDateTime fim;

    private ZonedDateTime data;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    @NotNull
    private String codigo;

    @Min(value = 1)
    private Integer numeroUsuario;

    @Min(value = 1)
    private Integer numeroInstituicaoEnsino;


    private Long softwareId;

    private String softwareNome;

    private Long instituicaoEnsinoId;

    private String instituicaoEnsinoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoSubscricao() {
        return tipoSubscricao;
    }

    public void setTipoSubscricao(String tipoSubscricao) {
        this.tipoSubscricao = tipoSubscricao;
    }

    public ZonedDateTime getInicio() {
        return inicio;
    }

    public void setInicio(ZonedDateTime inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTime getFim() {
        return fim;
    }

    public void setFim(ZonedDateTime fim) {
        this.fim = fim;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getNumeroUsuario() {
        return numeroUsuario;
    }

    public void setNumeroUsuario(Integer numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
    }

    public Integer getNumeroInstituicaoEnsino() {
        return numeroInstituicaoEnsino;
    }

    public void setNumeroInstituicaoEnsino(Integer numeroInstituicaoEnsino) {
        this.numeroInstituicaoEnsino = numeroInstituicaoEnsino;
    }

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareNome() {
        return softwareNome;
    }

    public void setSoftwareNome(String softwareNome) {
        this.softwareNome = softwareNome;
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

        LicencaSoftwareDTO licencaSoftwareDTO = (LicencaSoftwareDTO) o;
        if (licencaSoftwareDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), licencaSoftwareDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LicencaSoftwareDTO{" +
            "id=" + getId() +
            ", tipoSubscricao='" + getTipoSubscricao() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", data='" + getData() + "'" +
            ", valor=" + getValor() +
            ", codigo='" + getCodigo() + "'" +
            ", numeroUsuario=" + getNumeroUsuario() +
            ", numeroInstituicaoEnsino=" + getNumeroInstituicaoEnsino() +
            ", softwareId=" + getSoftwareId() +
            ", softwareNome='" + getSoftwareNome() + "'" +
            ", instituicaoEnsinoId=" + getInstituicaoEnsinoId() +
            ", instituicaoEnsinoNome='" + getInstituicaoEnsinoNome() + "'" +
            "}";
    }
}
