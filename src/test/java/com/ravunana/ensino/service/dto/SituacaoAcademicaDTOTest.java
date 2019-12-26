package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class SituacaoAcademicaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacaoAcademicaDTO.class);
        SituacaoAcademicaDTO situacaoAcademicaDTO1 = new SituacaoAcademicaDTO();
        situacaoAcademicaDTO1.setId(1L);
        SituacaoAcademicaDTO situacaoAcademicaDTO2 = new SituacaoAcademicaDTO();
        assertThat(situacaoAcademicaDTO1).isNotEqualTo(situacaoAcademicaDTO2);
        situacaoAcademicaDTO2.setId(situacaoAcademicaDTO1.getId());
        assertThat(situacaoAcademicaDTO1).isEqualTo(situacaoAcademicaDTO2);
        situacaoAcademicaDTO2.setId(2L);
        assertThat(situacaoAcademicaDTO1).isNotEqualTo(situacaoAcademicaDTO2);
        situacaoAcademicaDTO1.setId(null);
        assertThat(situacaoAcademicaDTO1).isNotEqualTo(situacaoAcademicaDTO2);
    }
}
