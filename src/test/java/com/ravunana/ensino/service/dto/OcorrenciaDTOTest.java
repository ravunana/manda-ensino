package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class OcorrenciaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OcorrenciaDTO.class);
        OcorrenciaDTO ocorrenciaDTO1 = new OcorrenciaDTO();
        ocorrenciaDTO1.setId(1L);
        OcorrenciaDTO ocorrenciaDTO2 = new OcorrenciaDTO();
        assertThat(ocorrenciaDTO1).isNotEqualTo(ocorrenciaDTO2);
        ocorrenciaDTO2.setId(ocorrenciaDTO1.getId());
        assertThat(ocorrenciaDTO1).isEqualTo(ocorrenciaDTO2);
        ocorrenciaDTO2.setId(2L);
        assertThat(ocorrenciaDTO1).isNotEqualTo(ocorrenciaDTO2);
        ocorrenciaDTO1.setId(null);
        assertThat(ocorrenciaDTO1).isNotEqualTo(ocorrenciaDTO2);
    }
}
