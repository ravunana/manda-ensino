package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DetalheOcorrenciaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalheOcorrenciaDTO.class);
        DetalheOcorrenciaDTO detalheOcorrenciaDTO1 = new DetalheOcorrenciaDTO();
        detalheOcorrenciaDTO1.setId(1L);
        DetalheOcorrenciaDTO detalheOcorrenciaDTO2 = new DetalheOcorrenciaDTO();
        assertThat(detalheOcorrenciaDTO1).isNotEqualTo(detalheOcorrenciaDTO2);
        detalheOcorrenciaDTO2.setId(detalheOcorrenciaDTO1.getId());
        assertThat(detalheOcorrenciaDTO1).isEqualTo(detalheOcorrenciaDTO2);
        detalheOcorrenciaDTO2.setId(2L);
        assertThat(detalheOcorrenciaDTO1).isNotEqualTo(detalheOcorrenciaDTO2);
        detalheOcorrenciaDTO1.setId(null);
        assertThat(detalheOcorrenciaDTO1).isNotEqualTo(detalheOcorrenciaDTO2);
    }
}
