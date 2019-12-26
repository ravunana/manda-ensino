package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class ContratoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratoDTO.class);
        ContratoDTO contratoDTO1 = new ContratoDTO();
        contratoDTO1.setId(1L);
        ContratoDTO contratoDTO2 = new ContratoDTO();
        assertThat(contratoDTO1).isNotEqualTo(contratoDTO2);
        contratoDTO2.setId(contratoDTO1.getId());
        assertThat(contratoDTO1).isEqualTo(contratoDTO2);
        contratoDTO2.setId(2L);
        assertThat(contratoDTO1).isNotEqualTo(contratoDTO2);
        contratoDTO1.setId(null);
        assertThat(contratoDTO1).isNotEqualTo(contratoDTO2);
    }
}
