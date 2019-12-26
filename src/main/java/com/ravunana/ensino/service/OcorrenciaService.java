package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Ocorrencia;
import com.ravunana.ensino.repository.OcorrenciaRepository;
import com.ravunana.ensino.repository.search.OcorrenciaSearchRepository;
import com.ravunana.ensino.service.dto.OcorrenciaDTO;
import com.ravunana.ensino.service.mapper.OcorrenciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Ocorrencia}.
 */
@Service
@Transactional
public class OcorrenciaService {

    private final Logger log = LoggerFactory.getLogger(OcorrenciaService.class);

    private final OcorrenciaRepository ocorrenciaRepository;

    private final OcorrenciaMapper ocorrenciaMapper;

    private final OcorrenciaSearchRepository ocorrenciaSearchRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, OcorrenciaMapper ocorrenciaMapper, OcorrenciaSearchRepository ocorrenciaSearchRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ocorrenciaMapper = ocorrenciaMapper;
        this.ocorrenciaSearchRepository = ocorrenciaSearchRepository;
    }

    /**
     * Save a ocorrencia.
     *
     * @param ocorrenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public OcorrenciaDTO save(OcorrenciaDTO ocorrenciaDTO) {
        log.debug("Request to save Ocorrencia : {}", ocorrenciaDTO);
        Ocorrencia ocorrencia = ocorrenciaMapper.toEntity(ocorrenciaDTO);
        ocorrencia = ocorrenciaRepository.save(ocorrencia);
        OcorrenciaDTO result = ocorrenciaMapper.toDto(ocorrencia);
        ocorrenciaSearchRepository.save(ocorrencia);
        return result;
    }

    /**
     * Get all the ocorrencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OcorrenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ocorrencias");
        return ocorrenciaRepository.findAll(pageable)
            .map(ocorrenciaMapper::toDto);
    }


    /**
     * Get one ocorrencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OcorrenciaDTO> findOne(Long id) {
        log.debug("Request to get Ocorrencia : {}", id);
        return ocorrenciaRepository.findById(id)
            .map(ocorrenciaMapper::toDto);
    }

    /**
     * Delete the ocorrencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ocorrencia : {}", id);
        ocorrenciaRepository.deleteById(id);
        ocorrenciaSearchRepository.deleteById(id);
    }

    /**
     * Search for the ocorrencia corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OcorrenciaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ocorrencias for query {}", query);
        return ocorrenciaSearchRepository.search(queryStringQuery(query), pageable)
            .map(ocorrenciaMapper::toDto);
    }
}
