package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class OcorrenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ocorrencia.class);
        Ocorrencia ocorrencia1 = new Ocorrencia();
        ocorrencia1.setId(1L);
        Ocorrencia ocorrencia2 = new Ocorrencia();
        ocorrencia2.setId(ocorrencia1.getId());
        assertThat(ocorrencia1).isEqualTo(ocorrencia2);
        ocorrencia2.setId(2L);
        assertThat(ocorrencia1).isNotEqualTo(ocorrencia2);
        ocorrencia1.setId(null);
        assertThat(ocorrencia1).isNotEqualTo(ocorrencia2);
    }
}
