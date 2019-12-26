package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Matricula} entity.
 */
public class MatriculaDTO implements Serializable {

    private Long id;

    private ZonedDateTime data;

    @NotNull
    @Min(value = 1)
    private Integer numero;

    @Lob
    private String observacao;

    @NotNull
    private Integer anoLectivo;

    @NotNull
    private String peridoLectivo;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long alunoId;

    private String alunoNumeroProcesso;

    private Long confirmacaoId;

    private String confirmacaoNumero;

    private Long categoriaId;

    private String categoriaNome;

    private Long turmaId;

    private String turmaDescricao;

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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getPeridoLectivo() {
        return peridoLectivo;
    }

    public void setPeridoLectivo(String peridoLectivo) {
        this.peridoLectivo = peridoLectivo;
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

    public Long getConfirmacaoId() {
        return confirmacaoId;
    }

    public void setConfirmacaoId(Long matriculaId) {
        this.confirmacaoId = matriculaId;
    }

    public String getConfirmacaoNumero() {
        return confirmacaoNumero;
    }

    public void setConfirmacaoNumero(String matriculaNumero) {
        this.confirmacaoNumero = matriculaNumero;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaAlunoId) {
        this.categoriaId = categoriaAlunoId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaAlunoNome) {
        this.categoriaNome = categoriaAlunoNome;
    }

    public Long getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Long turmaId) {
        this.turmaId = turmaId;
    }

    public String getTurmaDescricao() {
        return turmaDescricao;
    }

    public void setTurmaDescricao(String turmaDescricao) {
        this.turmaDescricao = turmaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatriculaDTO matriculaDTO = (MatriculaDTO) o;
        if (matriculaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matriculaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatriculaDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", numero=" + getNumero() +
            ", observacao='" + getObservacao() + "'" +
            ", anoLectivo=" + getAnoLectivo() +
            ", peridoLectivo='" + getPeridoLectivo() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", alunoId=" + getAlunoId() +
            ", alunoNumeroProcesso='" + getAlunoNumeroProcesso() + "'" +
            ", confirmacaoId=" + getConfirmacaoId() +
            ", confirmacaoNumero='" + getConfirmacaoNumero() + "'" +
            ", categoriaId=" + getCategoriaId() +
            ", categoriaNome='" + getCategoriaNome() + "'" +
            ", turmaId=" + getTurmaId() +
            ", turmaDescricao='" + getTurmaDescricao() + "'" +
            "}";
    }
}
