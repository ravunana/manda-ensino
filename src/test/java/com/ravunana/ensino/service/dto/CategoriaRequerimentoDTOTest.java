package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class CategoriaRequerimentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaRequerimentoDTO.class);
        CategoriaRequerimentoDTO categoriaRequerimentoDTO1 = new CategoriaRequerimentoDTO();
        categoriaRequerimentoDTO1.setId(1L);
        CategoriaRequerimentoDTO categoriaRequerimentoDTO2 = new CategoriaRequerimentoDTO();
        assertThat(categoriaRequerimentoDTO1).isNotEqualTo(categoriaRequerimentoDTO2);
        categoriaRequerimentoDTO2.setId(categoriaRequerimentoDTO1.getId());
        assertThat(categoriaRequerimentoDTO1).isEqualTo(categoriaRequerimentoDTO2);
        categoriaRequerimentoDTO2.setId(2L);
        assertThat(categoriaRequerimentoDTO1).isNotEqualTo(categoriaRequerimentoDTO2);
        categoriaRequerimentoDTO1.setId(null);
        assertThat(categoriaRequerimentoDTO1).isNotEqualTo(categoriaRequerimentoDTO2);
    }
}
