package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.MoradaPessoa;
import com.ravunana.ensino.repository.MoradaPessoaRepository;
import com.ravunana.ensino.repository.search.MoradaPessoaSearchRepository;
import com.ravunana.ensino.service.dto.MoradaPessoaDTO;
import com.ravunana.ensino.service.mapper.MoradaPessoaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MoradaPessoa}.
 */
@Service
@Transactional
public class MoradaPessoaService {

    private final Logger log = LoggerFactory.getLogger(MoradaPessoaService.class);

    private final MoradaPessoaRepository moradaPessoaRepository;

    private final MoradaPessoaMapper moradaPessoaMapper;

    private final MoradaPessoaSearchRepository moradaPessoaSearchRepository;

    public MoradaPessoaService(MoradaPessoaRepository moradaPessoaRepository, MoradaPessoaMapper moradaPessoaMapper, MoradaPessoaSearchRepository moradaPessoaSearchRepository) {
        this.moradaPessoaRepository = moradaPessoaRepository;
        this.moradaPessoaMapper = moradaPessoaMapper;
        this.moradaPessoaSearchRepository = moradaPessoaSearchRepository;
    }

    /**
     * Save a moradaPessoa.
     *
     * @param moradaPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public MoradaPessoaDTO save(MoradaPessoaDTO moradaPessoaDTO) {
        log.debug("Request to save MoradaPessoa : {}", moradaPessoaDTO);
        MoradaPessoa moradaPessoa = moradaPessoaMapper.toEntity(moradaPessoaDTO);
        moradaPessoa = moradaPessoaRepository.save(moradaPessoa);
        MoradaPessoaDTO result = moradaPessoaMapper.toDto(moradaPessoa);
        moradaPessoaSearchRepository.save(moradaPessoa);
        return result;
    }

    /**
     * Get all the moradaPessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoradaPessoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MoradaPessoas");
        return moradaPessoaRepository.findAll(pageable)
            .map(moradaPessoaMapper::toDto);
    }


    /**
     * Get one moradaPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoradaPessoaDTO> findOne(Long id) {
        log.debug("Request to get MoradaPessoa : {}", id);
        return moradaPessoaRepository.findById(id)
            .map(moradaPessoaMapper::toDto);
    }

    /**
     * Delete the moradaPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MoradaPessoa : {}", id);
        moradaPessoaRepository.deleteById(id);
        moradaPessoaSearchRepository.deleteById(id);
    }

    /**
     * Search for the moradaPessoa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoradaPessoaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MoradaPessoas for query {}", query);
        return moradaPessoaSearchRepository.search(queryStringQuery(query), pageable)
            .map(moradaPessoaMapper::toDto);
    }
}
