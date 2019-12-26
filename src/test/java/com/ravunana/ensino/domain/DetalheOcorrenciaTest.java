package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DetalheOcorrenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalheOcorrencia.class);
        DetalheOcorrencia detalheOcorrencia1 = new DetalheOcorrencia();
        detalheOcorrencia1.setId(1L);
        DetalheOcorrencia detalheOcorrencia2 = new DetalheOcorrencia();
        detalheOcorrencia2.setId(detalheOcorrencia1.getId());
        assertThat(detalheOcorrencia1).isEqualTo(detalheOcorrencia2);
        detalheOcorrencia2.setId(2L);
        assertThat(detalheOcorrencia1).isNotEqualTo(detalheOcorrencia2);
        detalheOcorrencia1.setId(null);
        assertThat(detalheOcorrencia1).isNotEqualTo(detalheOcorrencia2);
    }
}
