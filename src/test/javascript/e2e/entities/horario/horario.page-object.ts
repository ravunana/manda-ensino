import { element, by, ElementFinder } from 'protractor';

export class HorarioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-horario div table .btn-danger'));
  title = element.all(by.css('rv-horario div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class HorarioUpdatePage {
  pageTitle = element(by.id('rv-horario-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  inicioAulaInput = element(by.id('field_inicioAula'));
  terminoAluaInput = element(by.id('field_terminoAlua'));
  intervaloInput = element(by.id('field_intervalo'));
  diaSemanaInput = element(by.id('field_diaSemana'));
  regimeCurricularInput = element(by.id('field_regimeCurricular'));
  dataInput = element(by.id('field_data'));
  anoLectivoInput = element(by.id('field_anoLectivo'));
  categoriaInput = element(by.id('field_categoria'));
  utilizadorSelect = element(by.id('field_utilizador'));
  turmaSelect = element(by.id('field_turma'));
  professorSelect = element(by.id('field_professor'));
  disciplinaSelect = element(by.id('field_disciplina'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setInicioAulaInput(inicioAula: string): Promise<void> {
    await this.inicioAulaInput.sendKeys(inicioAula);
  }

  async getInicioAulaInput(): Promise<string> {
    return await this.inicioAulaInput.getAttribute('value');
  }

  async setTerminoAluaInput(terminoAlua: string): Promise<void> {
    await this.terminoAluaInput.sendKeys(terminoAlua);
  }

  async getTerminoAluaInput(): Promise<string> {
    return await this.terminoAluaInput.getAttribute('value');
  }

  async setIntervaloInput(intervalo: string): Promise<void> {
    await this.intervaloInput.sendKeys(intervalo);
  }

  async getIntervaloInput(): Promise<string> {
    return await this.intervaloInput.getAttribute('value');
  }

  async setDiaSemanaInput(diaSemana: string): Promise<void> {
    await this.diaSemanaInput.sendKeys(diaSemana);
  }

  async getDiaSemanaInput(): Promise<string> {
    return await this.diaSemanaInput.getAttribute('value');
  }

  async setRegimeCurricularInput(regimeCurricular: string): Promise<void> {
    await this.regimeCurricularInput.sendKeys(regimeCurricular);
  }

  async getRegimeCurricularInput(): Promise<string> {
    return await this.regimeCurricularInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async setAnoLectivoInput(anoLectivo: string): Promise<void> {
    await this.anoLectivoInput.sendKeys(anoLectivo);
  }

  async getAnoLectivoInput(): Promise<string> {
    return await this.anoLectivoInput.getAttribute('value');
  }

  async setCategoriaInput(categoria: string): Promise<void> {
    await this.categoriaInput.sendKeys(categoria);
  }

  async getCategoriaInput(): Promise<string> {
    return await this.categoriaInput.getAttribute('value');
  }

  async utilizadorSelectLastOption(): Promise<void> {
    await this.utilizadorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async utilizadorSelectOption(option: string): Promise<void> {
    await this.utilizadorSelect.sendKeys(option);
  }

  getUtilizadorSelect(): ElementFinder {
    return this.utilizadorSelect;
  }

  async getUtilizadorSelectedOption(): Promise<string> {
    return await this.utilizadorSelect.element(by.css('option:checked')).getText();
  }

  async turmaSelectLastOption(): Promise<void> {
    await this.turmaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async turmaSelectOption(option: string): Promise<void> {
    await this.turmaSelect.sendKeys(option);
  }

  getTurmaSelect(): ElementFinder {
    return this.turmaSelect;
  }

  async getTurmaSelectedOption(): Promise<string> {
    return await this.turmaSelect.element(by.css('option:checked')).getText();
  }

  async professorSelectLastOption(): Promise<void> {
    await this.professorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async professorSelectOption(option: string): Promise<void> {
    await this.professorSelect.sendKeys(option);
  }

  getProfessorSelect(): ElementFinder {
    return this.professorSelect;
  }

  async getProfessorSelectedOption(): Promise<string> {
    return await this.professorSelect.element(by.css('option:checked')).getText();
  }

  async disciplinaSelectLastOption(): Promise<void> {
    await this.disciplinaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async disciplinaSelectOption(option: string): Promise<void> {
    await this.disciplinaSelect.sendKeys(option);
  }

  getDisciplinaSelect(): ElementFinder {
    return this.disciplinaSelect;
  }

  async getDisciplinaSelectedOption(): Promise<string> {
    return await this.disciplinaSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class HorarioDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-horario-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-horario'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
