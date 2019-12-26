package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DetalhePagamentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalhePagamento.class);
        DetalhePagamento detalhePagamento1 = new DetalhePagamento();
        detalhePagamento1.setId(1L);
        DetalhePagamento detalhePagamento2 = new DetalhePagamento();
        detalhePagamento2.setId(detalhePagamento1.getId());
        assertThat(detalhePagamento1).isEqualTo(detalhePagamento2);
        detalhePagamento2.setId(2L);
        assertThat(detalhePagamento1).isNotEqualTo(detalhePagamento2);
        detalhePagamento1.setId(null);
        assertThat(detalhePagamento1).isNotEqualTo(detalhePagamento2);
    }
}
