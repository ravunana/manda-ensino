import { ITurma } from 'app/shared/model/turma.model';
import { IMatrizCurricular } from 'app/shared/model/matriz-curricular.model';
import { IEmolumento } from 'app/shared/model/emolumento.model';
import { IDossificacao } from 'app/shared/model/dossificacao.model';

export interface ICurso {
  id?: number;
  nome?: string;
  sigla?: string;
  competencias?: any;
  turmas?: ITurma[];
  matrizCurriculars?: IMatrizCurricular[];
  emolumentos?: IEmolumento[];
  areaFormacaoNome?: string;
  areaFormacaoId?: number;
  dossificacaos?: IDossificacao[];
}

export class Curso implements ICurso {
  constructor(
    public id?: number,
    public nome?: string,
    public sigla?: string,
    public competencias?: any,
    public turmas?: ITurma[],
    public matrizCurriculars?: IMatrizCurricular[],
    public emolumentos?: IEmolumento[],
    public areaFormacaoNome?: string,
    public areaFormacaoId?: number,
    public dossificacaos?: IDossificacao[]
  ) {}
}
