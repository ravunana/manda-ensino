package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class AreaFormacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaFormacao.class);
        AreaFormacao areaFormacao1 = new AreaFormacao();
        areaFormacao1.setId(1L);
        AreaFormacao areaFormacao2 = new AreaFormacao();
        areaFormacao2.setId(areaFormacao1.getId());
        assertThat(areaFormacao1).isEqualTo(areaFormacao2);
        areaFormacao2.setId(2L);
        assertThat(areaFormacao1).isNotEqualTo(areaFormacao2);
        areaFormacao1.setId(null);
        assertThat(areaFormacao1).isNotEqualTo(areaFormacao2);
    }
}
