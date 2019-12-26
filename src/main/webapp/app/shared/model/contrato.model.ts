import { Moment } from 'moment';

export interface IContrato {
  id?: number;
  de?: Moment;
  ate?: Moment;
  contrato?: string;
  aceitaTermos?: boolean;
  observacao?: any;
  termosContentType?: string;
  termos?: any;
  emVigor?: boolean;
  alunoNumeroProcesso?: string;
  alunoId?: number;
}

export class Contrato implements IContrato {
  constructor(
    public id?: number,
    public de?: Moment,
    public ate?: Moment,
    public contrato?: string,
    public aceitaTermos?: boolean,
    public observacao?: any,
    public termosContentType?: string,
    public termos?: any,
    public emVigor?: boolean,
    public alunoNumeroProcesso?: string,
    public alunoId?: number
  ) {
    this.aceitaTermos = this.aceitaTermos || false;
    this.emVigor = this.emVigor || false;
  }
}
