import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatrizCurricular } from 'app/shared/model/matriz-curricular.model';

@Component({
  selector: 'rv-matriz-curricular-detail',
  templateUrl: './matriz-curricular-detail.component.html'
})
export class MatrizCurricularDetailComponent implements OnInit {
  matrizCurricular: IMatrizCurricular | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matrizCurricular }) => {
      this.matrizCurricular = matrizCurricular;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
