package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class CategoriaValiacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaValiacao.class);
        CategoriaValiacao categoriaValiacao1 = new CategoriaValiacao();
        categoriaValiacao1.setId(1L);
        CategoriaValiacao categoriaValiacao2 = new CategoriaValiacao();
        categoriaValiacao2.setId(categoriaValiacao1.getId());
        assertThat(categoriaValiacao1).isEqualTo(categoriaValiacao2);
        categoriaValiacao2.setId(2L);
        assertThat(categoriaValiacao1).isNotEqualTo(categoriaValiacao2);
        categoriaValiacao1.setId(null);
        assertThat(categoriaValiacao1).isNotEqualTo(categoriaValiacao2);
    }
}
