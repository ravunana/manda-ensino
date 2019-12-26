import { ITurma } from 'app/shared/model/turma.model';
import { IPlanoCurricular } from 'app/shared/model/plano-curricular.model';
import { IUnidadeCurricular } from 'app/shared/model/unidade-curricular.model';
import { IEmolumento } from 'app/shared/model/emolumento.model';
import { IDossificacao } from 'app/shared/model/dossificacao.model';

export interface IClasse {
  id?: number;
  descricao?: number;
  tipoEnsino?: string;
  turmas?: ITurma[];
  planoCurriculars?: IPlanoCurricular[];
  unidadeCurriculars?: IUnidadeCurricular[];
  emolumentos?: IEmolumento[];
  dossificacaos?: IDossificacao[];
}

export class Classe implements IClasse {
  constructor(
    public id?: number,
    public descricao?: number,
    public tipoEnsino?: string,
    public turmas?: ITurma[],
    public planoCurriculars?: IPlanoCurricular[],
    public unidadeCurriculars?: IUnidadeCurricular[],
    public emolumentos?: IEmolumento[],
    public dossificacaos?: IDossificacao[]
  ) {}
}
