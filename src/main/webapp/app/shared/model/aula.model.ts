import { Moment } from 'moment';
import { IPlanoAula } from 'app/shared/model/plano-aula.model';

export interface IAula {
  id?: number;
  data?: Moment;
  sumario?: string;
  licao?: number;
  dada?: boolean;
  utilizadorLogin?: string;
  utilizadorId?: number;
  planoAulas?: IPlanoAula[];
  turmaDescricao?: string;
  turmaId?: number;
}

export class Aula implements IAula {
  constructor(
    public id?: number,
    public data?: Moment,
    public sumario?: string,
    public licao?: number,
    public dada?: boolean,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public planoAulas?: IPlanoAula[],
    public turmaDescricao?: string,
    public turmaId?: number
  ) {
    this.dada = this.dada || false;
  }
}
