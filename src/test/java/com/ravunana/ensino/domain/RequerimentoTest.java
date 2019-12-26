package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class RequerimentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requerimento.class);
        Requerimento requerimento1 = new Requerimento();
        requerimento1.setId(1L);
        Requerimento requerimento2 = new Requerimento();
        requerimento2.setId(requerimento1.getId());
        assertThat(requerimento1).isEqualTo(requerimento2);
        requerimento2.setId(2L);
        assertThat(requerimento1).isNotEqualTo(requerimento2);
        requerimento1.setId(null);
        assertThat(requerimento1).isNotEqualTo(requerimento2);
    }
}
