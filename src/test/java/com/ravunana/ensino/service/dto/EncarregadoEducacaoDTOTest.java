package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class EncarregadoEducacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncarregadoEducacaoDTO.class);
        EncarregadoEducacaoDTO encarregadoEducacaoDTO1 = new EncarregadoEducacaoDTO();
        encarregadoEducacaoDTO1.setId(1L);
        EncarregadoEducacaoDTO encarregadoEducacaoDTO2 = new EncarregadoEducacaoDTO();
        assertThat(encarregadoEducacaoDTO1).isNotEqualTo(encarregadoEducacaoDTO2);
        encarregadoEducacaoDTO2.setId(encarregadoEducacaoDTO1.getId());
        assertThat(encarregadoEducacaoDTO1).isEqualTo(encarregadoEducacaoDTO2);
        encarregadoEducacaoDTO2.setId(2L);
        assertThat(encarregadoEducacaoDTO1).isNotEqualTo(encarregadoEducacaoDTO2);
        encarregadoEducacaoDTO1.setId(null);
        assertThat(encarregadoEducacaoDTO1).isNotEqualTo(encarregadoEducacaoDTO2);
    }
}
