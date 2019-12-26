package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DossificacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dossificacao.class);
        Dossificacao dossificacao1 = new Dossificacao();
        dossificacao1.setId(1L);
        Dossificacao dossificacao2 = new Dossificacao();
        dossificacao2.setId(dossificacao1.getId());
        assertThat(dossificacao1).isEqualTo(dossificacao2);
        dossificacao2.setId(2L);
        assertThat(dossificacao1).isNotEqualTo(dossificacao2);
        dossificacao1.setId(null);
        assertThat(dossificacao1).isNotEqualTo(dossificacao2);
    }
}
