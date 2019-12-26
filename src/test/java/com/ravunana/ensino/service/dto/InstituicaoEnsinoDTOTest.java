package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class InstituicaoEnsinoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstituicaoEnsinoDTO.class);
        InstituicaoEnsinoDTO instituicaoEnsinoDTO1 = new InstituicaoEnsinoDTO();
        instituicaoEnsinoDTO1.setId(1L);
        InstituicaoEnsinoDTO instituicaoEnsinoDTO2 = new InstituicaoEnsinoDTO();
        assertThat(instituicaoEnsinoDTO1).isNotEqualTo(instituicaoEnsinoDTO2);
        instituicaoEnsinoDTO2.setId(instituicaoEnsinoDTO1.getId());
        assertThat(instituicaoEnsinoDTO1).isEqualTo(instituicaoEnsinoDTO2);
        instituicaoEnsinoDTO2.setId(2L);
        assertThat(instituicaoEnsinoDTO1).isNotEqualTo(instituicaoEnsinoDTO2);
        instituicaoEnsinoDTO1.setId(null);
        assertThat(instituicaoEnsinoDTO1).isNotEqualTo(instituicaoEnsinoDTO2);
    }
}
