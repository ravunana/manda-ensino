package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.DetalhePagamento} entity.
 */
public class DetalhePagamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private Boolean mensalidade;

    @NotNull
    @Min(value = 1)
    private Integer quantidade;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    @DecimalMin(value = "0")
    private Double desconto;

    @DecimalMin(value = "0")
    private Double multa;

    @DecimalMin(value = "0")
    private Double juro;

    private ZonedDateTime data;

    private LocalDate vencimento;

    @NotNull
    private Boolean quitado;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long emolumentoId;

    private String emolumentoNome;

    private Long depositoId;

    private String depositoNumeroTalao;

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

    public Boolean isMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Boolean mensalidade) {
        this.mensalidade = mensalidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Double getJuro() {
        return juro;
    }

    public void setJuro(Double juro) {
        this.juro = juro;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public Boolean isQuitado() {
        return quitado;
    }

    public void setQuitado(Boolean quitado) {
        this.quitado = quitado;
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

    public Long getEmolumentoId() {
        return emolumentoId;
    }

    public void setEmolumentoId(Long emolumentoId) {
        this.emolumentoId = emolumentoId;
    }

    public String getEmolumentoNome() {
        return emolumentoNome;
    }

    public void setEmolumentoNome(String emolumentoNome) {
        this.emolumentoNome = emolumentoNome;
    }

    public Long getDepositoId() {
        return depositoId;
    }

    public void setDepositoId(Long depositoId) {
        this.depositoId = depositoId;
    }

    public String getDepositoNumeroTalao() {
        return depositoNumeroTalao;
    }

    public void setDepositoNumeroTalao(String depositoNumeroTalao) {
        this.depositoNumeroTalao = depositoNumeroTalao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetalhePagamentoDTO detalhePagamentoDTO = (DetalhePagamentoDTO) o;
        if (detalhePagamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalhePagamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalhePagamentoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", mensalidade='" + isMensalidade() + "'" +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", desconto=" + getDesconto() +
            ", multa=" + getMulta() +
            ", juro=" + getJuro() +
            ", data='" + getData() + "'" +
            ", vencimento='" + getVencimento() + "'" +
            ", quitado='" + isQuitado() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", emolumentoId=" + getEmolumentoId() +
            ", emolumentoNome='" + getEmolumentoNome() + "'" +
            ", depositoId=" + getDepositoId() +
            ", depositoNumeroTalao='" + getDepositoNumeroTalao() + "'" +
            "}";
    }
}
