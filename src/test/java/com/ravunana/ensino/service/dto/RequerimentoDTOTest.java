package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class RequerimentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequerimentoDTO.class);
        RequerimentoDTO requerimentoDTO1 = new RequerimentoDTO();
        requerimentoDTO1.setId(1L);
        RequerimentoDTO requerimentoDTO2 = new RequerimentoDTO();
        assertThat(requerimentoDTO1).isNotEqualTo(requerimentoDTO2);
        requerimentoDTO2.setId(requerimentoDTO1.getId());
        assertThat(requerimentoDTO1).isEqualTo(requerimentoDTO2);
        requerimentoDTO2.setId(2L);
        assertThat(requerimentoDTO1).isNotEqualTo(requerimentoDTO2);
        requerimentoDTO1.setId(null);
        assertThat(requerimentoDTO1).isNotEqualTo(requerimentoDTO2);
    }
}
