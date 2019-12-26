package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Nota} entity.
 */
public class NotaDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    private Double valor;

    private ZonedDateTime data;

    private LocalDate anoLectivo;

    private String periodoLectivo;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long disciplinaId;

    private String disciplinaNome;

    private Long turmaId;

    private String turmaDescricao;

    private Long categoriaAvaliacaoId;

    private String categoriaAvaliacaoNome;

    private Long matriculaId;

    private String matriculaNumero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getPeriodoLectivo() {
        return periodoLectivo;
    }

    public void setPeriodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
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

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getDisciplinaNome() {
        return disciplinaNome;
    }

    public void setDisciplinaNome(String disciplinaNome) {
        this.disciplinaNome = disciplinaNome;
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

    public Long getCategoriaAvaliacaoId() {
        return categoriaAvaliacaoId;
    }

    public void setCategoriaAvaliacaoId(Long categoriaValiacaoId) {
        this.categoriaAvaliacaoId = categoriaValiacaoId;
    }

    public String getCategoriaAvaliacaoNome() {
        return categoriaAvaliacaoNome;
    }

    public void setCategoriaAvaliacaoNome(String categoriaValiacaoNome) {
        this.categoriaAvaliacaoNome = categoriaValiacaoNome;
    }

    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long matriculaId) {
        this.matriculaId = matriculaId;
    }

    public String getMatriculaNumero() {
        return matriculaNumero;
    }

    public void setMatriculaNumero(String matriculaNumero) {
        this.matriculaNumero = matriculaNumero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotaDTO notaDTO = (NotaDTO) o;
        if (notaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotaDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            ", anoLectivo='" + getAnoLectivo() + "'" +
            ", periodoLectivo='" + getPeriodoLectivo() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", disciplinaId=" + getDisciplinaId() +
            ", disciplinaNome='" + getDisciplinaNome() + "'" +
            ", turmaId=" + getTurmaId() +
            ", turmaDescricao='" + getTurmaDescricao() + "'" +
            ", categoriaAvaliacaoId=" + getCategoriaAvaliacaoId() +
            ", categoriaAvaliacaoNome='" + getCategoriaAvaliacaoNome() + "'" +
            ", matriculaId=" + getMatriculaId() +
            ", matriculaNumero='" + getMatriculaNumero() + "'" +
            "}";
    }
}
