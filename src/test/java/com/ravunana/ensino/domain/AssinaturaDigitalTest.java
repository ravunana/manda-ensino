package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class AssinaturaDigitalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssinaturaDigital.class);
        AssinaturaDigital assinaturaDigital1 = new AssinaturaDigital();
        assinaturaDigital1.setId(1L);
        AssinaturaDigital assinaturaDigital2 = new AssinaturaDigital();
        assinaturaDigital2.setId(assinaturaDigital1.getId());
        assertThat(assinaturaDigital1).isEqualTo(assinaturaDigital2);
        assinaturaDigital2.setId(2L);
        assertThat(assinaturaDigital1).isNotEqualTo(assinaturaDigital2);
        assinaturaDigital1.setId(null);
        assertThat(assinaturaDigital1).isNotEqualTo(assinaturaDigital2);
    }
}
