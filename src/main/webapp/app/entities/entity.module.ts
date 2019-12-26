import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'serie-documento',
        loadChildren: () => import('./serie-documento/serie-documento.module').then(m => m.EnsinoSerieDocumentoModule)
      },
      {
        path: 'software',
        loadChildren: () => import('./software/software.module').then(m => m.EnsinoSoftwareModule)
      },
      {
        path: 'licenca-software',
        loadChildren: () => import('./licenca-software/licenca-software.module').then(m => m.EnsinoLicencaSoftwareModule)
      },
      {
        path: 'lookup',
        loadChildren: () => import('./lookup/lookup.module').then(m => m.EnsinoLookupModule)
      },
      {
        path: 'lookup-item',
        loadChildren: () => import('./lookup-item/lookup-item.module').then(m => m.EnsinoLookupItemModule)
      },
      {
        path: 'morada-pessoa',
        loadChildren: () => import('./morada-pessoa/morada-pessoa.module').then(m => m.EnsinoMoradaPessoaModule)
      },
      {
        path: 'contacto-pessoa',
        loadChildren: () => import('./contacto-pessoa/contacto-pessoa.module').then(m => m.EnsinoContactoPessoaModule)
      },
      {
        path: 'pessoa',
        loadChildren: () => import('./pessoa/pessoa.module').then(m => m.EnsinoPessoaModule)
      },
      {
        path: 'documentacao-pessoa',
        loadChildren: () => import('./documentacao-pessoa/documentacao-pessoa.module').then(m => m.EnsinoDocumentacaoPessoaModule)
      },
      {
        path: 'relacionamento-pessoa',
        loadChildren: () => import('./relacionamento-pessoa/relacionamento-pessoa.module').then(m => m.EnsinoRelacionamentoPessoaModule)
      },
      {
        path: 'instituicao-ensino',
        loadChildren: () => import('./instituicao-ensino/instituicao-ensino.module').then(m => m.EnsinoInstituicaoEnsinoModule)
      },
      {
        path: 'assinatura-digital',
        loadChildren: () => import('./assinatura-digital/assinatura-digital.module').then(m => m.EnsinoAssinaturaDigitalModule)
      },
      {
        path: 'localizacao-instituicao-ensino',
        loadChildren: () =>
          import('./localizacao-instituicao-ensino/localizacao-instituicao-ensino.module').then(
            m => m.EnsinoLocalizacaoInstituicaoEnsinoModule
          )
      },
      {
        path: 'contacto-instituicao-ensino',
        loadChildren: () =>
          import('./contacto-instituicao-ensino/contacto-instituicao-ensino.module').then(m => m.EnsinoContactoInstituicaoEnsinoModule)
      },
      {
        path: 'coordenada-bancaria',
        loadChildren: () => import('./coordenada-bancaria/coordenada-bancaria.module').then(m => m.EnsinoCoordenadaBancariaModule)
      },
      {
        path: 'arquivo',
        loadChildren: () => import('./arquivo/arquivo.module').then(m => m.EnsinoArquivoModule)
      },
      {
        path: 'professor',
        loadChildren: () => import('./professor/professor.module').then(m => m.EnsinoProfessorModule)
      },
      {
        path: 'sala',
        loadChildren: () => import('./sala/sala.module').then(m => m.EnsinoSalaModule)
      },
      {
        path: 'area-formacao',
        loadChildren: () => import('./area-formacao/area-formacao.module').then(m => m.EnsinoAreaFormacaoModule)
      },
      {
        path: 'curso',
        loadChildren: () => import('./curso/curso.module').then(m => m.EnsinoCursoModule)
      },
      {
        path: 'classe',
        loadChildren: () => import('./classe/classe.module').then(m => m.EnsinoClasseModule)
      },
      {
        path: 'disciplina',
        loadChildren: () => import('./disciplina/disciplina.module').then(m => m.EnsinoDisciplinaModule)
      },
      {
        path: 'matriz-curricular',
        loadChildren: () => import('./matriz-curricular/matriz-curricular.module').then(m => m.EnsinoMatrizCurricularModule)
      },
      {
        path: 'plano-curricular',
        loadChildren: () => import('./plano-curricular/plano-curricular.module').then(m => m.EnsinoPlanoCurricularModule)
      },
      {
        path: 'unidade-curricular',
        loadChildren: () => import('./unidade-curricular/unidade-curricular.module').then(m => m.EnsinoUnidadeCurricularModule)
      },
      {
        path: 'criterio-avaliacao',
        loadChildren: () => import('./criterio-avaliacao/criterio-avaliacao.module').then(m => m.EnsinoCriterioAvaliacaoModule)
      },
      {
        path: 'turma',
        loadChildren: () => import('./turma/turma.module').then(m => m.EnsinoTurmaModule)
      },
      {
        path: 'horario',
        loadChildren: () => import('./horario/horario.module').then(m => m.EnsinoHorarioModule)
      },
      {
        path: 'plano-actividade',
        loadChildren: () => import('./plano-actividade/plano-actividade.module').then(m => m.EnsinoPlanoActividadeModule)
      },
      {
        path: 'aula',
        loadChildren: () => import('./aula/aula.module').then(m => m.EnsinoAulaModule)
      },
      {
        path: 'dossificacao',
        loadChildren: () => import('./dossificacao/dossificacao.module').then(m => m.EnsinoDossificacaoModule)
      },
      {
        path: 'plano-aula',
        loadChildren: () => import('./plano-aula/plano-aula.module').then(m => m.EnsinoPlanoAulaModule)
      },
      {
        path: 'categoria-valiacao',
        loadChildren: () => import('./categoria-valiacao/categoria-valiacao.module').then(m => m.EnsinoCategoriaValiacaoModule)
      },
      {
        path: 'nota',
        loadChildren: () => import('./nota/nota.module').then(m => m.EnsinoNotaModule)
      },
      {
        path: 'categoria-aluno',
        loadChildren: () => import('./categoria-aluno/categoria-aluno.module').then(m => m.EnsinoCategoriaAlunoModule)
      },
      {
        path: 'ocorrencia',
        loadChildren: () => import('./ocorrencia/ocorrencia.module').then(m => m.EnsinoOcorrenciaModule)
      },
      {
        path: 'detalhe-ocorrencia',
        loadChildren: () => import('./detalhe-ocorrencia/detalhe-ocorrencia.module').then(m => m.EnsinoDetalheOcorrenciaModule)
      },
      {
        path: 'ficha-medica',
        loadChildren: () => import('./ficha-medica/ficha-medica.module').then(m => m.EnsinoFichaMedicaModule)
      },
      {
        path: 'aluno',
        loadChildren: () => import('./aluno/aluno.module').then(m => m.EnsinoAlunoModule)
      },
      {
        path: 'situacao-academica',
        loadChildren: () => import('./situacao-academica/situacao-academica.module').then(m => m.EnsinoSituacaoAcademicaModule)
      },
      {
        path: 'encarregado-educacao',
        loadChildren: () => import('./encarregado-educacao/encarregado-educacao.module').then(m => m.EnsinoEncarregadoEducacaoModule)
      },
      {
        path: 'matricula',
        loadChildren: () => import('./matricula/matricula.module').then(m => m.EnsinoMatriculaModule)
      },
      {
        path: 'documento-matricula',
        loadChildren: () => import('./documento-matricula/documento-matricula.module').then(m => m.EnsinoDocumentoMatriculaModule)
      },
      {
        path: 'categoria-requerimento',
        loadChildren: () => import('./categoria-requerimento/categoria-requerimento.module').then(m => m.EnsinoCategoriaRequerimentoModule)
      },
      {
        path: 'requerimento',
        loadChildren: () => import('./requerimento/requerimento.module').then(m => m.EnsinoRequerimentoModule)
      },
      {
        path: 'contrato',
        loadChildren: () => import('./contrato/contrato.module').then(m => m.EnsinoContratoModule)
      },
      {
        path: 'emolumento',
        loadChildren: () => import('./emolumento/emolumento.module').then(m => m.EnsinoEmolumentoModule)
      },
      {
        path: 'deposito',
        loadChildren: () => import('./deposito/deposito.module').then(m => m.EnsinoDepositoModule)
      },
      {
        path: 'forma-liquidacao',
        loadChildren: () => import('./forma-liquidacao/forma-liquidacao.module').then(m => m.EnsinoFormaLiquidacaoModule)
      },
      {
        path: 'meio-liquidacao',
        loadChildren: () => import('./meio-liquidacao/meio-liquidacao.module').then(m => m.EnsinoMeioLiquidacaoModule)
      },
      {
        path: 'pagamento',
        loadChildren: () => import('./pagamento/pagamento.module').then(m => m.EnsinoPagamentoModule)
      },
      {
        path: 'detalhe-pagamento',
        loadChildren: () => import('./detalhe-pagamento/detalhe-pagamento.module').then(m => m.EnsinoDetalhePagamentoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EnsinoEntityModule {}
