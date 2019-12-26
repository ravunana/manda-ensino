package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.AlunoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aluno} and its DTO {@link AlunoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class, UserMapper.class, EncarregadoEducacaoMapper.class})
public interface AlunoMapper extends EntityMapper<AlunoDTO, Aluno> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "encarregadoEducacao.id", target = "encarregadoEducacaoId")
    AlunoDTO toDto(Aluno aluno);

    @Mapping(source = "pessoaId", target = "pessoa")
    @Mapping(target = "matriculas", ignore = true)
    @Mapping(target = "removeMatricula", ignore = true)
    @Mapping(target = "contratoes", ignore = true)
    @Mapping(target = "removeContrato", ignore = true)
    @Mapping(target = "situacaoAcademicas", ignore = true)
    @Mapping(target = "removeSituacaoAcademica", ignore = true)
    @Mapping(target = "requerimentos", ignore = true)
    @Mapping(target = "removeRequerimento", ignore = true)
    @Mapping(target = "pagamentos", ignore = true)
    @Mapping(target = "removePagamento", ignore = true)
    @Mapping(target = "depositos", ignore = true)
    @Mapping(target = "removeDeposito", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "encarregadoEducacaoId", target = "encarregadoEducacao")
    Aluno toEntity(AlunoDTO alunoDTO);

    default Aluno fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aluno aluno = new Aluno();
        aluno.setId(id);
        return aluno;
    }
}
