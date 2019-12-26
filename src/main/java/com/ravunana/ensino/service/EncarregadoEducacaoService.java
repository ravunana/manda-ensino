package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.EncarregadoEducacao;
import com.ravunana.ensino.repository.EncarregadoEducacaoRepository;
import com.ravunana.ensino.repository.search.EncarregadoEducacaoSearchRepository;
import com.ravunana.ensino.service.dto.EncarregadoEducacaoDTO;
import com.ravunana.ensino.service.mapper.EncarregadoEducacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EncarregadoEducacao}.
 */
@Service
@Transactional
public class EncarregadoEducacaoService {

    private final Logger log = LoggerFactory.getLogger(EncarregadoEducacaoService.class);

    private final EncarregadoEducacaoRepository encarregadoEducacaoRepository;

    private final EncarregadoEducacaoMapper encarregadoEducacaoMapper;

    private final EncarregadoEducacaoSearchRepository encarregadoEducacaoSearchRepository;

    public EncarregadoEducacaoService(EncarregadoEducacaoRepository encarregadoEducacaoRepository, EncarregadoEducacaoMapper encarregadoEducacaoMapper, EncarregadoEducacaoSearchRepository encarregadoEducacaoSearchRepository) {
        this.encarregadoEducacaoRepository = encarregadoEducacaoRepository;
        this.encarregadoEducacaoMapper = encarregadoEducacaoMapper;
        this.encarregadoEducacaoSearchRepository = encarregadoEducacaoSearchRepository;
    }

    /**
     * Save a encarregadoEducacao.
     *
     * @param encarregadoEducacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public EncarregadoEducacaoDTO save(EncarregadoEducacaoDTO encarregadoEducacaoDTO) {
        log.debug("Request to save EncarregadoEducacao : {}", encarregadoEducacaoDTO);
        EncarregadoEducacao encarregadoEducacao = encarregadoEducacaoMapper.toEntity(encarregadoEducacaoDTO);
        encarregadoEducacao = encarregadoEducacaoRepository.save(encarregadoEducacao);
        EncarregadoEducacaoDTO result = encarregadoEducacaoMapper.toDto(encarregadoEducacao);
        encarregadoEducacaoSearchRepository.save(encarregadoEducacao);
        return result;
    }

    /**
     * Get all the encarregadoEducacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EncarregadoEducacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EncarregadoEducacaos");
        return encarregadoEducacaoRepository.findAll(pageable)
            .map(encarregadoEducacaoMapper::toDto);
    }


    /**
     * Get one encarregadoEducacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EncarregadoEducacaoDTO> findOne(Long id) {
        log.debug("Request to get EncarregadoEducacao : {}", id);
        return encarregadoEducacaoRepository.findById(id)
            .map(encarregadoEducacaoMapper::toDto);
    }

    /**
     * Delete the encarregadoEducacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EncarregadoEducacao : {}", id);
        encarregadoEducacaoRepository.deleteById(id);
        encarregadoEducacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the encarregadoEducacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EncarregadoEducacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EncarregadoEducacaos for query {}", query);
        return encarregadoEducacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(encarregadoEducacaoMapper::toDto);
    }
}
