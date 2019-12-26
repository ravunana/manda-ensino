package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class MatrizCurricularTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatrizCurricular.class);
        MatrizCurricular matrizCurricular1 = new MatrizCurricular();
        matrizCurricular1.setId(1L);
        MatrizCurricular matrizCurricular2 = new MatrizCurricular();
        matrizCurricular2.setId(matrizCurricular1.getId());
        assertThat(matrizCurricular1).isEqualTo(matrizCurricular2);
        matrizCurricular2.setId(2L);
        assertThat(matrizCurricular1).isNotEqualTo(matrizCurricular2);
        matrizCurricular1.setId(null);
        assertThat(matrizCurricular1).isNotEqualTo(matrizCurricular2);
    }
}
