package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DetalhePagamentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalhePagamentoDTO.class);
        DetalhePagamentoDTO detalhePagamentoDTO1 = new DetalhePagamentoDTO();
        detalhePagamentoDTO1.setId(1L);
        DetalhePagamentoDTO detalhePagamentoDTO2 = new DetalhePagamentoDTO();
        assertThat(detalhePagamentoDTO1).isNotEqualTo(detalhePagamentoDTO2);
        detalhePagamentoDTO2.setId(detalhePagamentoDTO1.getId());
        assertThat(detalhePagamentoDTO1).isEqualTo(detalhePagamentoDTO2);
        detalhePagamentoDTO2.setId(2L);
        assertThat(detalhePagamentoDTO1).isNotEqualTo(detalhePagamentoDTO2);
        detalhePagamentoDTO1.setId(null);
        assertThat(detalhePagamentoDTO1).isNotEqualTo(detalhePagamentoDTO2);
    }
}
