import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoCurricular } from 'app/shared/model/plano-curricular.model';

@Component({
  selector: 'rv-plano-curricular-detail',
  templateUrl: './plano-curricular-detail.component.html'
})
export class PlanoCurricularDetailComponent implements OnInit {
  planoCurricular: IPlanoCurricular | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoCurricular }) => {
      this.planoCurricular = planoCurricular;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
