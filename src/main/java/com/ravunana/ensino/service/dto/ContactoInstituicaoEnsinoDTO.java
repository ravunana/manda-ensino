package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.ContactoInstituicaoEnsino} entity.
 */
public class ContactoInstituicaoEnsinoDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoContacto;

    @NotNull
    private String descricao;

    @NotNull
    private String contacto;


    private Long instituicaoEnsinoId;

    private String instituicaoEnsinoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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

        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO = (ContactoInstituicaoEnsinoDTO) o;
        if (contactoInstituicaoEnsinoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactoInstituicaoEnsinoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactoInstituicaoEnsinoDTO{" +
            "id=" + getId() +
            ", tipoContacto='" + getTipoContacto() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", contacto='" + getContacto() + "'" +
            ", instituicaoEnsinoId=" + getInstituicaoEnsinoId() +
            ", instituicaoEnsinoNome='" + getInstituicaoEnsinoNome() + "'" +
            "}";
    }
}
