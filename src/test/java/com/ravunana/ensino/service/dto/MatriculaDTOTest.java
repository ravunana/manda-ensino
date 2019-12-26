package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class MatriculaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatriculaDTO.class);
        MatriculaDTO matriculaDTO1 = new MatriculaDTO();
        matriculaDTO1.setId(1L);
        MatriculaDTO matriculaDTO2 = new MatriculaDTO();
        assertThat(matriculaDTO1).isNotEqualTo(matriculaDTO2);
        matriculaDTO2.setId(matriculaDTO1.getId());
        assertThat(matriculaDTO1).isEqualTo(matriculaDTO2);
        matriculaDTO2.setId(2L);
        assertThat(matriculaDTO1).isNotEqualTo(matriculaDTO2);
        matriculaDTO1.setId(null);
        assertThat(matriculaDTO1).isNotEqualTo(matriculaDTO2);
    }
}
