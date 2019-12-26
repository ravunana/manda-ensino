import { IPlanoCurricular } from 'app/shared/model/plano-curricular.model';
import { IHorario } from 'app/shared/model/horario.model';
import { INota } from 'app/shared/model/nota.model';
import { IPlanoAula } from 'app/shared/model/plano-aula.model';
import { IDossificacao } from 'app/shared/model/dossificacao.model';
import { IUnidadeCurricular } from 'app/shared/model/unidade-curricular.model';
import { ISituacaoAcademica } from 'app/shared/model/situacao-academica.model';

export interface IDisciplina {
  id?: number;
  nome?: string;
  sigla?: string;
  planoCurriculars?: IPlanoCurricular[];
  horarios?: IHorario[];
  notas?: INota[];
  planoAulas?: IPlanoAula[];
  dossificacaos?: IDossificacao[];
  unidadeCurriculars?: IUnidadeCurricular[];
  situacaoAcademicas?: ISituacaoAcademica[];
}

export class Disciplina implements IDisciplina {
  constructor(
    public id?: number,
    public nome?: string,
    public sigla?: string,
    public planoCurriculars?: IPlanoCurricular[],
    public horarios?: IHorario[],
    public notas?: INota[],
    public planoAulas?: IPlanoAula[],
    public dossificacaos?: IDossificacao[],
    public unidadeCurriculars?: IUnidadeCurricular[],
    public situacaoAcademicas?: ISituacaoAcademica[]
  ) {}
}
