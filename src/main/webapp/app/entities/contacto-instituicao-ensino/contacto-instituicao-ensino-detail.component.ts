import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';

@Component({
  selector: 'rv-contacto-instituicao-ensino-detail',
  templateUrl: './contacto-instituicao-ensino-detail.component.html'
})
export class ContactoInstituicaoEnsinoDetailComponent implements OnInit {
  contactoInstituicaoEnsino: IContactoInstituicaoEnsino | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactoInstituicaoEnsino }) => {
      this.contactoInstituicaoEnsino = contactoInstituicaoEnsino;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
