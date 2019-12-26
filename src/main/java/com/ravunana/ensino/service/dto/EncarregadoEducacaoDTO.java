package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.EncarregadoEducacao} entity.
 */
public class EncarregadoEducacaoDTO implements Serializable {

    private Long id;

    private String profissao;

    private String cargo;

    private BigDecimal faixaSalarial;

    private String nomeEmpresa;

    private String contactoEmpresa;


    private Long pessoaId;

    private String pessoaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getFaixaSalarial() {
        return faixaSalarial;
    }

    public void setFaixaSalarial(BigDecimal faixaSalarial) {
        this.faixaSalarial = faixaSalarial;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getContactoEmpresa() {
        return contactoEmpresa;
    }

    public void setContactoEmpresa(String contactoEmpresa) {
        this.contactoEmpresa = contactoEmpresa;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EncarregadoEducacaoDTO encarregadoEducacaoDTO = (EncarregadoEducacaoDTO) o;
        if (encarregadoEducacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), encarregadoEducacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EncarregadoEducacaoDTO{" +
            "id=" + getId() +
            ", profissao='" + getProfissao() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", faixaSalarial=" + getFaixaSalarial() +
            ", nomeEmpresa='" + getNomeEmpresa() + "'" +
            ", contactoEmpresa='" + getContactoEmpresa() + "'" +
            ", pessoaId=" + getPessoaId() +
            ", pessoaNome='" + getPessoaNome() + "'" +
            "}";
    }
}
