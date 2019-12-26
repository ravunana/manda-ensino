package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class SituacaoAcademicaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacaoAcademica.class);
        SituacaoAcademica situacaoAcademica1 = new SituacaoAcademica();
        situacaoAcademica1.setId(1L);
        SituacaoAcademica situacaoAcademica2 = new SituacaoAcademica();
        situacaoAcademica2.setId(situacaoAcademica1.getId());
        assertThat(situacaoAcademica1).isEqualTo(situacaoAcademica2);
        situacaoAcademica2.setId(2L);
        assertThat(situacaoAcademica1).isNotEqualTo(situacaoAcademica2);
        situacaoAcademica1.setId(null);
        assertThat(situacaoAcademica1).isNotEqualTo(situacaoAcademica2);
    }
}
