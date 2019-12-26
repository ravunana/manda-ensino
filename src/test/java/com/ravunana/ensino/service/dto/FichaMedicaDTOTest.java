package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class FichaMedicaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaMedicaDTO.class);
        FichaMedicaDTO fichaMedicaDTO1 = new FichaMedicaDTO();
        fichaMedicaDTO1.setId(1L);
        FichaMedicaDTO fichaMedicaDTO2 = new FichaMedicaDTO();
        assertThat(fichaMedicaDTO1).isNotEqualTo(fichaMedicaDTO2);
        fichaMedicaDTO2.setId(fichaMedicaDTO1.getId());
        assertThat(fichaMedicaDTO1).isEqualTo(fichaMedicaDTO2);
        fichaMedicaDTO2.setId(2L);
        assertThat(fichaMedicaDTO1).isNotEqualTo(fichaMedicaDTO2);
        fichaMedicaDTO1.setId(null);
        assertThat(fichaMedicaDTO1).isNotEqualTo(fichaMedicaDTO2);
    }
}
