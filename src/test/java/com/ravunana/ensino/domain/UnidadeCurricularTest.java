package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class UnidadeCurricularTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeCurricular.class);
        UnidadeCurricular unidadeCurricular1 = new UnidadeCurricular();
        unidadeCurricular1.setId(1L);
        UnidadeCurricular unidadeCurricular2 = new UnidadeCurricular();
        unidadeCurricular2.setId(unidadeCurricular1.getId());
        assertThat(unidadeCurricular1).isEqualTo(unidadeCurricular2);
        unidadeCurricular2.setId(2L);
        assertThat(unidadeCurricular1).isNotEqualTo(unidadeCurricular2);
        unidadeCurricular1.setId(null);
        assertThat(unidadeCurricular1).isNotEqualTo(unidadeCurricular2);
    }
}
