package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategoriaValiacao.
 */
@Entity
@Table(name = "pdg_categoria_avaliacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "categoriavaliacao")
public class CategoriaValiacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    
    @Column(name = "sigla_interna", unique = true)
    private String siglaInterna;

    @NotNull
    @Column(name = "sigla_pauta", nullable = false, unique = true)
    private String siglaPauta;

    @OneToMany(mappedBy = "categoriaAvaliacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Nota> notas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("categoriaValiacaos")
    private AreaFormacao areaFormacao;

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

    public CategoriaValiacao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSiglaInterna() {
        return siglaInterna;
    }

    public CategoriaValiacao siglaInterna(String siglaInterna) {
        this.siglaInterna = siglaInterna;
        return this;
    }

    public void setSiglaInterna(String siglaInterna) {
        this.siglaInterna = siglaInterna;
    }

    public String getSiglaPauta() {
        return siglaPauta;
    }

    public CategoriaValiacao siglaPauta(String siglaPauta) {
        this.siglaPauta = siglaPauta;
        return this;
    }

    public void setSiglaPauta(String siglaPauta) {
        this.siglaPauta = siglaPauta;
    }

    public Set<Nota> getNotas() {
        return notas;
    }

    public CategoriaValiacao notas(Set<Nota> notas) {
        this.notas = notas;
        return this;
    }

    public CategoriaValiacao addNota(Nota nota) {
        this.notas.add(nota);
        nota.setCategoriaAvaliacao(this);
        return this;
    }

    public CategoriaValiacao removeNota(Nota nota) {
        this.notas.remove(nota);
        nota.setCategoriaAvaliacao(null);
        return this;
    }

    public void setNotas(Set<Nota> notas) {
        this.notas = notas;
    }

    public AreaFormacao getAreaFormacao() {
        return areaFormacao;
    }

    public CategoriaValiacao areaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacao = areaFormacao;
        return this;
    }

    public void setAreaFormacao(AreaFormacao areaFormacao) {
        this.areaFormacao = areaFormacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaValiacao)) {
            return false;
        }
        return id != null && id.equals(((CategoriaValiacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoriaValiacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", siglaInterna='" + getSiglaInterna() + "'" +
            ", siglaPauta='" + getSiglaPauta() + "'" +
            "}";
    }
}
