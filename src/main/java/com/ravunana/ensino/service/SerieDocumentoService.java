package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.SerieDocumento;
import com.ravunana.ensino.repository.SerieDocumentoRepository;
import com.ravunana.ensino.repository.search.SerieDocumentoSearchRepository;
import com.ravunana.ensino.service.dto.SerieDocumentoDTO;
import com.ravunana.ensino.service.mapper.SerieDocumentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SerieDocumento}.
 */
@Service
@Transactional
public class SerieDocumentoService {

    private final Logger log = LoggerFactory.getLogger(SerieDocumentoService.class);

    private final SerieDocumentoRepository serieDocumentoRepository;

    private final SerieDocumentoMapper serieDocumentoMapper;

    private final SerieDocumentoSearchRepository serieDocumentoSearchRepository;

    public SerieDocumentoService(SerieDocumentoRepository serieDocumentoRepository, SerieDocumentoMapper serieDocumentoMapper, SerieDocumentoSearchRepository serieDocumentoSearchRepository) {
        this.serieDocumentoRepository = serieDocumentoRepository;
        this.serieDocumentoMapper = serieDocumentoMapper;
        this.serieDocumentoSearchRepository = serieDocumentoSearchRepository;
    }

    /**
     * Save a serieDocumento.
     *
     * @param serieDocumentoDTO the entity to save.
     * @return the persisted entity.
     */
    public SerieDocumentoDTO save(SerieDocumentoDTO serieDocumentoDTO) {
        log.debug("Request to save SerieDocumento : {}", serieDocumentoDTO);
        SerieDocumento serieDocumento = serieDocumentoMapper.toEntity(serieDocumentoDTO);
        serieDocumento = serieDocumentoRepository.save(serieDocumento);
        SerieDocumentoDTO result = serieDocumentoMapper.toDto(serieDocumento);
        serieDocumentoSearchRepository.save(serieDocumento);
        return result;
    }

    /**
     * Get all the serieDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SerieDocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SerieDocumentos");
        return serieDocumentoRepository.findAll(pageable)
            .map(serieDocumentoMapper::toDto);
    }


    /**
     * Get one serieDocumento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SerieDocumentoDTO> findOne(Long id) {
        log.debug("Request to get SerieDocumento : {}", id);
        return serieDocumentoRepository.findById(id)
            .map(serieDocumentoMapper::toDto);
    }

    /**
     * Delete the serieDocumento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SerieDocumento : {}", id);
        serieDocumentoRepository.deleteById(id);
        serieDocumentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the serieDocumento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SerieDocumentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SerieDocumentos for query {}", query);
        return serieDocumentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(serieDocumentoMapper::toDto);
    }
}
