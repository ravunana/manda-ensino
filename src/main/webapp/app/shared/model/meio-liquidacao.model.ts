import { IDeposito } from 'app/shared/model/deposito.model';

export interface IMeioLiquidacao {
  id?: number;
  nome?: string;
  sigla?: string;
  icon?: string;
  mostrarPontoVenda?: boolean;
  depositos?: IDeposito[];
}

export class MeioLiquidacao implements IMeioLiquidacao {
  constructor(
    public id?: number,
    public nome?: string,
    public sigla?: string,
    public icon?: string,
    public mostrarPontoVenda?: boolean,
    public depositos?: IDeposito[]
  ) {
    this.mostrarPontoVenda = this.mostrarPontoVenda || false;
  }
}
