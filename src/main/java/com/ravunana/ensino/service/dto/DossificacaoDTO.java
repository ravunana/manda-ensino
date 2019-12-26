package com.ravunana.ensino.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Dossificacao} entity.
 */
public class DossificacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String periodoLectivo;

    @NotNull
    private LocalDate anoLectivo;

    @NotNull
    private String objectivoGeral;

    
    @Lob
    private String objectivoEspecifico;

    @NotNull
    private Integer semanaLectiva;

    @NotNull
    private LocalDate de;

    @NotNull
    private LocalDate ate;

    
    @Lob
    private String conteudo;

    
    @Lob
    private String procedimentoEnsino;

    
    @Lob
    private String recursosDidatico;

    @NotNull
    private ZonedDateTime tempoAula;

    @NotNull
    private String formaAvaliacao;


    private Set<CursoDTO> cursos = new HashSet<>();

    private Set<ClasseDTO> classes = new HashSet<>();

    private Long disciplinaId;

    private String disciplinaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodoLectivo() {
        return periodoLectivo;
    }

    public void setPeriodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
    }

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getObjectivoGeral() {
        return objectivoGeral;
    }

    public void setObjectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
    }

    public String getObjectivoEspecifico() {
        return objectivoEspecifico;
    }

    public void setObjectivoEspecifico(String objectivoEspecifico) {
        this.objectivoEspecifico = objectivoEspecifico;
    }

    public Integer getSemanaLectiva() {
        return semanaLectiva;
    }

    public void setSemanaLectiva(Integer semanaLectiva) {
        this.semanaLectiva = semanaLectiva;
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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getProcedimentoEnsino() {
        return procedimentoEnsino;
    }

    public void setProcedimentoEnsino(String procedimentoEnsino) {
        this.procedimentoEnsino = procedimentoEnsino;
    }

    public String getRecursosDidatico() {
        return recursosDidatico;
    }

    public void setRecursosDidatico(String recursosDidatico) {
        this.recursosDidatico = recursosDidatico;
    }

    public ZonedDateTime getTempoAula() {
        return tempoAula;
    }

    public void setTempoAula(ZonedDateTime tempoAula) {
        this.tempoAula = tempoAula;
    }

    public String getFormaAvaliacao() {
        return formaAvaliacao;
    }

    public void setFormaAvaliacao(String formaAvaliacao) {
        this.formaAvaliacao = formaAvaliacao;
    }

    public Set<CursoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(Set<CursoDTO> cursos) {
        this.cursos = cursos;
    }

    public Set<ClasseDTO> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClasseDTO> classes) {
        this.classes = classes;
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

        DossificacaoDTO dossificacaoDTO = (DossificacaoDTO) o;
        if (dossificacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dossificacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DossificacaoDTO{" +
            "id=" + getId() +
            ", periodoLectivo='" + getPeriodoLectivo() + "'" +
            ", anoLectivo='" + getAnoLectivo() + "'" +
            ", objectivoGeral='" + getObjectivoGeral() + "'" +
            ", objectivoEspecifico='" + getObjectivoEspecifico() + "'" +
            ", semanaLectiva=" + getSemanaLectiva() +
            ", de='" + getDe() + "'" +
            ", ate='" + getAte() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", procedimentoEnsino='" + getProcedimentoEnsino() + "'" +
            ", recursosDidatico='" + getRecursosDidatico() + "'" +
            ", tempoAula='" + getTempoAula() + "'" +
            ", formaAvaliacao='" + getFormaAvaliacao() + "'" +
            ", disciplinaId=" + getDisciplinaId() +
            ", disciplinaNome='" + getDisciplinaNome() + "'" +
            "}";
    }
}
