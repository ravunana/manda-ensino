package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dossificacao.
 */
@Entity
@Table(name = "pdg_dossificacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dossificacao")
public class Dossificacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "periodo_lectivo", nullable = false)
    private String periodoLectivo;

    @NotNull
    @Column(name = "ano_lectivo", nullable = false)
    private LocalDate anoLectivo;

    @NotNull
    @Column(name = "objectivo_geral", nullable = false)
    private String objectivoGeral;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "objectivo_especifico", nullable = false)
    private String objectivoEspecifico;

    @NotNull
    @Column(name = "semana_lectiva", nullable = false)
    private Integer semanaLectiva;

    @NotNull
    @Column(name = "de", nullable = false)
    private LocalDate de;

    @NotNull
    @Column(name = "ate", nullable = false)
    private LocalDate ate;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "procedimento_ensino", nullable = false)
    private String procedimentoEnsino;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "recursos_didatico", nullable = false)
    private String recursosDidatico;

    @NotNull
    @Column(name = "tempo_aula", nullable = false)
    private ZonedDateTime tempoAula;

    @NotNull
    @Column(name = "forma_avaliacao", nullable = false)
    private String formaAvaliacao;

    @OneToMany(mappedBy = "dossificacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoAula> planoAulas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "pdg_dossificacao_curso",
               joinColumns = @JoinColumn(name = "dossificacao_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "curso_id", referencedColumnName = "id"))
    private Set<Curso> cursos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "pdg_dossificacao_classe",
               joinColumns = @JoinColumn(name = "dossificacao_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "classe_id", referencedColumnName = "id"))
    private Set<Classe> classes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dossificacaos")
    private Disciplina disciplina;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodoLectivo() {
        return periodoLectivo;
    }

    public Dossificacao periodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
        return this;
    }

    public void setPeriodoLectivo(String periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
    }

    public LocalDate getAnoLectivo() {
        return anoLectivo;
    }

    public Dossificacao anoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
        return this;
    }

    public void setAnoLectivo(LocalDate anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getObjectivoGeral() {
        return objectivoGeral;
    }

    public Dossificacao objectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
        return this;
    }

    public void setObjectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
    }

    public String getObjectivoEspecifico() {
        return objectivoEspecifico;
    }

    public Dossificacao objectivoEspecifico(String objectivoEspecifico) {
        this.objectivoEspecifico = objectivoEspecifico;
        return this;
    }

    public void setObjectivoEspecifico(String objectivoEspecifico) {
        this.objectivoEspecifico = objectivoEspecifico;
    }

    public Integer getSemanaLectiva() {
        return semanaLectiva;
    }

    public Dossificacao semanaLectiva(Integer semanaLectiva) {
        this.semanaLectiva = semanaLectiva;
        return this;
    }

    public void setSemanaLectiva(Integer semanaLectiva) {
        this.semanaLectiva = semanaLectiva;
    }

    public LocalDate getDe() {
        return de;
    }

    public Dossificacao de(LocalDate de) {
        this.de = de;
        return this;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public LocalDate getAte() {
        return ate;
    }

    public Dossificacao ate(LocalDate ate) {
        this.ate = ate;
        return this;
    }

    public void setAte(LocalDate ate) {
        this.ate = ate;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Dossificacao conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getProcedimentoEnsino() {
        return procedimentoEnsino;
    }

    public Dossificacao procedimentoEnsino(String procedimentoEnsino) {
        this.procedimentoEnsino = procedimentoEnsino;
        return this;
    }

    public void setProcedimentoEnsino(String procedimentoEnsino) {
        this.procedimentoEnsino = procedimentoEnsino;
    }

    public String getRecursosDidatico() {
        return recursosDidatico;
    }

    public Dossificacao recursosDidatico(String recursosDidatico) {
        this.recursosDidatico = recursosDidatico;
        return this;
    }

    public void setRecursosDidatico(String recursosDidatico) {
        this.recursosDidatico = recursosDidatico;
    }

    public ZonedDateTime getTempoAula() {
        return tempoAula;
    }

    public Dossificacao tempoAula(ZonedDateTime tempoAula) {
        this.tempoAula = tempoAula;
        return this;
    }

    public void setTempoAula(ZonedDateTime tempoAula) {
        this.tempoAula = tempoAula;
    }

    public String getFormaAvaliacao() {
        return formaAvaliacao;
    }

    public Dossificacao formaAvaliacao(String formaAvaliacao) {
        this.formaAvaliacao = formaAvaliacao;
        return this;
    }

    public void setFormaAvaliacao(String formaAvaliacao) {
        this.formaAvaliacao = formaAvaliacao;
    }

    public Set<PlanoAula> getPlanoAulas() {
        return planoAulas;
    }

    public Dossificacao planoAulas(Set<PlanoAula> planoAulas) {
        this.planoAulas = planoAulas;
        return this;
    }

    public Dossificacao addPlanoAula(PlanoAula planoAula) {
        this.planoAulas.add(planoAula);
        planoAula.setDossificacao(this);
        return this;
    }

    public Dossificacao removePlanoAula(PlanoAula planoAula) {
        this.planoAulas.remove(planoAula);
        planoAula.setDossificacao(null);
        return this;
    }

    public void setPlanoAulas(Set<PlanoAula> planoAulas) {
        this.planoAulas = planoAulas;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public Dossificacao cursos(Set<Curso> cursos) {
        this.cursos = cursos;
        return this;
    }

    public Dossificacao addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.getDossificacaos().add(this);
        return this;
    }

    public Dossificacao removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.getDossificacaos().remove(this);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public Set<Classe> getClasses() {
        return classes;
    }

    public Dossificacao classes(Set<Classe> classes) {
        this.classes = classes;
        return this;
    }

    public Dossificacao addClasse(Classe classe) {
        this.classes.add(classe);
        classe.getDossificacaos().add(this);
        return this;
    }

    public Dossificacao removeClasse(Classe classe) {
        this.classes.remove(classe);
        classe.getDossificacaos().remove(this);
        return this;
    }

    public void setClasses(Set<Classe> classes) {
        this.classes = classes;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Dossificacao disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dossificacao)) {
            return false;
        }
        return id != null && id.equals(((Dossificacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dossificacao{" +
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
            "}";
    }
}
