import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDisciplina } from 'app/shared/model/disciplina.model';

@Component({
  selector: 'rv-disciplina-detail',
  templateUrl: './disciplina-detail.component.html'
})
export class DisciplinaDetailComponent implements OnInit {
  disciplina: IDisciplina | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disciplina }) => {
      this.disciplina = disciplina;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
