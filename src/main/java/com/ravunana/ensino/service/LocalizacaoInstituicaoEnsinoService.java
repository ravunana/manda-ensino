package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.LocalizacaoInstituicaoEnsino;
import com.ravunana.ensino.repository.LocalizacaoInstituicaoEnsinoRepository;
import com.ravunana.ensino.repository.search.LocalizacaoInstituicaoEnsinoSearchRepository;
import com.ravunana.ensino.service.dto.LocalizacaoInstituicaoEnsinoDTO;
import com.ravunana.ensino.service.mapper.LocalizacaoInstituicaoEnsinoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LocalizacaoInstituicaoEnsino}.
 */
@Service
@Transactional
public class LocalizacaoInstituicaoEnsinoService {

    private final Logger log = LoggerFactory.getLogger(LocalizacaoInstituicaoEnsinoService.class);

    private final LocalizacaoInstituicaoEnsinoRepository localizacaoInstituicaoEnsinoRepository;

    private final LocalizacaoInstituicaoEnsinoMapper localizacaoInstituicaoEnsinoMapper;

    private final LocalizacaoInstituicaoEnsinoSearchRepository localizacaoInstituicaoEnsinoSearchRepository;

    public LocalizacaoInstituicaoEnsinoService(LocalizacaoInstituicaoEnsinoRepository localizacaoInstituicaoEnsinoRepository, LocalizacaoInstituicaoEnsinoMapper localizacaoInstituicaoEnsinoMapper, LocalizacaoInstituicaoEnsinoSearchRepository localizacaoInstituicaoEnsinoSearchRepository) {
        this.localizacaoInstituicaoEnsinoRepository = localizacaoInstituicaoEnsinoRepository;
        this.localizacaoInstituicaoEnsinoMapper = localizacaoInstituicaoEnsinoMapper;
        this.localizacaoInstituicaoEnsinoSearchRepository = localizacaoInstituicaoEnsinoSearchRepository;
    }

    /**
     * Save a localizacaoInstituicaoEnsino.
     *
     * @param localizacaoInstituicaoEnsinoDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalizacaoInstituicaoEnsinoDTO save(LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO) {
        log.debug("Request to save LocalizacaoInstituicaoEnsino : {}", localizacaoInstituicaoEnsinoDTO);
        LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino = localizacaoInstituicaoEnsinoMapper.toEntity(localizacaoInstituicaoEnsinoDTO);
        localizacaoInstituicaoEnsino = localizacaoInstituicaoEnsinoRepository.save(localizacaoInstituicaoEnsino);
        LocalizacaoInstituicaoEnsinoDTO result = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);
        localizacaoInstituicaoEnsinoSearchRepository.save(localizacaoInstituicaoEnsino);
        return result;
    }

    /**
     * Get all the localizacaoInstituicaoEnsinos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalizacaoInstituicaoEnsinoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocalizacaoInstituicaoEnsinos");
        return localizacaoInstituicaoEnsinoRepository.findAll(pageable)
            .map(localizacaoInstituicaoEnsinoMapper::toDto);
    }


    /**
     * Get one localizacaoInstituicaoEnsino by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocalizacaoInstituicaoEnsinoDTO> findOne(Long id) {
        log.debug("Request to get LocalizacaoInstituicaoEnsino : {}", id);
        return localizacaoInstituicaoEnsinoRepository.findById(id)
            .map(localizacaoInstituicaoEnsinoMapper::toDto);
    }

    /**
     * Delete the localizacaoInstituicaoEnsino by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocalizacaoInstituicaoEnsino : {}", id);
        localizacaoInstituicaoEnsinoRepository.deleteById(id);
        localizacaoInstituicaoEnsinoSearchRepository.deleteById(id);
    }

    /**
     * Search for the localizacaoInstituicaoEnsino corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalizacaoInstituicaoEnsinoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LocalizacaoInstituicaoEnsinos for query {}", query);
        return localizacaoInstituicaoEnsinoSearchRepository.search(queryStringQuery(query), pageable)
            .map(localizacaoInstituicaoEnsinoMapper::toDto);
    }
}
