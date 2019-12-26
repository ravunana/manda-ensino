package com.ravunana.ensino.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategoriaRequerimento.
 */
@Entity
@Table(name = "sec_categoria_requerimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "categoriarequerimento")
public class CategoriaRequerimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tempo_resposta")
    private Integer tempoResposta;

    @Column(name = "pagase")
    private Boolean pagase;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "categoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimento> requerimentos = new HashSet<>();

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

    public CategoriaRequerimento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTempoResposta() {
        return tempoResposta;
    }

    public CategoriaRequerimento tempoResposta(Integer tempoResposta) {
        this.tempoResposta = tempoResposta;
        return this;
    }

    public void setTempoResposta(Integer tempoResposta) {
        this.tempoResposta = tempoResposta;
    }

    public Boolean isPagase() {
        return pagase;
    }

    public CategoriaRequerimento pagase(Boolean pagase) {
        this.pagase = pagase;
        return this;
    }

    public void setPagase(Boolean pagase) {
        this.pagase = pagase;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaRequerimento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Requerimento> getRequerimentos() {
        return requerimentos;
    }

    public CategoriaRequerimento requerimentos(Set<Requerimento> requerimentos) {
        this.requerimentos = requerimentos;
        return this;
    }

    public CategoriaRequerimento addRequerimento(Requerimento requerimento) {
        this.requerimentos.add(requerimento);
        requerimento.setCategoria(this);
        return this;
    }

    public CategoriaRequerimento removeRequerimento(Requerimento requerimento) {
        this.requerimentos.remove(requerimento);
        requerimento.setCategoria(null);
        return this;
    }

    public void setRequerimentos(Set<Requerimento> requerimentos) {
        this.requerimentos = requerimentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaRequerimento)) {
            return false;
        }
        return id != null && id.equals(((CategoriaRequerimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoriaRequerimento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tempoResposta=" + getTempoResposta() +
            ", pagase='" + isPagase() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
