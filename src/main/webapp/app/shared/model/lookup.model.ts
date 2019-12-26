import { ILookupItem } from 'app/shared/model/lookup-item.model';

export interface ILookup {
  id?: number;
  nome?: string;
  entidade?: string;
  modificavel?: boolean;
  lookupItems?: ILookupItem[];
}

export class Lookup implements ILookup {
  constructor(
    public id?: number,
    public nome?: string,
    public entidade?: string,
    public modificavel?: boolean,
    public lookupItems?: ILookupItem[]
  ) {
    this.modificavel = this.modificavel || false;
  }
}
