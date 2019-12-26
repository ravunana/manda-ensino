package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class CategoriaValiacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaValiacaoDTO.class);
        CategoriaValiacaoDTO categoriaValiacaoDTO1 = new CategoriaValiacaoDTO();
        categoriaValiacaoDTO1.setId(1L);
        CategoriaValiacaoDTO categoriaValiacaoDTO2 = new CategoriaValiacaoDTO();
        assertThat(categoriaValiacaoDTO1).isNotEqualTo(categoriaValiacaoDTO2);
        categoriaValiacaoDTO2.setId(categoriaValiacaoDTO1.getId());
        assertThat(categoriaValiacaoDTO1).isEqualTo(categoriaValiacaoDTO2);
        categoriaValiacaoDTO2.setId(2L);
        assertThat(categoriaValiacaoDTO1).isNotEqualTo(categoriaValiacaoDTO2);
        categoriaValiacaoDTO1.setId(null);
        assertThat(categoriaValiacaoDTO1).isNotEqualTo(categoriaValiacaoDTO2);
    }
}
