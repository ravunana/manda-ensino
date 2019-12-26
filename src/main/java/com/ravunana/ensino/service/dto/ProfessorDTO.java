package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Professor} entity.
 */
public class ProfessorDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeroAgente;

    private Boolean ativo;


    private Long pessoaId;

    private String pessoaNome;

    private Long utilizadorId;

    private String utilizadorLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroAgente() {
        return numeroAgente;
    }

    public void setNumeroAgente(String numeroAgente) {
        this.numeroAgente = numeroAgente;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
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

        ProfessorDTO professorDTO = (ProfessorDTO) o;
        if (professorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessorDTO{" +
            "id=" + getId() +
            ", numeroAgente='" + getNumeroAgente() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", pessoaId=" + getPessoaId() +
            ", pessoaNome='" + getPessoaNome() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            "}";
    }
}
