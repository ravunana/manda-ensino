package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Turma} entity.
 */
public class TurmaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    private LocalDate anoLectivo;

    @NotNull
    private String regime;

    @NotNull
    private String turno;

    private ZonedDateTime data;

    private Boolean ativo;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long salaId;

    private String salaNumero;

    private Long classeId;

    private String classeDescricao;

    private Long cursoId;

    private String cursoNome;

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

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }

    public String getSalaNumero() {
        return salaNumero;
    }

    public void setSalaNumero(String salaNumero) {
        this.salaNumero = salaNumero;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TurmaDTO turmaDTO = (TurmaDTO) o;
        if (turmaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turmaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TurmaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", anoLectivo='" + getAnoLectivo() + "'" +
            ", regime='" + getRegime() + "'" +
            ", turno='" + getTurno() + "'" +
            ", data='" + getData() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", salaId=" + getSalaId() +
            ", salaNumero='" + getSalaNumero() + "'" +
            ", classeId=" + getClasseId() +
            ", classeDescricao='" + getClasseDescricao() + "'" +
            ", cursoId=" + getCursoId() +
            ", cursoNome='" + getCursoNome() + "'" +
            "}";
    }
}
