package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class CategoriaAlunoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaAluno.class);
        CategoriaAluno categoriaAluno1 = new CategoriaAluno();
        categoriaAluno1.setId(1L);
        CategoriaAluno categoriaAluno2 = new CategoriaAluno();
        categoriaAluno2.setId(categoriaAluno1.getId());
        assertThat(categoriaAluno1).isEqualTo(categoriaAluno2);
        categoriaAluno2.setId(2L);
        assertThat(categoriaAluno1).isNotEqualTo(categoriaAluno2);
        categoriaAluno1.setId(null);
        assertThat(categoriaAluno1).isNotEqualTo(categoriaAluno2);
    }
}
