export interface IContactoInstituicaoEnsino {
  id?: number;
  tipoContacto?: string;
  descricao?: string;
  contacto?: string;
  instituicaoEnsinoNome?: string;
  instituicaoEnsinoId?: number;
}

export class ContactoInstituicaoEnsino implements IContactoInstituicaoEnsino {
  constructor(
    public id?: number,
    public tipoContacto?: string,
    public descricao?: string,
    public contacto?: string,
    public instituicaoEnsinoNome?: string,
    public instituicaoEnsinoId?: number
  ) {}
}
