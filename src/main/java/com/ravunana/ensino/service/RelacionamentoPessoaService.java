package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.RelacionamentoPessoa;
import com.ravunana.ensino.repository.RelacionamentoPessoaRepository;
import com.ravunana.ensino.repository.search.RelacionamentoPessoaSearchRepository;
import com.ravunana.ensino.service.dto.RelacionamentoPessoaDTO;
import com.ravunana.ensino.service.mapper.RelacionamentoPessoaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RelacionamentoPessoa}.
 */
@Service
@Transactional
public class RelacionamentoPessoaService {

    private final Logger log = LoggerFactory.getLogger(RelacionamentoPessoaService.class);

    private final RelacionamentoPessoaRepository relacionamentoPessoaRepository;

    private final RelacionamentoPessoaMapper relacionamentoPessoaMapper;

    private final RelacionamentoPessoaSearchRepository relacionamentoPessoaSearchRepository;

    public RelacionamentoPessoaService(RelacionamentoPessoaRepository relacionamentoPessoaRepository, RelacionamentoPessoaMapper relacionamentoPessoaMapper, RelacionamentoPessoaSearchRepository relacionamentoPessoaSearchRepository) {
        this.relacionamentoPessoaRepository = relacionamentoPessoaRepository;
        this.relacionamentoPessoaMapper = relacionamentoPessoaMapper;
        this.relacionamentoPessoaSearchRepository = relacionamentoPessoaSearchRepository;
    }

    /**
     * Save a relacionamentoPessoa.
     *
     * @param relacionamentoPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public RelacionamentoPessoaDTO save(RelacionamentoPessoaDTO relacionamentoPessoaDTO) {
        log.debug("Request to save RelacionamentoPessoa : {}", relacionamentoPessoaDTO);
        RelacionamentoPessoa relacionamentoPessoa = relacionamentoPessoaMapper.toEntity(relacionamentoPessoaDTO);
        relacionamentoPessoa = relacionamentoPessoaRepository.save(relacionamentoPessoa);
        RelacionamentoPessoaDTO result = relacionamentoPessoaMapper.toDto(relacionamentoPessoa);
        relacionamentoPessoaSearchRepository.save(relacionamentoPessoa);
        return result;
    }

    /**
     * Get all the relacionamentoPessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RelacionamentoPessoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelacionamentoPessoas");
        return relacionamentoPessoaRepository.findAll(pageable)
            .map(relacionamentoPessoaMapper::toDto);
    }


    /**
     * Get one relacionamentoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RelacionamentoPessoaDTO> findOne(Long id) {
        log.debug("Request to get RelacionamentoPessoa : {}", id);
        return relacionamentoPessoaRepository.findById(id)
            .map(relacionamentoPessoaMapper::toDto);
    }

    /**
     * Delete the relacionamentoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RelacionamentoPessoa : {}", id);
        relacionamentoPessoaRepository.deleteById(id);
        relacionamentoPessoaSearchRepository.deleteById(id);
    }

    /**
     * Search for the relacionamentoPessoa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RelacionamentoPessoaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RelacionamentoPessoas for query {}", query);
        return relacionamentoPessoaSearchRepository.search(queryStringQuery(query), pageable)
            .map(relacionamentoPessoaMapper::toDto);
    }
}
