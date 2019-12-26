package com.ravunana.ensino.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.CategoriaAluno} entity.
 */
@ApiModel(description = "/")
public class CategoriaAlunoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Double desconto;

    private Boolean pagaPropina;

    private Boolean pagaMulta;

    @Lob
    private String descricao;

    @Min(value = 1)
    private Integer diaPagamento;

    private Boolean mesAtual;

    private Boolean ativo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Boolean isPagaPropina() {
        return pagaPropina;
    }

    public void setPagaPropina(Boolean pagaPropina) {
        this.pagaPropina = pagaPropina;
    }

    public Boolean isPagaMulta() {
        return pagaMulta;
    }

    public void setPagaMulta(Boolean pagaMulta) {
        this.pagaMulta = pagaMulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(Integer diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    public Boolean isMesAtual() {
        return mesAtual;
    }

    public void setMesAtual(Boolean mesAtual) {
        this.mesAtual = mesAtual;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoriaAlunoDTO categoriaAlunoDTO = (CategoriaAlunoDTO) o;
        if (categoriaAlunoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriaAlunoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoriaAlunoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", desconto=" + getDesconto() +
            ", pagaPropina='" + isPagaPropina() + "'" +
            ", pagaMulta='" + isPagaMulta() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", diaPagamento=" + getDiaPagamento() +
            ", mesAtual='" + isMesAtual() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
