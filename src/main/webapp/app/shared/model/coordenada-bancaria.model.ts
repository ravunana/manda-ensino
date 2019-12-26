import { IDeposito } from 'app/shared/model/deposito.model';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';

export interface ICoordenadaBancaria {
  id?: number;
  descricao?: string;
  proprietario?: string;
  numeroConta?: string;
  iban?: string;
  ativo?: boolean;
  mostrarDocumento?: boolean;
  mostrarPontoVenda?: boolean;
  padraoRecebimento?: boolean;
  padraoPagamento?: boolean;
  depositos?: IDeposito[];
  instituicaoEnsinos?: IInstituicaoEnsino[];
}

export class CoordenadaBancaria implements ICoordenadaBancaria {
  constructor(
    public id?: number,
    public descricao?: string,
    public proprietario?: string,
    public numeroConta?: string,
    public iban?: string,
    public ativo?: boolean,
    public mostrarDocumento?: boolean,
    public mostrarPontoVenda?: boolean,
    public padraoRecebimento?: boolean,
    public padraoPagamento?: boolean,
    public depositos?: IDeposito[],
    public instituicaoEnsinos?: IInstituicaoEnsino[]
  ) {
    this.ativo = this.ativo || false;
    this.mostrarDocumento = this.mostrarDocumento || false;
    this.mostrarPontoVenda = this.mostrarPontoVenda || false;
    this.padraoRecebimento = this.padraoRecebimento || false;
    this.padraoPagamento = this.padraoPagamento || false;
  }
}
