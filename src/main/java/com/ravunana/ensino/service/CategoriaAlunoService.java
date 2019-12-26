package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.CategoriaAluno;
import com.ravunana.ensino.repository.CategoriaAlunoRepository;
import com.ravunana.ensino.repository.search.CategoriaAlunoSearchRepository;
import com.ravunana.ensino.service.dto.CategoriaAlunoDTO;
import com.ravunana.ensino.service.mapper.CategoriaAlunoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CategoriaAluno}.
 */
@Service
@Transactional
public class CategoriaAlunoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaAlunoService.class);

    private final CategoriaAlunoRepository categoriaAlunoRepository;

    private final CategoriaAlunoMapper categoriaAlunoMapper;

    private final CategoriaAlunoSearchRepository categoriaAlunoSearchRepository;

    public CategoriaAlunoService(CategoriaAlunoRepository categoriaAlunoRepository, CategoriaAlunoMapper categoriaAlunoMapper, CategoriaAlunoSearchRepository categoriaAlunoSearchRepository) {
        this.categoriaAlunoRepository = categoriaAlunoRepository;
        this.categoriaAlunoMapper = categoriaAlunoMapper;
        this.categoriaAlunoSearchRepository = categoriaAlunoSearchRepository;
    }

    /**
     * Save a categoriaAluno.
     *
     * @param categoriaAlunoDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriaAlunoDTO save(CategoriaAlunoDTO categoriaAlunoDTO) {
        log.debug("Request to save CategoriaAluno : {}", categoriaAlunoDTO);
        CategoriaAluno categoriaAluno = categoriaAlunoMapper.toEntity(categoriaAlunoDTO);
        categoriaAluno = categoriaAlunoRepository.save(categoriaAluno);
        CategoriaAlunoDTO result = categoriaAlunoMapper.toDto(categoriaAluno);
        categoriaAlunoSearchRepository.save(categoriaAluno);
        return result;
    }

    /**
     * Get all the categoriaAlunos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaAlunoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaAlunos");
        return categoriaAlunoRepository.findAll(pageable)
            .map(categoriaAlunoMapper::toDto);
    }


    /**
     * Get one categoriaAluno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaAlunoDTO> findOne(Long id) {
        log.debug("Request to get CategoriaAluno : {}", id);
        return categoriaAlunoRepository.findById(id)
            .map(categoriaAlunoMapper::toDto);
    }

    /**
     * Delete the categoriaAluno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaAluno : {}", id);
        categoriaAlunoRepository.deleteById(id);
        categoriaAlunoSearchRepository.deleteById(id);
    }

    /**
     * Search for the categoriaAluno corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaAlunoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CategoriaAlunos for query {}", query);
        return categoriaAlunoSearchRepository.search(queryStringQuery(query), pageable)
            .map(categoriaAlunoMapper::toDto);
    }
}
