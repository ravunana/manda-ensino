package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class MatrizCurricularDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatrizCurricularDTO.class);
        MatrizCurricularDTO matrizCurricularDTO1 = new MatrizCurricularDTO();
        matrizCurricularDTO1.setId(1L);
        MatrizCurricularDTO matrizCurricularDTO2 = new MatrizCurricularDTO();
        assertThat(matrizCurricularDTO1).isNotEqualTo(matrizCurricularDTO2);
        matrizCurricularDTO2.setId(matrizCurricularDTO1.getId());
        assertThat(matrizCurricularDTO1).isEqualTo(matrizCurricularDTO2);
        matrizCurricularDTO2.setId(2L);
        assertThat(matrizCurricularDTO1).isNotEqualTo(matrizCurricularDTO2);
        matrizCurricularDTO1.setId(null);
        assertThat(matrizCurricularDTO1).isNotEqualTo(matrizCurricularDTO2);
    }
}
