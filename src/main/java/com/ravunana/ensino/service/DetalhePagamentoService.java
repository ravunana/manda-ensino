package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.DetalhePagamento;
import com.ravunana.ensino.repository.DetalhePagamentoRepository;
import com.ravunana.ensino.repository.search.DetalhePagamentoSearchRepository;
import com.ravunana.ensino.service.dto.DetalhePagamentoDTO;
import com.ravunana.ensino.service.mapper.DetalhePagamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DetalhePagamento}.
 */
@Service
@Transactional
public class DetalhePagamentoService {

    private final Logger log = LoggerFactory.getLogger(DetalhePagamentoService.class);

    private final DetalhePagamentoRepository detalhePagamentoRepository;

    private final DetalhePagamentoMapper detalhePagamentoMapper;

    private final DetalhePagamentoSearchRepository detalhePagamentoSearchRepository;

    public DetalhePagamentoService(DetalhePagamentoRepository detalhePagamentoRepository, DetalhePagamentoMapper detalhePagamentoMapper, DetalhePagamentoSearchRepository detalhePagamentoSearchRepository) {
        this.detalhePagamentoRepository = detalhePagamentoRepository;
        this.detalhePagamentoMapper = detalhePagamentoMapper;
        this.detalhePagamentoSearchRepository = detalhePagamentoSearchRepository;
    }

    /**
     * Save a detalhePagamento.
     *
     * @param detalhePagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalhePagamentoDTO save(DetalhePagamentoDTO detalhePagamentoDTO) {
        log.debug("Request to save DetalhePagamento : {}", detalhePagamentoDTO);
        DetalhePagamento detalhePagamento = detalhePagamentoMapper.toEntity(detalhePagamentoDTO);
        detalhePagamento = detalhePagamentoRepository.save(detalhePagamento);
        DetalhePagamentoDTO result = detalhePagamentoMapper.toDto(detalhePagamento);
        detalhePagamentoSearchRepository.save(detalhePagamento);
        return result;
    }

    /**
     * Get all the detalhePagamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalhePagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalhePagamentos");
        return detalhePagamentoRepository.findAll(pageable)
            .map(detalhePagamentoMapper::toDto);
    }


    /**
     * Get one detalhePagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalhePagamentoDTO> findOne(Long id) {
        log.debug("Request to get DetalhePagamento : {}", id);
        return detalhePagamentoRepository.findById(id)
            .map(detalhePagamentoMapper::toDto);
    }

    /**
     * Delete the detalhePagamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalhePagamento : {}", id);
        detalhePagamentoRepository.deleteById(id);
        detalhePagamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the detalhePagamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalhePagamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetalhePagamentos for query {}", query);
        return detalhePagamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(detalhePagamentoMapper::toDto);
    }
}
