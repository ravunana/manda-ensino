package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.CategoriaValiacao;
import com.ravunana.ensino.repository.CategoriaValiacaoRepository;
import com.ravunana.ensino.repository.search.CategoriaValiacaoSearchRepository;
import com.ravunana.ensino.service.dto.CategoriaValiacaoDTO;
import com.ravunana.ensino.service.mapper.CategoriaValiacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CategoriaValiacao}.
 */
@Service
@Transactional
public class CategoriaValiacaoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaValiacaoService.class);

    private final CategoriaValiacaoRepository categoriaValiacaoRepository;

    private final CategoriaValiacaoMapper categoriaValiacaoMapper;

    private final CategoriaValiacaoSearchRepository categoriaValiacaoSearchRepository;

    public CategoriaValiacaoService(CategoriaValiacaoRepository categoriaValiacaoRepository, CategoriaValiacaoMapper categoriaValiacaoMapper, CategoriaValiacaoSearchRepository categoriaValiacaoSearchRepository) {
        this.categoriaValiacaoRepository = categoriaValiacaoRepository;
        this.categoriaValiacaoMapper = categoriaValiacaoMapper;
        this.categoriaValiacaoSearchRepository = categoriaValiacaoSearchRepository;
    }

    /**
     * Save a categoriaValiacao.
     *
     * @param categoriaValiacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriaValiacaoDTO save(CategoriaValiacaoDTO categoriaValiacaoDTO) {
        log.debug("Request to save CategoriaValiacao : {}", categoriaValiacaoDTO);
        CategoriaValiacao categoriaValiacao = categoriaValiacaoMapper.toEntity(categoriaValiacaoDTO);
        categoriaValiacao = categoriaValiacaoRepository.save(categoriaValiacao);
        CategoriaValiacaoDTO result = categoriaValiacaoMapper.toDto(categoriaValiacao);
        categoriaValiacaoSearchRepository.save(categoriaValiacao);
        return result;
    }

    /**
     * Get all the categoriaValiacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaValiacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaValiacaos");
        return categoriaValiacaoRepository.findAll(pageable)
            .map(categoriaValiacaoMapper::toDto);
    }


    /**
     * Get one categoriaValiacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaValiacaoDTO> findOne(Long id) {
        log.debug("Request to get CategoriaValiacao : {}", id);
        return categoriaValiacaoRepository.findById(id)
            .map(categoriaValiacaoMapper::toDto);
    }

    /**
     * Delete the categoriaValiacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaValiacao : {}", id);
        categoriaValiacaoRepository.deleteById(id);
        categoriaValiacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the categoriaValiacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaValiacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CategoriaValiacaos for query {}", query);
        return categoriaValiacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(categoriaValiacaoMapper::toDto);
    }
}
