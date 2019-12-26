package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class LocalizacaoInstituicaoEnsinoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalizacaoInstituicaoEnsinoDTO.class);
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO1 = new LocalizacaoInstituicaoEnsinoDTO();
        localizacaoInstituicaoEnsinoDTO1.setId(1L);
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO2 = new LocalizacaoInstituicaoEnsinoDTO();
        assertThat(localizacaoInstituicaoEnsinoDTO1).isNotEqualTo(localizacaoInstituicaoEnsinoDTO2);
        localizacaoInstituicaoEnsinoDTO2.setId(localizacaoInstituicaoEnsinoDTO1.getId());
        assertThat(localizacaoInstituicaoEnsinoDTO1).isEqualTo(localizacaoInstituicaoEnsinoDTO2);
        localizacaoInstituicaoEnsinoDTO2.setId(2L);
        assertThat(localizacaoInstituicaoEnsinoDTO1).isNotEqualTo(localizacaoInstituicaoEnsinoDTO2);
        localizacaoInstituicaoEnsinoDTO1.setId(null);
        assertThat(localizacaoInstituicaoEnsinoDTO1).isNotEqualTo(localizacaoInstituicaoEnsinoDTO2);
    }
}
