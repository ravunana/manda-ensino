package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class UnidadeCurricularDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeCurricularDTO.class);
        UnidadeCurricularDTO unidadeCurricularDTO1 = new UnidadeCurricularDTO();
        unidadeCurricularDTO1.setId(1L);
        UnidadeCurricularDTO unidadeCurricularDTO2 = new UnidadeCurricularDTO();
        assertThat(unidadeCurricularDTO1).isNotEqualTo(unidadeCurricularDTO2);
        unidadeCurricularDTO2.setId(unidadeCurricularDTO1.getId());
        assertThat(unidadeCurricularDTO1).isEqualTo(unidadeCurricularDTO2);
        unidadeCurricularDTO2.setId(2L);
        assertThat(unidadeCurricularDTO1).isNotEqualTo(unidadeCurricularDTO2);
        unidadeCurricularDTO1.setId(null);
        assertThat(unidadeCurricularDTO1).isNotEqualTo(unidadeCurricularDTO2);
    }
}
