package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.DetalheOcorrencia;
import com.ravunana.ensino.repository.DetalheOcorrenciaRepository;
import com.ravunana.ensino.repository.search.DetalheOcorrenciaSearchRepository;
import com.ravunana.ensino.service.dto.DetalheOcorrenciaDTO;
import com.ravunana.ensino.service.mapper.DetalheOcorrenciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DetalheOcorrencia}.
 */
@Service
@Transactional
public class DetalheOcorrenciaService {

    private final Logger log = LoggerFactory.getLogger(DetalheOcorrenciaService.class);

    private final DetalheOcorrenciaRepository detalheOcorrenciaRepository;

    private final DetalheOcorrenciaMapper detalheOcorrenciaMapper;

    private final DetalheOcorrenciaSearchRepository detalheOcorrenciaSearchRepository;

    public DetalheOcorrenciaService(DetalheOcorrenciaRepository detalheOcorrenciaRepository, DetalheOcorrenciaMapper detalheOcorrenciaMapper, DetalheOcorrenciaSearchRepository detalheOcorrenciaSearchRepository) {
        this.detalheOcorrenciaRepository = detalheOcorrenciaRepository;
        this.detalheOcorrenciaMapper = detalheOcorrenciaMapper;
        this.detalheOcorrenciaSearchRepository = detalheOcorrenciaSearchRepository;
    }

    /**
     * Save a detalheOcorrencia.
     *
     * @param detalheOcorrenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalheOcorrenciaDTO save(DetalheOcorrenciaDTO detalheOcorrenciaDTO) {
        log.debug("Request to save DetalheOcorrencia : {}", detalheOcorrenciaDTO);
        DetalheOcorrencia detalheOcorrencia = detalheOcorrenciaMapper.toEntity(detalheOcorrenciaDTO);
        detalheOcorrencia = detalheOcorrenciaRepository.save(detalheOcorrencia);
        DetalheOcorrenciaDTO result = detalheOcorrenciaMapper.toDto(detalheOcorrencia);
        detalheOcorrenciaSearchRepository.save(detalheOcorrencia);
        return result;
    }

    /**
     * Get all the detalheOcorrencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalheOcorrenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalheOcorrencias");
        return detalheOcorrenciaRepository.findAll(pageable)
            .map(detalheOcorrenciaMapper::toDto);
    }


    /**
     * Get one detalheOcorrencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalheOcorrenciaDTO> findOne(Long id) {
        log.debug("Request to get DetalheOcorrencia : {}", id);
        return detalheOcorrenciaRepository.findById(id)
            .map(detalheOcorrenciaMapper::toDto);
    }

    /**
     * Delete the detalheOcorrencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalheOcorrencia : {}", id);
        detalheOcorrenciaRepository.deleteById(id);
        detalheOcorrenciaSearchRepository.deleteById(id);
    }

    /**
     * Search for the detalheOcorrencia corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalheOcorrenciaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetalheOcorrencias for query {}", query);
        return detalheOcorrenciaSearchRepository.search(queryStringQuery(query), pageable)
            .map(detalheOcorrenciaMapper::toDto);
    }
}
