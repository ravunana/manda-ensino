export interface ISerieDocumento {
  id?: number;
  serie?: string;
  sequencia?: number;
  entidade?: string;
  instituicaoEnsinoNome?: string;
  instituicaoEnsinoId?: number;
}

export class SerieDocumento implements ISerieDocumento {
  constructor(
    public id?: number,
    public serie?: string,
    public sequencia?: number,
    public entidade?: string,
    public instituicaoEnsinoNome?: string,
    public instituicaoEnsinoId?: number
  ) {}
}
