package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.DocumentoMatriculaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentoMatricula} and its DTO {@link DocumentoMatriculaDTO}.
 */
@Mapper(componentModel = "spring", uses = {MatriculaMapper.class})
public interface DocumentoMatriculaMapper extends EntityMapper<DocumentoMatriculaDTO, DocumentoMatricula> {

    @Mapping(source = "matricula.id", target = "matriculaId")
    @Mapping(source = "matricula.numero", target = "matriculaNumero")
    DocumentoMatriculaDTO toDto(DocumentoMatricula documentoMatricula);

    @Mapping(source = "matriculaId", target = "matricula")
    DocumentoMatricula toEntity(DocumentoMatriculaDTO documentoMatriculaDTO);

    default DocumentoMatricula fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentoMatricula documentoMatricula = new DocumentoMatricula();
        documentoMatricula.setId(id);
        return documentoMatricula;
    }
}
