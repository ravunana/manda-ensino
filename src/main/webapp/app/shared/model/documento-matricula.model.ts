import { Moment } from 'moment';

export interface IDocumentoMatricula {
  id?: number;
  fotografia?: boolean;
  certificado?: boolean;
  bilhete?: boolean;
  resenciamentoMilitar?: boolean;
  cartaoVacina?: boolean;
  atestadoMedico?: boolean;
  fichaTrnasferencia?: boolean;
  historicoEscolar?: boolean;
  cedula?: boolean;
  descricao?: string;
  anoLectivo?: number;
  data?: Moment;
  matriculaNumero?: string;
  matriculaId?: number;
}

export class DocumentoMatricula implements IDocumentoMatricula {
  constructor(
    public id?: number,
    public fotografia?: boolean,
    public certificado?: boolean,
    public bilhete?: boolean,
    public resenciamentoMilitar?: boolean,
    public cartaoVacina?: boolean,
    public atestadoMedico?: boolean,
    public fichaTrnasferencia?: boolean,
    public historicoEscolar?: boolean,
    public cedula?: boolean,
    public descricao?: string,
    public anoLectivo?: number,
    public data?: Moment,
    public matriculaNumero?: string,
    public matriculaId?: number
  ) {
    this.fotografia = this.fotografia || false;
    this.certificado = this.certificado || false;
    this.bilhete = this.bilhete || false;
    this.resenciamentoMilitar = this.resenciamentoMilitar || false;
    this.cartaoVacina = this.cartaoVacina || false;
    this.atestadoMedico = this.atestadoMedico || false;
    this.fichaTrnasferencia = this.fichaTrnasferencia || false;
    this.historicoEscolar = this.historicoEscolar || false;
    this.cedula = this.cedula || false;
  }
}
