package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Dossificacao;
import com.ravunana.ensino.repository.DossificacaoRepository;
import com.ravunana.ensino.repository.search.DossificacaoSearchRepository;
import com.ravunana.ensino.service.dto.DossificacaoDTO;
import com.ravunana.ensino.service.mapper.DossificacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Dossificacao}.
 */
@Service
@Transactional
public class DossificacaoService {

    private final Logger log = LoggerFactory.getLogger(DossificacaoService.class);

    private final DossificacaoRepository dossificacaoRepository;

    private final DossificacaoMapper dossificacaoMapper;

    private final DossificacaoSearchRepository dossificacaoSearchRepository;

    public DossificacaoService(DossificacaoRepository dossificacaoRepository, DossificacaoMapper dossificacaoMapper, DossificacaoSearchRepository dossificacaoSearchRepository) {
        this.dossificacaoRepository = dossificacaoRepository;
        this.dossificacaoMapper = dossificacaoMapper;
        this.dossificacaoSearchRepository = dossificacaoSearchRepository;
    }

    /**
     * Save a dossificacao.
     *
     * @param dossificacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public DossificacaoDTO save(DossificacaoDTO dossificacaoDTO) {
        log.debug("Request to save Dossificacao : {}", dossificacaoDTO);
        Dossificacao dossificacao = dossificacaoMapper.toEntity(dossificacaoDTO);
        dossificacao = dossificacaoRepository.save(dossificacao);
        DossificacaoDTO result = dossificacaoMapper.toDto(dossificacao);
        dossificacaoSearchRepository.save(dossificacao);
        return result;
    }

    /**
     * Get all the dossificacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DossificacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dossificacaos");
        return dossificacaoRepository.findAll(pageable)
            .map(dossificacaoMapper::toDto);
    }

    /**
     * Get all the dossificacaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DossificacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return dossificacaoRepository.findAllWithEagerRelationships(pageable).map(dossificacaoMapper::toDto);
    }
    

    /**
     * Get one dossificacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DossificacaoDTO> findOne(Long id) {
        log.debug("Request to get Dossificacao : {}", id);
        return dossificacaoRepository.findOneWithEagerRelationships(id)
            .map(dossificacaoMapper::toDto);
    }

    /**
     * Delete the dossificacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dossificacao : {}", id);
        dossificacaoRepository.deleteById(id);
        dossificacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the dossificacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DossificacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dossificacaos for query {}", query);
        return dossificacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(dossificacaoMapper::toDto);
    }
}
