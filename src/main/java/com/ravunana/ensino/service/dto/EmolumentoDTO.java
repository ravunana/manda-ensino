package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Emolumento} entity.
 */
public class EmolumentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valor;

    @NotNull
    @DecimalMin(value = "0")
    private Double valorMulta;

    @NotNull
    @Min(value = 1)
    private Integer tempoMulta;

    @DecimalMin(value = "0")
    private Double quantidade;


    private Long cursoId;

    private String cursoNome;

    private Long classeId;

    private String classeDescricao;

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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(Double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public Integer getTempoMulta() {
        return tempoMulta;
    }

    public void setTempoMulta(Integer tempoMulta) {
        this.tempoMulta = tempoMulta;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }

    public Long getClasseId() {
        return classeId;
    }

    public void setClasseId(Long classeId) {
        this.classeId = classeId;
    }

    public String getClasseDescricao() {
        return classeDescricao;
    }

    public void setClasseDescricao(String classeDescricao) {
        this.classeDescricao = classeDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmolumentoDTO emolumentoDTO = (EmolumentoDTO) o;
        if (emolumentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emolumentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmolumentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", valorMulta=" + getValorMulta() +
            ", tempoMulta=" + getTempoMulta() +
            ", quantidade=" + getQuantidade() +
            ", cursoId=" + getCursoId() +
            ", cursoNome='" + getCursoNome() + "'" +
            ", classeId=" + getClasseId() +
            ", classeDescricao='" + getClasseDescricao() + "'" +
            "}";
    }
}
