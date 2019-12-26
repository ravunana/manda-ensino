import { Moment } from 'moment';

export interface IOcorrencia {
  id?: number;
  tipo?: string;
  data?: Moment;
  numero?: string;
  reportarEncarregado?: boolean;
  utilizadorLogin?: string;
  utilizadorId?: number;
  matriculaNumero?: string;
  matriculaId?: number;
}

export class Ocorrencia implements IOcorrencia {
  constructor(
    public id?: number,
    public tipo?: string,
    public data?: Moment,
    public numero?: string,
    public reportarEncarregado?: boolean,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public matriculaNumero?: string,
    public matriculaId?: number
  ) {
    this.reportarEncarregado = this.reportarEncarregado || false;
  }
}
