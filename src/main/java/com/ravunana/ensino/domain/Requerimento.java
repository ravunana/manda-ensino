package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Requerimento.
 */
@Entity
@Table(name = "sec_requerimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "requerimento")
public class Requerimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Lob
    @Column(name = "requerimento")
    private byte[] requerimento;

    @Column(name = "requerimento_content_type")
    private String requerimentoContentType;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "status_requerimento")
    private String statusRequerimento;

    @ManyToOne
    @JsonIgnoreProperties("requerimentos")
    private User utilizador;

    @ManyToOne
    @JsonIgnoreProperties("requerimentos")
    private CategoriaRequerimento categoria;

    @ManyToOne
    @JsonIgnoreProperties("requerimentos")
    private Aluno aluno;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getRequerimento() {
        return requerimento;
    }

    public Requerimento requerimento(byte[] requerimento) {
        this.requerimento = requerimento;
        return this;
    }

    public void setRequerimento(byte[] requerimento) {
        this.requerimento = requerimento;
    }

    public String getRequerimentoContentType() {
        return requerimentoContentType;
    }

    public Requerimento requerimentoContentType(String requerimentoContentType) {
        this.requerimentoContentType = requerimentoContentType;
        return this;
    }

    public void setRequerimentoContentType(String requerimentoContentType) {
        this.requerimentoContentType = requerimentoContentType;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Requerimento data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getStatusRequerimento() {
        return statusRequerimento;
    }

    public Requerimento statusRequerimento(String statusRequerimento) {
        this.statusRequerimento = statusRequerimento;
        return this;
    }

    public void setStatusRequerimento(String statusRequerimento) {
        this.statusRequerimento = statusRequerimento;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Requerimento utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public CategoriaRequerimento getCategoria() {
        return categoria;
    }

    public Requerimento categoria(CategoriaRequerimento categoriaRequerimento) {
        this.categoria = categoriaRequerimento;
        return this;
    }

    public void setCategoria(CategoriaRequerimento categoriaRequerimento) {
        this.categoria = categoriaRequerimento;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Requerimento aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requerimento)) {
            return false;
        }
        return id != null && id.equals(((Requerimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Requerimento{" +
            "id=" + getId() +
            ", requerimento='" + getRequerimento() + "'" +
            ", requerimentoContentType='" + getRequerimentoContentType() + "'" +
            ", data='" + getData() + "'" +
            ", statusRequerimento='" + getStatusRequerimento() + "'" +
            "}";
    }
}
