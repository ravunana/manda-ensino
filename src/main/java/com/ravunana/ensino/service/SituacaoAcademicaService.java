package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.SituacaoAcademica;
import com.ravunana.ensino.repository.SituacaoAcademicaRepository;
import com.ravunana.ensino.repository.search.SituacaoAcademicaSearchRepository;
import com.ravunana.ensino.service.dto.SituacaoAcademicaDTO;
import com.ravunana.ensino.service.mapper.SituacaoAcademicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SituacaoAcademica}.
 */
@Service
@Transactional
public class SituacaoAcademicaService {

    private final Logger log = LoggerFactory.getLogger(SituacaoAcademicaService.class);

    private final SituacaoAcademicaRepository situacaoAcademicaRepository;

    private final SituacaoAcademicaMapper situacaoAcademicaMapper;

    private final SituacaoAcademicaSearchRepository situacaoAcademicaSearchRepository;

    public SituacaoAcademicaService(SituacaoAcademicaRepository situacaoAcademicaRepository, SituacaoAcademicaMapper situacaoAcademicaMapper, SituacaoAcademicaSearchRepository situacaoAcademicaSearchRepository) {
        this.situacaoAcademicaRepository = situacaoAcademicaRepository;
        this.situacaoAcademicaMapper = situacaoAcademicaMapper;
        this.situacaoAcademicaSearchRepository = situacaoAcademicaSearchRepository;
    }

    /**
     * Save a situacaoAcademica.
     *
     * @param situacaoAcademicaDTO the entity to save.
     * @return the persisted entity.
     */
    public SituacaoAcademicaDTO save(SituacaoAcademicaDTO situacaoAcademicaDTO) {
        log.debug("Request to save SituacaoAcademica : {}", situacaoAcademicaDTO);
        SituacaoAcademica situacaoAcademica = situacaoAcademicaMapper.toEntity(situacaoAcademicaDTO);
        situacaoAcademica = situacaoAcademicaRepository.save(situacaoAcademica);
        SituacaoAcademicaDTO result = situacaoAcademicaMapper.toDto(situacaoAcademica);
        situacaoAcademicaSearchRepository.save(situacaoAcademica);
        return result;
    }

    /**
     * Get all the situacaoAcademicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SituacaoAcademicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SituacaoAcademicas");
        return situacaoAcademicaRepository.findAll(pageable)
            .map(situacaoAcademicaMapper::toDto);
    }


    /**
     * Get one situacaoAcademica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SituacaoAcademicaDTO> findOne(Long id) {
        log.debug("Request to get SituacaoAcademica : {}", id);
        return situacaoAcademicaRepository.findById(id)
            .map(situacaoAcademicaMapper::toDto);
    }

    /**
     * Delete the situacaoAcademica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SituacaoAcademica : {}", id);
        situacaoAcademicaRepository.deleteById(id);
        situacaoAcademicaSearchRepository.deleteById(id);
    }

    /**
     * Search for the situacaoAcademica corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SituacaoAcademicaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SituacaoAcademicas for query {}", query);
        return situacaoAcademicaSearchRepository.search(queryStringQuery(query), pageable)
            .map(situacaoAcademicaMapper::toDto);
    }
}
