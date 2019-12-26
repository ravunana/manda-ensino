package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.DocumentacaoPessoa} entity.
 */
public class DocumentacaoPessoaDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipo;

    @NotNull
    private String numero;

    private LocalDate emissao;

    private LocalDate validade;

    private String naturalidade;

    private String nacionalidade;

    private String localEmissao;

    
    private String nif;


    private Long pessoaId;

    private String pessoaNome;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getEmissao() {
        return emissao;
    }

    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getLocalEmissao() {
        return localEmissao;
    }

    public void setLocalEmissao(String localEmissao) {
        this.localEmissao = localEmissao;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
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

        DocumentacaoPessoaDTO documentacaoPessoaDTO = (DocumentacaoPessoaDTO) o;
        if (documentacaoPessoaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentacaoPessoaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentacaoPessoaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", numero='" + getNumero() + "'" +
            ", emissao='" + getEmissao() + "'" +
            ", validade='" + getValidade() + "'" +
            ", naturalidade='" + getNaturalidade() + "'" +
            ", nacionalidade='" + getNacionalidade() + "'" +
            ", localEmissao='" + getLocalEmissao() + "'" +
            ", nif='" + getNif() + "'" +
            ", pessoaId=" + getPessoaId() +
            ", pessoaNome='" + getPessoaNome() + "'" +
            "}";
    }
}
