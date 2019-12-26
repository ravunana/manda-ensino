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
 * A Sala.
 */
@Entity
@Table(name = "pdg_sala")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sala")
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private Integer numero;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Min(value = 30)
    @Column(name = "lotacao", nullable = false)
    private Integer lotacao;

    @OneToMany(mappedBy = "sala")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Turma> turmas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Sala numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public Sala descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLotacao() {
        return lotacao;
    }

    public Sala lotacao(Integer lotacao) {
        this.lotacao = lotacao;
        return this;
    }

    public void setLotacao(Integer lotacao) {
        this.lotacao = lotacao;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public Sala turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public Sala addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.setSala(this);
        return this;
    }

    public Sala removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.setSala(null);
        return this;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sala)) {
            return false;
        }
        return id != null && id.equals(((Sala) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sala{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", descricao='" + getDescricao() + "'" +
            ", lotacao=" + getLotacao() +
            "}";
    }
}
