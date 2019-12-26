package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class CategoriaAlunoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaAlunoDTO.class);
        CategoriaAlunoDTO categoriaAlunoDTO1 = new CategoriaAlunoDTO();
        categoriaAlunoDTO1.setId(1L);
        CategoriaAlunoDTO categoriaAlunoDTO2 = new CategoriaAlunoDTO();
        assertThat(categoriaAlunoDTO1).isNotEqualTo(categoriaAlunoDTO2);
        categoriaAlunoDTO2.setId(categoriaAlunoDTO1.getId());
        assertThat(categoriaAlunoDTO1).isEqualTo(categoriaAlunoDTO2);
        categoriaAlunoDTO2.setId(2L);
        assertThat(categoriaAlunoDTO1).isNotEqualTo(categoriaAlunoDTO2);
        categoriaAlunoDTO1.setId(null);
        assertThat(categoriaAlunoDTO1).isNotEqualTo(categoriaAlunoDTO2);
    }
}
