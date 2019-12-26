package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class ContactoInstituicaoEnsinoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoInstituicaoEnsinoDTO.class);
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO1 = new ContactoInstituicaoEnsinoDTO();
        contactoInstituicaoEnsinoDTO1.setId(1L);
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO2 = new ContactoInstituicaoEnsinoDTO();
        assertThat(contactoInstituicaoEnsinoDTO1).isNotEqualTo(contactoInstituicaoEnsinoDTO2);
        contactoInstituicaoEnsinoDTO2.setId(contactoInstituicaoEnsinoDTO1.getId());
        assertThat(contactoInstituicaoEnsinoDTO1).isEqualTo(contactoInstituicaoEnsinoDTO2);
        contactoInstituicaoEnsinoDTO2.setId(2L);
        assertThat(contactoInstituicaoEnsinoDTO1).isNotEqualTo(contactoInstituicaoEnsinoDTO2);
        contactoInstituicaoEnsinoDTO1.setId(null);
        assertThat(contactoInstituicaoEnsinoDTO1).isNotEqualTo(contactoInstituicaoEnsinoDTO2);
    }
}
