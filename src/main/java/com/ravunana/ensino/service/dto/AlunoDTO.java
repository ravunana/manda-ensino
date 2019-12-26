package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Aluno} entity.
 */
public class AlunoDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeroProcesso;

    @NotNull
    private Boolean transferido;

    private ZonedDateTime data;

    private String turmaAnterior;

    private Integer anoConclusao;

    private String cursoFrequentado;

    private String nomeEscolaAnteriror;

    private String enderecoEscolaAnterior;

    private Integer classeConcluida;

    private String numeroProcessoAnterior;

    private String situacaoAnterior;


    private Long pessoaId;

    private String pessoaNome;

    private Long utilizadorId;

    private String utilizadorLogin;

    private Long encarregadoEducacaoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Boolean isTransferido() {
        return transferido;
    }

    public void setTransferido(Boolean transferido) {
        this.transferido = transferido;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getTurmaAnterior() {
        return turmaAnterior;
    }

    public void setTurmaAnterior(String turmaAnterior) {
        this.turmaAnterior = turmaAnterior;
    }

    public Integer getAnoConclusao() {
        return anoConclusao;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }

    public String getCursoFrequentado() {
        return cursoFrequentado;
    }

    public void setCursoFrequentado(String cursoFrequentado) {
        this.cursoFrequentado = cursoFrequentado;
    }

    public String getNomeEscolaAnteriror() {
        return nomeEscolaAnteriror;
    }

    public void setNomeEscolaAnteriror(String nomeEscolaAnteriror) {
        this.nomeEscolaAnteriror = nomeEscolaAnteriror;
    }

    public String getEnderecoEscolaAnterior() {
        return enderecoEscolaAnterior;
    }

    public void setEnderecoEscolaAnterior(String enderecoEscolaAnterior) {
        this.enderecoEscolaAnterior = enderecoEscolaAnterior;
    }

    public Integer getClasseConcluida() {
        return classeConcluida;
    }

    public void setClasseConcluida(Integer classeConcluida) {
        this.classeConcluida = classeConcluida;
    }

    public String getNumeroProcessoAnterior() {
        return numeroProcessoAnterior;
    }

    public void setNumeroProcessoAnterior(String numeroProcessoAnterior) {
        this.numeroProcessoAnterior = numeroProcessoAnterior;
    }

    public String getSituacaoAnterior() {
        return situacaoAnterior;
    }

    public void setSituacaoAnterior(String situacaoAnterior) {
        this.situacaoAnterior = situacaoAnterior;
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

    public Long getEncarregadoEducacaoId() {
        return encarregadoEducacaoId;
    }

    public void setEncarregadoEducacaoId(Long encarregadoEducacaoId) {
        this.encarregadoEducacaoId = encarregadoEducacaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlunoDTO alunoDTO = (AlunoDTO) o;
        if (alunoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alunoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlunoDTO{" +
            "id=" + getId() +
            ", numeroProcesso='" + getNumeroProcesso() + "'" +
            ", transferido='" + isTransferido() + "'" +
            ", data='" + getData() + "'" +
            ", turmaAnterior='" + getTurmaAnterior() + "'" +
            ", anoConclusao=" + getAnoConclusao() +
            ", cursoFrequentado='" + getCursoFrequentado() + "'" +
            ", nomeEscolaAnteriror='" + getNomeEscolaAnteriror() + "'" +
            ", enderecoEscolaAnterior='" + getEnderecoEscolaAnterior() + "'" +
            ", classeConcluida=" + getClasseConcluida() +
            ", numeroProcessoAnterior='" + getNumeroProcessoAnterior() + "'" +
            ", situacaoAnterior='" + getSituacaoAnterior() + "'" +
            ", pessoaId=" + getPessoaId() +
            ", pessoaNome='" + getPessoaNome() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", encarregadoEducacaoId=" + getEncarregadoEducacaoId() +
            "}";
    }
}
