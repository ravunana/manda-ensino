package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.CoordenadaBancaria} entity.
 */
public class CoordenadaBancariaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String proprietario;

    @NotNull
    private String numeroConta;

    
    private String iban;

    @NotNull
    private Boolean ativo;

    private Boolean mostrarDocumento;

    private Boolean mostrarPontoVenda;

    private Boolean padraoRecebimento;

    private Boolean padraoPagamento;


    private Set<InstituicaoEnsinoDTO> instituicaoEnsinos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isMostrarDocumento() {
        return mostrarDocumento;
    }

    public void setMostrarDocumento(Boolean mostrarDocumento) {
        this.mostrarDocumento = mostrarDocumento;
    }

    public Boolean isMostrarPontoVenda() {
        return mostrarPontoVenda;
    }

    public void setMostrarPontoVenda(Boolean mostrarPontoVenda) {
        this.mostrarPontoVenda = mostrarPontoVenda;
    }

    public Boolean isPadraoRecebimento() {
        return padraoRecebimento;
    }

    public void setPadraoRecebimento(Boolean padraoRecebimento) {
        this.padraoRecebimento = padraoRecebimento;
    }

    public Boolean isPadraoPagamento() {
        return padraoPagamento;
    }

    public void setPadraoPagamento(Boolean padraoPagamento) {
        this.padraoPagamento = padraoPagamento;
    }

    public Set<InstituicaoEnsinoDTO> getInstituicaoEnsinos() {
        return instituicaoEnsinos;
    }

    public void setInstituicaoEnsinos(Set<InstituicaoEnsinoDTO> instituicaoEnsinos) {
        this.instituicaoEnsinos = instituicaoEnsinos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoordenadaBancariaDTO coordenadaBancariaDTO = (CoordenadaBancariaDTO) o;
        if (coordenadaBancariaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coordenadaBancariaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoordenadaBancariaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", proprietario='" + getProprietario() + "'" +
            ", numeroConta='" + getNumeroConta() + "'" +
            ", iban='" + getIban() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", mostrarDocumento='" + isMostrarDocumento() + "'" +
            ", mostrarPontoVenda='" + isMostrarPontoVenda() + "'" +
            ", padraoRecebimento='" + isPadraoRecebimento() + "'" +
            ", padraoPagamento='" + isPadraoPagamento() + "'" +
            "}";
    }
}
