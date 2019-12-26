package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Deposito} entity.
 */
public class DepositoDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeroTalao;

    @NotNull
    private LocalDate dataDeposito;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal saldo;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long meioLiquidacaoId;

    private String meioLiquidacaoNome;

    private Long alunoId;

    private String alunoNumeroProcesso;

    private Long contaId;

    private String contaDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTalao() {
        return numeroTalao;
    }

    public void setNumeroTalao(String numeroTalao) {
        this.numeroTalao = numeroTalao;
    }

    public LocalDate getDataDeposito() {
        return dataDeposito;
    }

    public void setDataDeposito(LocalDate dataDeposito) {
        this.dataDeposito = dataDeposito;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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

    public Long getMeioLiquidacaoId() {
        return meioLiquidacaoId;
    }

    public void setMeioLiquidacaoId(Long meioLiquidacaoId) {
        this.meioLiquidacaoId = meioLiquidacaoId;
    }

    public String getMeioLiquidacaoNome() {
        return meioLiquidacaoNome;
    }

    public void setMeioLiquidacaoNome(String meioLiquidacaoNome) {
        this.meioLiquidacaoNome = meioLiquidacaoNome;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoNumeroProcesso() {
        return alunoNumeroProcesso;
    }

    public void setAlunoNumeroProcesso(String alunoNumeroProcesso) {
        this.alunoNumeroProcesso = alunoNumeroProcesso;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long coordenadaBancariaId) {
        this.contaId = coordenadaBancariaId;
    }

    public String getContaDescricao() {
        return contaDescricao;
    }

    public void setContaDescricao(String coordenadaBancariaDescricao) {
        this.contaDescricao = coordenadaBancariaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepositoDTO depositoDTO = (DepositoDTO) o;
        if (depositoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depositoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepositoDTO{" +
            "id=" + getId() +
            ", numeroTalao='" + getNumeroTalao() + "'" +
            ", dataDeposito='" + getDataDeposito() + "'" +
            ", valor=" + getValor() +
            ", saldo=" + getSaldo() +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", meioLiquidacaoId=" + getMeioLiquidacaoId() +
            ", meioLiquidacaoNome='" + getMeioLiquidacaoNome() + "'" +
            ", alunoId=" + getAlunoId() +
            ", alunoNumeroProcesso='" + getAlunoNumeroProcesso() + "'" +
            ", contaId=" + getContaId() +
            ", contaDescricao='" + getContaDescricao() + "'" +
            "}";
    }
}
