package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class LocalizacaoInstituicaoEnsinoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalizacaoInstituicaoEnsino.class);
        LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino1 = new LocalizacaoInstituicaoEnsino();
        localizacaoInstituicaoEnsino1.setId(1L);
        LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino2 = new LocalizacaoInstituicaoEnsino();
        localizacaoInstituicaoEnsino2.setId(localizacaoInstituicaoEnsino1.getId());
        assertThat(localizacaoInstituicaoEnsino1).isEqualTo(localizacaoInstituicaoEnsino2);
        localizacaoInstituicaoEnsino2.setId(2L);
        assertThat(localizacaoInstituicaoEnsino1).isNotEqualTo(localizacaoInstituicaoEnsino2);
        localizacaoInstituicaoEnsino1.setId(null);
        assertThat(localizacaoInstituicaoEnsino1).isNotEqualTo(localizacaoInstituicaoEnsino2);
    }
}
