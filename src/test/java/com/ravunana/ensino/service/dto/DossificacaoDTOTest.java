package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DossificacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DossificacaoDTO.class);
        DossificacaoDTO dossificacaoDTO1 = new DossificacaoDTO();
        dossificacaoDTO1.setId(1L);
        DossificacaoDTO dossificacaoDTO2 = new DossificacaoDTO();
        assertThat(dossificacaoDTO1).isNotEqualTo(dossificacaoDTO2);
        dossificacaoDTO2.setId(dossificacaoDTO1.getId());
        assertThat(dossificacaoDTO1).isEqualTo(dossificacaoDTO2);
        dossificacaoDTO2.setId(2L);
        assertThat(dossificacaoDTO1).isNotEqualTo(dossificacaoDTO2);
        dossificacaoDTO1.setId(null);
        assertThat(dossificacaoDTO1).isNotEqualTo(dossificacaoDTO2);
    }
}
