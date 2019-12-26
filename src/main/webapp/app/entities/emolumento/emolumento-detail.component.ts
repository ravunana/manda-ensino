import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmolumento } from 'app/shared/model/emolumento.model';

@Component({
  selector: 'rv-emolumento-detail',
  templateUrl: './emolumento-detail.component.html'
})
export class EmolumentoDetailComponent implements OnInit {
  emolumento: IEmolumento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emolumento }) => {
      this.emolumento = emolumento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
