package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.CategoriaRequerimento;
import com.ravunana.ensino.repository.CategoriaRequerimentoRepository;
import com.ravunana.ensino.repository.search.CategoriaRequerimentoSearchRepository;
import com.ravunana.ensino.service.dto.CategoriaRequerimentoDTO;
import com.ravunana.ensino.service.mapper.CategoriaRequerimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CategoriaRequerimento}.
 */
@Service
@Transactional
public class CategoriaRequerimentoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaRequerimentoService.class);

    private final CategoriaRequerimentoRepository categoriaRequerimentoRepository;

    private final CategoriaRequerimentoMapper categoriaRequerimentoMapper;

    private final CategoriaRequerimentoSearchRepository categoriaRequerimentoSearchRepository;

    public CategoriaRequerimentoService(CategoriaRequerimentoRepository categoriaRequerimentoRepository, CategoriaRequerimentoMapper categoriaRequerimentoMapper, CategoriaRequerimentoSearchRepository categoriaRequerimentoSearchRepository) {
        this.categoriaRequerimentoRepository = categoriaRequerimentoRepository;
        this.categoriaRequerimentoMapper = categoriaRequerimentoMapper;
        this.categoriaRequerimentoSearchRepository = categoriaRequerimentoSearchRepository;
    }

    /**
     * Save a categoriaRequerimento.
     *
     * @param categoriaRequerimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriaRequerimentoDTO save(CategoriaRequerimentoDTO categoriaRequerimentoDTO) {
        log.debug("Request to save CategoriaRequerimento : {}", categoriaRequerimentoDTO);
        CategoriaRequerimento categoriaRequerimento = categoriaRequerimentoMapper.toEntity(categoriaRequerimentoDTO);
        categoriaRequerimento = categoriaRequerimentoRepository.save(categoriaRequerimento);
        CategoriaRequerimentoDTO result = categoriaRequerimentoMapper.toDto(categoriaRequerimento);
        categoriaRequerimentoSearchRepository.save(categoriaRequerimento);
        return result;
    }

    /**
     * Get all the categoriaRequerimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaRequerimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaRequerimentos");
        return categoriaRequerimentoRepository.findAll(pageable)
            .map(categoriaRequerimentoMapper::toDto);
    }


    /**
     * Get one categoriaRequerimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaRequerimentoDTO> findOne(Long id) {
        log.debug("Request to get CategoriaRequerimento : {}", id);
        return categoriaRequerimentoRepository.findById(id)
            .map(categoriaRequerimentoMapper::toDto);
    }

    /**
     * Delete the categoriaRequerimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaRequerimento : {}", id);
        categoriaRequerimentoRepository.deleteById(id);
        categoriaRequerimentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the categoriaRequerimento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaRequerimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CategoriaRequerimentos for query {}", query);
        return categoriaRequerimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(categoriaRequerimentoMapper::toDto);
    }
}
