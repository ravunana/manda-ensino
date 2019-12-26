package com.ravunana.ensino.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AreaFormacao.
 */
@Entity
@Table(name = "pdg_area_formacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "areaformacao")
public class AreaFormacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 10)
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "competencias")
    private String competencias;

    @OneToMany(mappedBy = "areaFormacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Curso> cursos = new HashSet<>();

    @OneToMany(mappedBy = "areaFormacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CategoriaValiacao> categoriaValiacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public AreaFormacao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCompetencias() {
        return competencias;
    }

    public AreaFormacao competencias(String competencias) {
        this.competencias = competencias;
        return this;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public AreaFormacao cursos(Set<Curso> cursos) {
        this.cursos = cursos;
        return this;
    }

    public AreaFormacao addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.setAreaFormacao(this);
        return this;
    }

    public AreaFormacao removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.setAreaFormacao(null);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public Set<CategoriaValiacao> getCategoriaValiacaos() {
        return categoriaValiacaos;
    }

    public AreaFormacao categoriaValiacaos(Set<CategoriaValiacao> categoriaValiacaos) {
        this.categoriaValiacaos = categoriaValiacaos;
        return this;
    }

    public AreaFormacao addCategoriaValiacao(CategoriaValiacao categoriaValiacao) {
        this.categoriaValiacaos.add(categoriaValiacao);
        categoriaValiacao.setAreaFormacao(this);
        return this;
    }

    public AreaFormacao removeCategoriaValiacao(CategoriaValiacao categoriaValiacao) {
        this.categoriaValiacaos.remove(categoriaValiacao);
        categoriaValiacao.setAreaFormacao(null);
        return this;
    }

    public void setCategoriaValiacaos(Set<CategoriaValiacao> categoriaValiacaos) {
        this.categoriaValiacaos = categoriaValiacaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaFormacao)) {
            return false;
        }
        return id != null && id.equals(((AreaFormacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AreaFormacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", competencias='" + getCompetencias() + "'" +
            "}";
    }
}
