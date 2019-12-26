import { Moment } from 'moment';
import { IPlanoAula } from 'app/shared/model/plano-aula.model';
import { ICurso } from 'app/shared/model/curso.model';
import { IClasse } from 'app/shared/model/classe.model';

export interface IDossificacao {
  id?: number;
  periodoLectivo?: string;
  anoLectivo?: Moment;
  objectivoGeral?: string;
  objectivoEspecifico?: any;
  semanaLectiva?: number;
  de?: Moment;
  ate?: Moment;
  conteudo?: any;
  procedimentoEnsino?: any;
  recursosDidatico?: any;
  tempoAula?: Moment;
  formaAvaliacao?: string;
  planoAulas?: IPlanoAula[];
  cursos?: ICurso[];
  classes?: IClasse[];
  disciplinaNome?: string;
  disciplinaId?: number;
}

export class Dossificacao implements IDossificacao {
  constructor(
    public id?: number,
    public periodoLectivo?: string,
    public anoLectivo?: Moment,
    public objectivoGeral?: string,
    public objectivoEspecifico?: any,
    public semanaLectiva?: number,
    public de?: Moment,
    public ate?: Moment,
    public conteudo?: any,
    public procedimentoEnsino?: any,
    public recursosDidatico?: any,
    public tempoAula?: Moment,
    public formaAvaliacao?: string,
    public planoAulas?: IPlanoAula[],
    public cursos?: ICurso[],
    public classes?: IClasse[],
    public disciplinaNome?: string,
    public disciplinaId?: number
  ) {}
}
