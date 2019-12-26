import { Moment } from 'moment';

export interface IDetalheOcorrencia {
  id?: number;
  de?: Moment;
  ate?: Moment;
  motivo?: any;
  ocorrenciaNumero?: string;
  ocorrenciaId?: number;
}

export class DetalheOcorrencia implements IDetalheOcorrencia {
  constructor(
    public id?: number,
    public de?: Moment,
    public ate?: Moment,
    public motivo?: any,
    public ocorrenciaNumero?: string,
    public ocorrenciaId?: number
  ) {}
}
