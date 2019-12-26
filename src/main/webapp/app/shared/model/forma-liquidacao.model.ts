import { IPagamento } from 'app/shared/model/pagamento.model';

export interface IFormaLiquidacao {
  id?: number;
  nome?: string;
  juro?: number;
  prazoLiquidacao?: number;
  quantidade?: number;
  icon?: string;
  pagamentos?: IPagamento[];
}

export class FormaLiquidacao implements IFormaLiquidacao {
  constructor(
    public id?: number,
    public nome?: string,
    public juro?: number,
    public prazoLiquidacao?: number,
    public quantidade?: number,
    public icon?: string,
    public pagamentos?: IPagamento[]
  ) {}
}
