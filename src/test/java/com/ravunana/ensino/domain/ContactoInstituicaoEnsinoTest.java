package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class ContactoInstituicaoEnsinoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoInstituicaoEnsino.class);
        ContactoInstituicaoEnsino contactoInstituicaoEnsino1 = new ContactoInstituicaoEnsino();
        contactoInstituicaoEnsino1.setId(1L);
        ContactoInstituicaoEnsino contactoInstituicaoEnsino2 = new ContactoInstituicaoEnsino();
        contactoInstituicaoEnsino2.setId(contactoInstituicaoEnsino1.getId());
        assertThat(contactoInstituicaoEnsino1).isEqualTo(contactoInstituicaoEnsino2);
        contactoInstituicaoEnsino2.setId(2L);
        assertThat(contactoInstituicaoEnsino1).isNotEqualTo(contactoInstituicaoEnsino2);
        contactoInstituicaoEnsino1.setId(null);
        assertThat(contactoInstituicaoEnsino1).isNotEqualTo(contactoInstituicaoEnsino2);
    }
}
