package com.ravunana.ensino.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.MatrizCurricular} entity.
 */
public class MatrizCurricularDTO implements Serializable {

    private Long id;


    private Long cursoId;

    private String cursoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatrizCurricularDTO matrizCurricularDTO = (MatrizCurricularDTO) o;
        if (matrizCurricularDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matrizCurricularDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatrizCurricularDTO{" +
            "id=" + getId() +
            ", cursoId=" + getCursoId() +
            ", cursoNome='" + getCursoNome() + "'" +
            "}";
    }
}
