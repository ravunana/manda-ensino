import { Moment } from 'moment';
import { ITurma } from 'app/shared/model/turma.model';
import { IAula } from 'app/shared/model/aula.model';

export interface IPlanoAula {
  id?: number;
  objectivoGeral?: any;
  objectivoEspecifico?: any;
  conteudo?: any;
  estrategia?: any;
  actividades?: any;
  tempo?: Moment;
  recursosEnsino?: any;
  avaliacao?: any;
  observacao?: any;
  bibliografia?: any;
  perfilEntrada?: any;
  perfilSaida?: any;
  anexo1ContentType?: string;
  anexo1?: any;
  anexo2ContentType?: string;
  anexo2?: any;
  anexo3ContentType?: string;
  anexo3?: any;
  utilizadorLogin?: string;
  utilizadorId?: number;
  turmas?: ITurma[];
  disciplinaNome?: string;
  disciplinaId?: number;
  dossificacaoId?: number;
  aulas?: IAula[];
}

export class PlanoAula implements IPlanoAula {
  constructor(
    public id?: number,
    public objectivoGeral?: any,
    public objectivoEspecifico?: any,
    public conteudo?: any,
    public estrategia?: any,
    public actividades?: any,
    public tempo?: Moment,
    public recursosEnsino?: any,
    public avaliacao?: any,
    public observacao?: any,
    public bibliografia?: any,
    public perfilEntrada?: any,
    public perfilSaida?: any,
    public anexo1ContentType?: string,
    public anexo1?: any,
    public anexo2ContentType?: string,
    public anexo2?: any,
    public anexo3ContentType?: string,
    public anexo3?: any,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public turmas?: ITurma[],
    public disciplinaNome?: string,
    public disciplinaId?: number,
    public dossificacaoId?: number,
    public aulas?: IAula[]
  ) {}
}
