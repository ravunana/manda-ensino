package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.AssinaturaDigital} entity.
 */
public class AssinaturaDigitalDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipo;

    @Lob
    private byte[] assinatura;

    private String assinaturaContentType;
    
    private String hashcode;

    private ZonedDateTime data;


    private Long instituicaoId;

    private String instituicaoNome;

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

    public byte[] getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(byte[] assinatura) {
        this.assinatura = assinatura;
    }

    public String getAssinaturaContentType() {
        return assinaturaContentType;
    }

    public void setAssinaturaContentType(String assinaturaContentType) {
        this.assinaturaContentType = assinaturaContentType;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Long getInstituicaoId() {
        return instituicaoId;
    }

    public void setInstituicaoId(Long instituicaoEnsinoId) {
        this.instituicaoId = instituicaoEnsinoId;
    }

    public String getInstituicaoNome() {
        return instituicaoNome;
    }

    public void setInstituicaoNome(String instituicaoEnsinoNome) {
        this.instituicaoNome = instituicaoEnsinoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssinaturaDigitalDTO assinaturaDigitalDTO = (AssinaturaDigitalDTO) o;
        if (assinaturaDigitalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assinaturaDigitalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssinaturaDigitalDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", assinatura='" + getAssinatura() + "'" +
            ", hashcode='" + getHashcode() + "'" +
            ", data='" + getData() + "'" +
            ", instituicaoId=" + getInstituicaoId() +
            ", instituicaoNome='" + getInstituicaoNome() + "'" +
            "}";
    }
}
