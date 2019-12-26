package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class AulaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AulaDTO.class);
        AulaDTO aulaDTO1 = new AulaDTO();
        aulaDTO1.setId(1L);
        AulaDTO aulaDTO2 = new AulaDTO();
        assertThat(aulaDTO1).isNotEqualTo(aulaDTO2);
        aulaDTO2.setId(aulaDTO1.getId());
        assertThat(aulaDTO1).isEqualTo(aulaDTO2);
        aulaDTO2.setId(2L);
        assertThat(aulaDTO1).isNotEqualTo(aulaDTO2);
        aulaDTO1.setId(null);
        assertThat(aulaDTO1).isNotEqualTo(aulaDTO2);
    }
}
