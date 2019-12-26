package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Requerimento;
import com.ravunana.ensino.repository.RequerimentoRepository;
import com.ravunana.ensino.repository.search.RequerimentoSearchRepository;
import com.ravunana.ensino.service.dto.RequerimentoDTO;
import com.ravunana.ensino.service.mapper.RequerimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Requerimento}.
 */
@Service
@Transactional
public class RequerimentoService {

    private final Logger log = LoggerFactory.getLogger(RequerimentoService.class);

    private final RequerimentoRepository requerimentoRepository;

    private final RequerimentoMapper requerimentoMapper;

    private final RequerimentoSearchRepository requerimentoSearchRepository;

    public RequerimentoService(RequerimentoRepository requerimentoRepository, RequerimentoMapper requerimentoMapper, RequerimentoSearchRepository requerimentoSearchRepository) {
        this.requerimentoRepository = requerimentoRepository;
        this.requerimentoMapper = requerimentoMapper;
        this.requerimentoSearchRepository = requerimentoSearchRepository;
    }

    /**
     * Save a requerimento.
     *
     * @param requerimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public RequerimentoDTO save(RequerimentoDTO requerimentoDTO) {
        log.debug("Request to save Requerimento : {}", requerimentoDTO);
        Requerimento requerimento = requerimentoMapper.toEntity(requerimentoDTO);
        requerimento = requerimentoRepository.save(requerimento);
        RequerimentoDTO result = requerimentoMapper.toDto(requerimento);
        requerimentoSearchRepository.save(requerimento);
        return result;
    }

    /**
     * Get all the requerimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequerimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requerimentos");
        return requerimentoRepository.findAll(pageable)
            .map(requerimentoMapper::toDto);
    }


    /**
     * Get one requerimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequerimentoDTO> findOne(Long id) {
        log.debug("Request to get Requerimento : {}", id);
        return requerimentoRepository.findById(id)
            .map(requerimentoMapper::toDto);
    }

    /**
     * Delete the requerimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Requerimento : {}", id);
        requerimentoRepository.deleteById(id);
        requerimentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the requerimento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequerimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Requerimentos for query {}", query);
        return requerimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(requerimentoMapper::toDto);
    }
}
