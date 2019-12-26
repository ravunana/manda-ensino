import { Moment } from 'moment';

export interface IDocumentacaoPessoa {
  id?: number;
  tipo?: string;
  numero?: string;
  emissao?: Moment;
  validade?: Moment;
  naturalidade?: string;
  nacionalidade?: string;
  localEmissao?: string;
  nif?: string;
  pessoaNome?: string;
  pessoaId?: number;
}

export class DocumentacaoPessoa implements IDocumentacaoPessoa {
  constructor(
    public id?: number,
    public tipo?: string,
    public numero?: string,
    public emissao?: Moment,
    public validade?: Moment,
    public naturalidade?: string,
    public nacionalidade?: string,
    public localEmissao?: string,
    public nif?: string,
    public pessoaNome?: string,
    public pessoaId?: number
  ) {}
}
