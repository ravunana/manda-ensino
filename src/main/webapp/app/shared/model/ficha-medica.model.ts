export interface IFichaMedica {
  id?: number;
  fazEducacaoFisica?: boolean;
  grupoSanguinio?: string;
  altura?: number;
  peso?: number;
  autorizaMedicamento?: boolean;
  observacao?: any;
  nomeMedico?: string;
  contactoMedico?: string;
  desmaioConstante?: boolean;
  complicacoesSaude?: any;
  alunoNumeroProcesso?: string;
  alunoId?: number;
  utilizadorLogin?: string;
  utilizadorId?: number;
}

export class FichaMedica implements IFichaMedica {
  constructor(
    public id?: number,
    public fazEducacaoFisica?: boolean,
    public grupoSanguinio?: string,
    public altura?: number,
    public peso?: number,
    public autorizaMedicamento?: boolean,
    public observacao?: any,
    public nomeMedico?: string,
    public contactoMedico?: string,
    public desmaioConstante?: boolean,
    public complicacoesSaude?: any,
    public alunoNumeroProcesso?: string,
    public alunoId?: number,
    public utilizadorLogin?: string,
    public utilizadorId?: number
  ) {
    this.fazEducacaoFisica = this.fazEducacaoFisica || false;
    this.autorizaMedicamento = this.autorizaMedicamento || false;
    this.desmaioConstante = this.desmaioConstante || false;
  }
}
