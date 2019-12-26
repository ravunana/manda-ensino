package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A UnidadeCurricular.
 */
@Entity
@Table(name = "pdg_unidade_curricular")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "unidadecurricular")
public class UnidadeCurricular implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @NotNull
    @Column(name = "unidade", nullable = false)
    private String unidade;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @OneToMany(mappedBy = "herarquia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UnidadeCurricular> unidadeCurriculars = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("unidadeCurriculars")
    private Disciplina disciplina;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("unidadeCurriculars")
    private Classe classe;

    @ManyToOne
    @JsonIgnoreProperties("unidadeCurriculars")
    private UnidadeCurricular herarquia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public UnidadeCurricular descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public UnidadeCurricular unidade(String unidade) {
        this.unidade = unidade;
        return this;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Integer getNumero() {
        return numero;
    }

    public UnidadeCurricular numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Set<UnidadeCurricular> getUnidadeCurriculars() {
        return unidadeCurriculars;
    }

    public UnidadeCurricular unidadeCurriculars(Set<UnidadeCurricular> unidadeCurriculars) {
        this.unidadeCurriculars = unidadeCurriculars;
        return this;
    }

    public UnidadeCurricular addUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurriculars.add(unidadeCurricular);
        unidadeCurricular.setHerarquia(this);
        return this;
    }

    public UnidadeCurricular removeUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurriculars.remove(unidadeCurricular);
        unidadeCurricular.setHerarquia(null);
        return this;
    }

    public void setUnidadeCurriculars(Set<UnidadeCurricular> unidadeCurriculars) {
        this.unidadeCurriculars = unidadeCurriculars;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public UnidadeCurricular disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Classe getClasse() {
        return classe;
    }

    public UnidadeCurricular classe(Classe classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public UnidadeCurricular getHerarquia() {
        return herarquia;
    }

    public UnidadeCurricular herarquia(UnidadeCurricular unidadeCurricular) {
        this.herarquia = unidadeCurricular;
        return this;
    }

    public void setHerarquia(UnidadeCurricular unidadeCurricular) {
        this.herarquia = unidadeCurricular;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadeCurricular)) {
            return false;
        }
        return id != null && id.equals(((UnidadeCurricular) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UnidadeCurricular{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", unidade='" + getUnidade() + "'" +
            ", numero=" + getNumero() +
            "}";
    }
}
