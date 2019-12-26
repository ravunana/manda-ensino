package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.DocumentoMatricula;
import com.ravunana.ensino.repository.DocumentoMatriculaRepository;
import com.ravunana.ensino.repository.search.DocumentoMatriculaSearchRepository;
import com.ravunana.ensino.service.dto.DocumentoMatriculaDTO;
import com.ravunana.ensino.service.mapper.DocumentoMatriculaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DocumentoMatricula}.
 */
@Service
@Transactional
public class DocumentoMatriculaService {

    private final Logger log = LoggerFactory.getLogger(DocumentoMatriculaService.class);

    private final DocumentoMatriculaRepository documentoMatriculaRepository;

    private final DocumentoMatriculaMapper documentoMatriculaMapper;

    private final DocumentoMatriculaSearchRepository documentoMatriculaSearchRepository;

    public DocumentoMatriculaService(DocumentoMatriculaRepository documentoMatriculaRepository, DocumentoMatriculaMapper documentoMatriculaMapper, DocumentoMatriculaSearchRepository documentoMatriculaSearchRepository) {
        this.documentoMatriculaRepository = documentoMatriculaRepository;
        this.documentoMatriculaMapper = documentoMatriculaMapper;
        this.documentoMatriculaSearchRepository = documentoMatriculaSearchRepository;
    }

    /**
     * Save a documentoMatricula.
     *
     * @param documentoMatriculaDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentoMatriculaDTO save(DocumentoMatriculaDTO documentoMatriculaDTO) {
        log.debug("Request to save DocumentoMatricula : {}", documentoMatriculaDTO);
        DocumentoMatricula documentoMatricula = documentoMatriculaMapper.toEntity(documentoMatriculaDTO);
        documentoMatricula = documentoMatriculaRepository.save(documentoMatricula);
        DocumentoMatriculaDTO result = documentoMatriculaMapper.toDto(documentoMatricula);
        documentoMatriculaSearchRepository.save(documentoMatricula);
        return result;
    }

    /**
     * Get all the documentoMatriculas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoMatriculaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentoMatriculas");
        return documentoMatriculaRepository.findAll(pageable)
            .map(documentoMatriculaMapper::toDto);
    }


    /**
     * Get one documentoMatricula by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentoMatriculaDTO> findOne(Long id) {
        log.debug("Request to get DocumentoMatricula : {}", id);
        return documentoMatriculaRepository.findById(id)
            .map(documentoMatriculaMapper::toDto);
    }

    /**
     * Delete the documentoMatricula by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentoMatricula : {}", id);
        documentoMatriculaRepository.deleteById(id);
        documentoMatriculaSearchRepository.deleteById(id);
    }

    /**
     * Search for the documentoMatricula corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoMatriculaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentoMatriculas for query {}", query);
        return documentoMatriculaSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentoMatriculaMapper::toDto);
    }
}
