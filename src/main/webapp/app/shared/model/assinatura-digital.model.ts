import { Moment } from 'moment';

export interface IAssinaturaDigital {
  id?: number;
  tipo?: string;
  assinaturaContentType?: string;
  assinatura?: any;
  hashcode?: string;
  data?: Moment;
  instituicaoNome?: string;
  instituicaoId?: number;
}

export class AssinaturaDigital implements IAssinaturaDigital {
  constructor(
    public id?: number,
    public tipo?: string,
    public assinaturaContentType?: string,
    public assinatura?: any,
    public hashcode?: string,
    public data?: Moment,
    public instituicaoNome?: string,
    public instituicaoId?: number
  ) {}
}
