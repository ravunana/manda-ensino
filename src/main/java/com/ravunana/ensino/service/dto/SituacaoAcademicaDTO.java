package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.SituacaoAcademica} entity.
 */
public class SituacaoAcademicaDTO implements Serializable {

    private Long id;

    private Integer anoLectivo;

    private ZonedDateTime data;

    private String estado;

    @Lob
    private String descricao;


    private Long alunoId;

    private String alunoNumeroProcesso;

    private Long disciplinaId;

    private String disciplinaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

        SituacaoAcademicaDTO situacaoAcademicaDTO = (SituacaoAcademicaDTO) o;
        if (situacaoAcademicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), situacaoAcademicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SituacaoAcademicaDTO{" +
            "id=" + getId() +
            ", anoLectivo=" + getAnoLectivo() +
            ", data='" + getData() + "'" +
            ", estado='" + getEstado() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", alunoId=" + getAlunoId() +
            ", alunoNumeroProcesso='" + getAlunoNumeroProcesso() + "'" +
            ", disciplinaId=" + getDisciplinaId() +
            ", disciplinaNome='" + getDisciplinaNome() + "'" +
            "}";
    }
}
