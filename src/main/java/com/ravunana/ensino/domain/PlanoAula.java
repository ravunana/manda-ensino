package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A PlanoAula.
 */
@Entity
@Table(name = "prof_plano_aula")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "planoaula")
public class PlanoAula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "objectivo_geral", nullable = false)
    private String objectivoGeral;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "objectivo_especifico", nullable = false)
    private String objectivoEspecifico;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "estrategia", nullable = false)
    private String estrategia;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "actividades", nullable = false)
    private String actividades;

    @NotNull
    @Column(name = "tempo", nullable = false)
    private ZonedDateTime tempo;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "recursos_ensino", nullable = false)
    private String recursosEnsino;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "avaliacao", nullable = false)
    private String avaliacao;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao", nullable = false)
    private String observacao;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "bibliografia", nullable = false)
    private String bibliografia;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "perfil_entrada", nullable = false)
    private String perfilEntrada;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "perfil_saida", nullable = false)
    private String perfilSaida;

    @Lob
    @Column(name = "anexo_1")
    private byte[] anexo1;

    @Column(name = "anexo_1_content_type")
    private String anexo1ContentType;

    @Lob
    @Column(name = "anexo_2")
    private byte[] anexo2;

    @Column(name = "anexo_2_content_type")
    private String anexo2ContentType;

    @Lob
    @Column(name = "anexo_3")
    private byte[] anexo3;

    @Column(name = "anexo_3_content_type")
    private String anexo3ContentType;

    @ManyToOne
    @JsonIgnoreProperties("planoAulas")
    private User utilizador;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "prof_plano_aula_turma",
               joinColumns = @JoinColumn(name = "plano_aula_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "turma_id", referencedColumnName = "id"))
    private Set<Turma> turmas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("planoAulas")
    private Disciplina disciplina;

    @ManyToOne
    @JsonIgnoreProperties("planoAulas")
    private Dossificacao dossificacao;

    @ManyToMany(mappedBy = "planoAulas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Aula> aulas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectivoGeral() {
        return objectivoGeral;
    }

    public PlanoAula objectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
        return this;
    }

    public void setObjectivoGeral(String objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
    }

    public String getObjectivoEspecifico() {
        return objectivoEspecifico;
    }

    public PlanoAula objectivoEspecifico(String objectivoEspecifico) {
        this.objectivoEspecifico = objectivoEspecifico;
        return this;
    }

    public void setObjectivoEspecifico(String objectivoEspecifico) {
        this.objectivoEspecifico = objectivoEspecifico;
    }

    public String getConteudo() {
        return conteudo;
    }

    public PlanoAula conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getEstrategia() {
        return estrategia;
    }

    public PlanoAula estrategia(String estrategia) {
        this.estrategia = estrategia;
        return this;
    }

    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }

    public String getActividades() {
        return actividades;
    }

    public PlanoAula actividades(String actividades) {
        this.actividades = actividades;
        return this;
    }

    public void setActividades(String actividades) {
        this.actividades = actividades;
    }

    public ZonedDateTime getTempo() {
        return tempo;
    }

    public PlanoAula tempo(ZonedDateTime tempo) {
        this.tempo = tempo;
        return this;
    }

    public void setTempo(ZonedDateTime tempo) {
        this.tempo = tempo;
    }

    public String getRecursosEnsino() {
        return recursosEnsino;
    }

    public PlanoAula recursosEnsino(String recursosEnsino) {
        this.recursosEnsino = recursosEnsino;
        return this;
    }

    public void setRecursosEnsino(String recursosEnsino) {
        this.recursosEnsino = recursosEnsino;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public PlanoAula avaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
        return this;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public PlanoAula observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public PlanoAula bibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
        return this;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public String getPerfilEntrada() {
        return perfilEntrada;
    }

    public PlanoAula perfilEntrada(String perfilEntrada) {
        this.perfilEntrada = perfilEntrada;
        return this;
    }

    public void setPerfilEntrada(String perfilEntrada) {
        this.perfilEntrada = perfilEntrada;
    }

    public String getPerfilSaida() {
        return perfilSaida;
    }

    public PlanoAula perfilSaida(String perfilSaida) {
        this.perfilSaida = perfilSaida;
        return this;
    }

    public void setPerfilSaida(String perfilSaida) {
        this.perfilSaida = perfilSaida;
    }

    public byte[] getAnexo1() {
        return anexo1;
    }

    public PlanoAula anexo1(byte[] anexo1) {
        this.anexo1 = anexo1;
        return this;
    }

    public void setAnexo1(byte[] anexo1) {
        this.anexo1 = anexo1;
    }

    public String getAnexo1ContentType() {
        return anexo1ContentType;
    }

    public PlanoAula anexo1ContentType(String anexo1ContentType) {
        this.anexo1ContentType = anexo1ContentType;
        return this;
    }

    public void setAnexo1ContentType(String anexo1ContentType) {
        this.anexo1ContentType = anexo1ContentType;
    }

    public byte[] getAnexo2() {
        return anexo2;
    }

    public PlanoAula anexo2(byte[] anexo2) {
        this.anexo2 = anexo2;
        return this;
    }

    public void setAnexo2(byte[] anexo2) {
        this.anexo2 = anexo2;
    }

    public String getAnexo2ContentType() {
        return anexo2ContentType;
    }

    public PlanoAula anexo2ContentType(String anexo2ContentType) {
        this.anexo2ContentType = anexo2ContentType;
        return this;
    }

    public void setAnexo2ContentType(String anexo2ContentType) {
        this.anexo2ContentType = anexo2ContentType;
    }

    public byte[] getAnexo3() {
        return anexo3;
    }

    public PlanoAula anexo3(byte[] anexo3) {
        this.anexo3 = anexo3;
        return this;
    }

    public void setAnexo3(byte[] anexo3) {
        this.anexo3 = anexo3;
    }

    public String getAnexo3ContentType() {
        return anexo3ContentType;
    }

    public PlanoAula anexo3ContentType(String anexo3ContentType) {
        this.anexo3ContentType = anexo3ContentType;
        return this;
    }

    public void setAnexo3ContentType(String anexo3ContentType) {
        this.anexo3ContentType = anexo3ContentType;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public PlanoAula utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public PlanoAula turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public PlanoAula addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.getPlanoAulas().add(this);
        return this;
    }

    public PlanoAula removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.getPlanoAulas().remove(this);
        return this;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public PlanoAula disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Dossificacao getDossificacao() {
        return dossificacao;
    }

    public PlanoAula dossificacao(Dossificacao dossificacao) {
        this.dossificacao = dossificacao;
        return this;
    }

    public void setDossificacao(Dossificacao dossificacao) {
        this.dossificacao = dossificacao;
    }

    public Set<Aula> getAulas() {
        return aulas;
    }

    public PlanoAula aulas(Set<Aula> aulas) {
        this.aulas = aulas;
        return this;
    }

    public PlanoAula addAula(Aula aula) {
        this.aulas.add(aula);
        aula.getPlanoAulas().add(this);
        return this;
    }

    public PlanoAula removeAula(Aula aula) {
        this.aulas.remove(aula);
        aula.getPlanoAulas().remove(this);
        return this;
    }

    public void setAulas(Set<Aula> aulas) {
        this.aulas = aulas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoAula)) {
            return false;
        }
        return id != null && id.equals(((PlanoAula) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlanoAula{" +
            "id=" + getId() +
            ", objectivoGeral='" + getObjectivoGeral() + "'" +
            ", objectivoEspecifico='" + getObjectivoEspecifico() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", estrategia='" + getEstrategia() + "'" +
            ", actividades='" + getActividades() + "'" +
            ", tempo='" + getTempo() + "'" +
            ", recursosEnsino='" + getRecursosEnsino() + "'" +
            ", avaliacao='" + getAvaliacao() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", bibliografia='" + getBibliografia() + "'" +
            ", perfilEntrada='" + getPerfilEntrada() + "'" +
            ", perfilSaida='" + getPerfilSaida() + "'" +
            ", anexo1='" + getAnexo1() + "'" +
            ", anexo1ContentType='" + getAnexo1ContentType() + "'" +
            ", anexo2='" + getAnexo2() + "'" +
            ", anexo2ContentType='" + getAnexo2ContentType() + "'" +
            ", anexo3='" + getAnexo3() + "'" +
            ", anexo3ContentType='" + getAnexo3ContentType() + "'" +
            "}";
    }
}
