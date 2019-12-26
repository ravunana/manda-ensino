package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.DepositoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deposito} and its DTO {@link DepositoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, MeioLiquidacaoMapper.class, AlunoMapper.class, CoordenadaBancariaMapper.class})
public interface DepositoMapper extends EntityMapper<DepositoDTO, Deposito> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "meioLiquidacao.id", target = "meioLiquidacaoId")
    @Mapping(source = "meioLiquidacao.nome", target = "meioLiquidacaoNome")
    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.numeroProcesso", target = "alunoNumeroProcesso")
    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    DepositoDTO toDto(Deposito deposito);

    @Mapping(target = "detalhePagamentos", ignore = true)
    @Mapping(target = "removeDetalhePagamento", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "meioLiquidacaoId", target = "meioLiquidacao")
    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "contaId", target = "conta")
    Deposito toEntity(DepositoDTO depositoDTO);

    default Deposito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deposito deposito = new Deposito();
        deposito.setId(id);
        return deposito;
    }
}
