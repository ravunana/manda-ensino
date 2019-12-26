package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class CategoriaRequerimentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaRequerimento.class);
        CategoriaRequerimento categoriaRequerimento1 = new CategoriaRequerimento();
        categoriaRequerimento1.setId(1L);
        CategoriaRequerimento categoriaRequerimento2 = new CategoriaRequerimento();
        categoriaRequerimento2.setId(categoriaRequerimento1.getId());
        assertThat(categoriaRequerimento1).isEqualTo(categoriaRequerimento2);
        categoriaRequerimento2.setId(2L);
        assertThat(categoriaRequerimento1).isNotEqualTo(categoriaRequerimento2);
        categoriaRequerimento1.setId(null);
        assertThat(categoriaRequerimento1).isNotEqualTo(categoriaRequerimento2);
    }
}
