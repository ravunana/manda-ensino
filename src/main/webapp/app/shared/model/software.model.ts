import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';

export interface ISoftware {
  id?: number;
  instituicaoEnsino?: string;
  tipoSistema?: string;
  nif?: string;
  numeroValidacaoAGT?: number;
  nome?: string;
  versao?: string;
  hashCode?: string;
  hashControl?: string;
  licencaSoftwares?: ILicencaSoftware[];
}

export class Software implements ISoftware {
  constructor(
    public id?: number,
    public instituicaoEnsino?: string,
    public tipoSistema?: string,
    public nif?: string,
    public numeroValidacaoAGT?: number,
    public nome?: string,
    public versao?: string,
    public hashCode?: string,
    public hashControl?: string,
    public licencaSoftwares?: ILicencaSoftware[]
  ) {}
}
