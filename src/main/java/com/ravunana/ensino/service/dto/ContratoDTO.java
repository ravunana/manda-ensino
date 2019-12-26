package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Contrato} entity.
 */
public class ContratoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate de;

    @NotNull
    private LocalDate ate;

    @NotNull
    private String contrato;

    @NotNull
    private Boolean aceitaTermos;

    @Lob
    private String observacao;

    @Lob
    private byte[] termos;

    private String termosContentType;
    @NotNull
    private Boolean emVigor;


    private Long alunoId;

    private String alunoNumeroProcesso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDe() {
        return de;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Boolean isAceitaTermos() {
        return aceitaTermos;
    }

    public void setAceitaTermos(Boolean aceitaTermos) {
        this.aceitaTermos = aceitaTermos;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public byte[] getTermos() {
        return termos;
    }

    public void setTermos(byte[] termos) {
        this.termos = termos;
    }

    public String getTermosContentType() {
        return termosContentType;
    }

    public void setTermosContentType(String termosContentType) {
        this.termosContentType = termosContentType;
    }

    public Boolean isEmVigor() {
        return emVigor;
    }

    public void setEmVigor(Boolean emVigor) {
        this.emVigor = emVigor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContratoDTO contratoDTO = (ContratoDTO) o;
        if (contratoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contratoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContratoDTO{" +
            "id=" + getId() +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", contrato='" + getContrato() + "'" +
            ", aceitaTermos='" + isAceitaTermos() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", termos='" + getTermos() + "'" +
            ", emVigor='" + isEmVigor() + "'" +
            ", alunoId=" + getAlunoId() +
            ", alunoNumeroProcesso='" + getAlunoNumeroProcesso() + "'" +
            "}";
    }
}
