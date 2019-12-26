package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Horario} entity.
 */
public class HorarioDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime inicioAula;

    @NotNull
    private ZonedDateTime terminoAlua;

    @NotNull
    private ZonedDateTime intervalo;

    @NotNull
    private String diaSemana;

    @NotNull
    private String regimeCurricular;

    @NotNull
    private ZonedDateTime data;

    private LocalDate anoLectivo;

    private String categoria;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long turmaId;

    private String turmaDescricao;

    private Long professorId;

    private String professorNumeroAgente;

    private Long disciplinaId;

    private String disciplinaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getInicioAula() {
        return inicioAula;
    }

    public void setInicioAula(ZonedDateTime inicioAula) {
        this.inicioAula = inicioAula;
    }

    public ZonedDateTime getTerminoAlua() {
        return terminoAlua;
    }

    public void setTerminoAlua(ZonedDateTime terminoAlua) {
        this.terminoAlua = terminoAlua;
    }

    public ZonedDateTime getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(ZonedDateTime intervalo) {
        this.intervalo = intervalo;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getRegimeCurricular() {
        return regimeCurricular;
    }

    public void setRegimeCurricular(String regimeCurricular) {
        this.regimeCurricular = regimeCurricular;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getProfessorNumeroAgente() {
        return professorNumeroAgente;
    }

    public void setProfessorNumeroAgente(String professorNumeroAgente) {
        this.professorNumeroAgente = professorNumeroAgente;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorarioDTO horarioDTO = (HorarioDTO) o;
        if (horarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HorarioDTO{" +
            "id=" + getId() +
            ", inicioAula='" + getInicioAula() + "'" +
            ", terminoAlua='" + getTerminoAlua() + "'" +
            ", intervalo='" + getIntervalo() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", regimeCurricular='" + getRegimeCurricular() + "'" +
            ", data='" + getData() + "'" +
            ", anoLectivo='" + getAnoLectivo() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", turmaId=" + getTurmaId() +
            ", turmaDescricao='" + getTurmaDescricao() + "'" +
            ", professorId=" + getProfessorId() +
            ", professorNumeroAgente='" + getProfessorNumeroAgente() + "'" +
            ", disciplinaId=" + getDisciplinaId() +
            ", disciplinaNome='" + getDisciplinaNome() + "'" +
            "}";
    }
}
