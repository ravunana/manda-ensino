export interface ILocalizacaoInstituicaoEnsino {
  id?: number;
  provincia?: string;
  municipio?: string;
  bairro?: string;
  rua?: string;
  quarteirao?: string;
  numeroPorta?: string;
  caixaPostal?: string;
  instituicaoEnsinoNome?: string;
  instituicaoEnsinoId?: number;
}

export class LocalizacaoInstituicaoEnsino implements ILocalizacaoInstituicaoEnsino {
  constructor(
    public id?: number,
    public provincia?: string,
    public municipio?: string,
    public bairro?: string,
    public rua?: string,
    public quarteirao?: string,
    public numeroPorta?: string,
    public caixaPostal?: string,
    public instituicaoEnsinoNome?: string,
    public instituicaoEnsinoId?: number
  ) {}
}
