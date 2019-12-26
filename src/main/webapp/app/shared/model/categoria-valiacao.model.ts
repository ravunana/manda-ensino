import { INota } from 'app/shared/model/nota.model';

export interface ICategoriaValiacao {
  id?: number;
  nome?: string;
  siglaInterna?: string;
  siglaPauta?: string;
  notas?: INota[];
  areaFormacaoNome?: string;
  areaFormacaoId?: number;
}

export class CategoriaValiacao implements ICategoriaValiacao {
  constructor(
    public id?: number,
    public nome?: string,
    public siglaInterna?: string,
    public siglaPauta?: string,
    public notas?: INota[],
    public areaFormacaoNome?: string,
    public areaFormacaoId?: number
  ) {}
}
