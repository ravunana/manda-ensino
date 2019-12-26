import { Moment } from 'moment';
import { IInstituicaoEnsino } from 'app/shared/model/instituicao-ensino.model';
import { ILocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';
import { IContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';
import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';
import { IAssinaturaDigital } from 'app/shared/model/assinatura-digital.model';
import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

export interface IInstituicaoEnsino {
  id?: number;
  nome?: string;
  logotipoContentType?: string;
  logotipo?: any;
  fundacao?: Moment;
  numero?: string;
  tipoVinculo?: string;
  unidadePagadora?: string;
  unidadeOrganica?: string;
  tipoInstalacao?: string;
  dimensao?: string;
  carimboContentType?: string;
  carimbo?: any;
  sede?: boolean;
  instituicaoEnsinos?: IInstituicaoEnsino[];
  localizacaoInstituicaoEnsinos?: ILocalizacaoInstituicaoEnsino[];
  contactoInstituicaoEnsinos?: IContactoInstituicaoEnsino[];
  licencaSoftwares?: ILicencaSoftware[];
  assinaturaDigitals?: IAssinaturaDigital[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  hierarquiaNome?: string;
  hierarquiaId?: number;
  coordenadaBancarias?: ICoordenadaBancaria[];
}

export class InstituicaoEnsino implements IInstituicaoEnsino {
  constructor(
    public id?: number,
    public nome?: string,
    public logotipoContentType?: string,
    public logotipo?: any,
    public fundacao?: Moment,
    public numero?: string,
    public tipoVinculo?: string,
    public unidadePagadora?: string,
    public unidadeOrganica?: string,
    public tipoInstalacao?: string,
    public dimensao?: string,
    public carimboContentType?: string,
    public carimbo?: any,
    public sede?: boolean,
    public instituicaoEnsinos?: IInstituicaoEnsino[],
    public localizacaoInstituicaoEnsinos?: ILocalizacaoInstituicaoEnsino[],
    public contactoInstituicaoEnsinos?: IContactoInstituicaoEnsino[],
    public licencaSoftwares?: ILicencaSoftware[],
    public assinaturaDigitals?: IAssinaturaDigital[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public hierarquiaNome?: string,
    public hierarquiaId?: number,
    public coordenadaBancarias?: ICoordenadaBancaria[]
  ) {
    this.sede = this.sede || false;
  }
}
