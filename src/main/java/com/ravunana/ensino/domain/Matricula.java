package com.ravunana.ensino.domain;
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
 * A Matricula.
 */
@Entity
@Table(name = "sec_matricula")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matricula")
public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "data")
    private ZonedDateTime data;

    @NotNull
    @Min(value = 1)
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @NotNull
    @Column(name = "ano_lectivo", nullable = false)
    private Integer anoLectivo;

    @NotNull
    @Column(name = "perido_lectivo", nullable = false)
    private String peridoLectivo;

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nota> notas = new HashSet<>();

    @OneToMany(mappedBy = "confirmacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Matricula> matriculas = new HashSet<>();

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    @OneToMany(mappedBy = "matricula")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DocumentoMatricula> documentoMatriculas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("matriculas")
    private User utilizador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("matriculas")
    private Aluno aluno;

    @ManyToOne
    @JsonIgnoreProperties("matriculas")
    private Matricula confirmacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("matriculas")
    private CategoriaAluno categoria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("matriculas")
    private Turma turma;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Matricula data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Integer getNumero() {
        return numero;
    }

    public Matricula numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getObservacao() {
        return observacao;
    }

    public Matricula observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getAnoLectivo() {
        return anoLectivo;
    }

    public Matricula anoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
        return this;
    }

    public void setAnoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public String getPeridoLectivo() {
        return peridoLectivo;
    }

    public Matricula peridoLectivo(String peridoLectivo) {
        this.peridoLectivo = peridoLectivo;
        return this;
    }

    public void setPeridoLectivo(String peridoLectivo) {
        this.peridoLectivo = peridoLectivo;
    }

    public Set<Nota> getNotas() {
        return notas;
    }

    public Matricula notas(Set<Nota> notas) {
        this.notas = notas;
        return this;
    }

    public Matricula addNota(Nota nota) {
        this.notas.add(nota);
        nota.setMatricula(this);
        return this;
    }

    public Matricula removeNota(Nota nota) {
        this.notas.remove(nota);
        nota.setMatricula(null);
        return this;
    }

    public void setNotas(Set<Nota> notas) {
        this.notas = notas;
    }

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public Matricula matriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
        return this;
    }

    public Matricula addMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setConfirmacao(this);
        return this;
    }

    public Matricula removeMatricula(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setConfirmacao(null);
        return this;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }

    public Matricula ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
        return this;
    }

    public Matricula addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setMatricula(this);
        return this;
    }

    public Matricula removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setMatricula(null);
        return this;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public Set<DocumentoMatricula> getDocumentoMatriculas() {
        return documentoMatriculas;
    }

    public Matricula documentoMatriculas(Set<DocumentoMatricula> documentoMatriculas) {
        this.documentoMatriculas = documentoMatriculas;
        return this;
    }

    public Matricula addDocumentoMatricula(DocumentoMatricula documentoMatricula) {
        this.documentoMatriculas.add(documentoMatricula);
        documentoMatricula.setMatricula(this);
        return this;
    }

    public Matricula removeDocumentoMatricula(DocumentoMatricula documentoMatricula) {
        this.documentoMatriculas.remove(documentoMatricula);
        documentoMatricula.setMatricula(null);
        return this;
    }

    public void setDocumentoMatriculas(Set<DocumentoMatricula> documentoMatriculas) {
        this.documentoMatriculas = documentoMatriculas;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Matricula utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Matricula aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Matricula getConfirmacao() {
        return confirmacao;
    }

    public Matricula confirmacao(Matricula matricula) {
        this.confirmacao = matricula;
        return this;
    }

    public void setConfirmacao(Matricula matricula) {
        this.confirmacao = matricula;
    }

    public CategoriaAluno getCategoria() {
        return categoria;
    }

    public Matricula categoria(CategoriaAluno categoriaAluno) {
        this.categoria = categoriaAluno;
        return this;
    }

    public void setCategoria(CategoriaAluno categoriaAluno) {
        this.categoria = categoriaAluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public Matricula turma(Turma turma) {
        this.turma = turma;
        return this;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matricula)) {
            return false;
        }
        return id != null && id.equals(((Matricula) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Matricula{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", numero=" + getNumero() +
            ", observacao='" + getObservacao() + "'" +
            ", anoLectivo=" + getAnoLectivo() +
            ", peridoLectivo='" + getPeridoLectivo() + "'" +
            "}";
    }
}
