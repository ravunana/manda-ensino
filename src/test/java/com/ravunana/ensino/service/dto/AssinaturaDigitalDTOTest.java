package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class AssinaturaDigitalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssinaturaDigitalDTO.class);
        AssinaturaDigitalDTO assinaturaDigitalDTO1 = new AssinaturaDigitalDTO();
        assinaturaDigitalDTO1.setId(1L);
        AssinaturaDigitalDTO assinaturaDigitalDTO2 = new AssinaturaDigitalDTO();
        assertThat(assinaturaDigitalDTO1).isNotEqualTo(assinaturaDigitalDTO2);
        assinaturaDigitalDTO2.setId(assinaturaDigitalDTO1.getId());
        assertThat(assinaturaDigitalDTO1).isEqualTo(assinaturaDigitalDTO2);
        assinaturaDigitalDTO2.setId(2L);
        assertThat(assinaturaDigitalDTO1).isNotEqualTo(assinaturaDigitalDTO2);
        assinaturaDigitalDTO1.setId(null);
        assertThat(assinaturaDigitalDTO1).isNotEqualTo(assinaturaDigitalDTO2);
    }
}
