package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class FichaMedicaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaMedica.class);
        FichaMedica fichaMedica1 = new FichaMedica();
        fichaMedica1.setId(1L);
        FichaMedica fichaMedica2 = new FichaMedica();
        fichaMedica2.setId(fichaMedica1.getId());
        assertThat(fichaMedica1).isEqualTo(fichaMedica2);
        fichaMedica2.setId(2L);
        assertThat(fichaMedica1).isNotEqualTo(fichaMedica2);
        fichaMedica1.setId(null);
        assertThat(fichaMedica1).isNotEqualTo(fichaMedica2);
    }
}
