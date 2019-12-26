import { Moment } from 'moment';
import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { IDocumentacaoPessoa } from 'app/shared/model/documentacao-pessoa.model';

export interface IPessoa {
  id?: number;
  tipoPessoa?: string;
  nome?: string;
  imagemContentType?: string;
  imagem?: any;
  pai?: string;
  mae?: string;
  nascimento?: Moment;
  moradaPessoas?: IMoradaPessoa[];
  contactoPessoas?: IContactoPessoa[];
  documentacaoPessoas?: IDocumentacaoPessoa[];
  utilizadorLogin?: string;
  utilizadorId?: number;
}

export class Pessoa implements IPessoa {
  constructor(
    public id?: number,
    public tipoPessoa?: string,
    public nome?: string,
    public imagemContentType?: string,
    public imagem?: any,
    public pai?: string,
    public mae?: string,
    public nascimento?: Moment,
    public moradaPessoas?: IMoradaPessoa[],
    public contactoPessoas?: IContactoPessoa[],
    public documentacaoPessoas?: IDocumentacaoPessoa[],
    public utilizadorLogin?: string,
    public utilizadorId?: number
  ) {}
}
