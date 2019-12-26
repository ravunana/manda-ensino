package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.CategoriaValiacao} entity.
 */
public class CategoriaValiacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    
    private String siglaInterna;

    @NotNull
    private String siglaPauta;


    private Long areaFormacaoId;

    private String areaFormacaoNome;

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

    public String getSiglaInterna() {
        return siglaInterna;
    }

    public void setSiglaInterna(String siglaInterna) {
        this.siglaInterna = siglaInterna;
    }

    public String getSiglaPauta() {
        return siglaPauta;
    }

    public void setSiglaPauta(String siglaPauta) {
        this.siglaPauta = siglaPauta;
    }

    public Long getAreaFormacaoId() {
        return areaFormacaoId;
    }

    public void setAreaFormacaoId(Long areaFormacaoId) {
        this.areaFormacaoId = areaFormacaoId;
    }

    public String getAreaFormacaoNome() {
        return areaFormacaoNome;
    }

    public void setAreaFormacaoNome(String areaFormacaoNome) {
        this.areaFormacaoNome = areaFormacaoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoriaValiacaoDTO categoriaValiacaoDTO = (CategoriaValiacaoDTO) o;
        if (categoriaValiacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriaValiacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoriaValiacaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", siglaInterna='" + getSiglaInterna() + "'" +
            ", siglaPauta='" + getSiglaPauta() + "'" +
            ", areaFormacaoId=" + getAreaFormacaoId() +
            ", areaFormacaoNome='" + getAreaFormacaoNome() + "'" +
            "}";
    }
}
