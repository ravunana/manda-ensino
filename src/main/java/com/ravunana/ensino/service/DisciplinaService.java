package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Disciplina;
import com.ravunana.ensino.repository.DisciplinaRepository;
import com.ravunana.ensino.repository.search.DisciplinaSearchRepository;
import com.ravunana.ensino.service.dto.DisciplinaDTO;
import com.ravunana.ensino.service.mapper.DisciplinaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Disciplina}.
 */
@Service
@Transactional
public class DisciplinaService {

    private final Logger log = LoggerFactory.getLogger(DisciplinaService.class);

    private final DisciplinaRepository disciplinaRepository;

    private final DisciplinaMapper disciplinaMapper;

    private final DisciplinaSearchRepository disciplinaSearchRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, DisciplinaMapper disciplinaMapper, DisciplinaSearchRepository disciplinaSearchRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaMapper = disciplinaMapper;
        this.disciplinaSearchRepository = disciplinaSearchRepository;
    }

    /**
     * Save a disciplina.
     *
     * @param disciplinaDTO the entity to save.
     * @return the persisted entity.
     */
    public DisciplinaDTO save(DisciplinaDTO disciplinaDTO) {
        log.debug("Request to save Disciplina : {}", disciplinaDTO);
        Disciplina disciplina = disciplinaMapper.toEntity(disciplinaDTO);
        disciplina = disciplinaRepository.save(disciplina);
        DisciplinaDTO result = disciplinaMapper.toDto(disciplina);
        disciplinaSearchRepository.save(disciplina);
        return result;
    }

    /**
     * Get all the disciplinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disciplinas");
        return disciplinaRepository.findAll(pageable)
            .map(disciplinaMapper::toDto);
    }


    /**
     * Get one disciplina by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DisciplinaDTO> findOne(Long id) {
        log.debug("Request to get Disciplina : {}", id);
        return disciplinaRepository.findById(id)
            .map(disciplinaMapper::toDto);
    }

    /**
     * Delete the disciplina by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Disciplina : {}", id);
        disciplinaRepository.deleteById(id);
        disciplinaSearchRepository.deleteById(id);
    }

    /**
     * Search for the disciplina corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DisciplinaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Disciplinas for query {}", query);
        return disciplinaSearchRepository.search(queryStringQuery(query), pageable)
            .map(disciplinaMapper::toDto);
    }
}
