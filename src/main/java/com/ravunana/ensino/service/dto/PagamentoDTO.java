package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Pagamento} entity.
 */
public class PagamentoDTO implements Serializable {

    private Long id;

    private ZonedDateTime data;

    @NotNull
    private String numero;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long alunoId;

    private String alunoNumeroProcesso;

    private Long formaLiquidacaoId;

    private String formaLiquidacaoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public Long getFormaLiquidacaoId() {
        return formaLiquidacaoId;
    }

    public void setFormaLiquidacaoId(Long formaLiquidacaoId) {
        this.formaLiquidacaoId = formaLiquidacaoId;
    }

    public String getFormaLiquidacaoNome() {
        return formaLiquidacaoNome;
    }

    public void setFormaLiquidacaoNome(String formaLiquidacaoNome) {
        this.formaLiquidacaoNome = formaLiquidacaoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PagamentoDTO pagamentoDTO = (PagamentoDTO) o;
        if (pagamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PagamentoDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", numero='" + getNumero() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", alunoId=" + getAlunoId() +
            ", alunoNumeroProcesso='" + getAlunoNumeroProcesso() + "'" +
            ", formaLiquidacaoId=" + getFormaLiquidacaoId() +
            ", formaLiquidacaoNome='" + getFormaLiquidacaoNome() + "'" +
            "}";
    }
}
