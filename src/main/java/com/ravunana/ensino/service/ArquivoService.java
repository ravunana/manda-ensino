package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Arquivo;
import com.ravunana.ensino.repository.ArquivoRepository;
import com.ravunana.ensino.repository.search.ArquivoSearchRepository;
import com.ravunana.ensino.service.dto.ArquivoDTO;
import com.ravunana.ensino.service.mapper.ArquivoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Arquivo}.
 */
@Service
@Transactional
public class ArquivoService {

    private final Logger log = LoggerFactory.getLogger(ArquivoService.class);

    private final ArquivoRepository arquivoRepository;

    private final ArquivoMapper arquivoMapper;

    private final ArquivoSearchRepository arquivoSearchRepository;

    public ArquivoService(ArquivoRepository arquivoRepository, ArquivoMapper arquivoMapper, ArquivoSearchRepository arquivoSearchRepository) {
        this.arquivoRepository = arquivoRepository;
        this.arquivoMapper = arquivoMapper;
        this.arquivoSearchRepository = arquivoSearchRepository;
    }

    /**
     * Save a arquivo.
     *
     * @param arquivoDTO the entity to save.
     * @return the persisted entity.
     */
    public ArquivoDTO save(ArquivoDTO arquivoDTO) {
        log.debug("Request to save Arquivo : {}", arquivoDTO);
        Arquivo arquivo = arquivoMapper.toEntity(arquivoDTO);
        arquivo = arquivoRepository.save(arquivo);
        ArquivoDTO result = arquivoMapper.toDto(arquivo);
        arquivoSearchRepository.save(arquivo);
        return result;
    }

    /**
     * Get all the arquivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArquivoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Arquivos");
        return arquivoRepository.findAll(pageable)
            .map(arquivoMapper::toDto);
    }


    /**
     * Get one arquivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArquivoDTO> findOne(Long id) {
        log.debug("Request to get Arquivo : {}", id);
        return arquivoRepository.findById(id)
            .map(arquivoMapper::toDto);
    }

    /**
     * Delete the arquivo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Arquivo : {}", id);
        arquivoRepository.deleteById(id);
        arquivoSearchRepository.deleteById(id);
    }

    /**
     * Search for the arquivo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArquivoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Arquivos for query {}", query);
        return arquivoSearchRepository.search(queryStringQuery(query), pageable)
            .map(arquivoMapper::toDto);
    }
}
