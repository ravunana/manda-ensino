package com.ravunana.ensino.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.CategoriaRequerimento} entity.
 */
public class CategoriaRequerimentoDTO implements Serializable {

    private Long id;

    private String nome;

    private Integer tempoResposta;

    private Boolean pagase;

    @Lob
    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTempoResposta() {
        return tempoResposta;
    }

    public void setTempoResposta(Integer tempoResposta) {
        this.tempoResposta = tempoResposta;
    }

    public Boolean isPagase() {
        return pagase;
    }

    public void setPagase(Boolean pagase) {
        this.pagase = pagase;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoriaRequerimentoDTO categoriaRequerimentoDTO = (CategoriaRequerimentoDTO) o;
        if (categoriaRequerimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriaRequerimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoriaRequerimentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tempoResposta=" + getTempoResposta() +
            ", pagase='" + isPagase() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
