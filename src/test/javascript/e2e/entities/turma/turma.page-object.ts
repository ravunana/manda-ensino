import { element, by, ElementFinder } from 'protractor';

export class TurmaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-turma div table .btn-danger'));
  title = element.all(by.css('rv-turma div h2#page-heading span')).first();

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

export class TurmaUpdatePage {
  pageTitle = element(by.id('rv-turma-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  anoLectivoInput = element(by.id('field_anoLectivo'));
  regimeInput = element(by.id('field_regime'));
  turnoInput = element(by.id('field_turno'));
  dataInput = element(by.id('field_data'));
  ativoInput = element(by.id('field_ativo'));
  utilizadorSelect = element(by.id('field_utilizador'));
  salaSelect = element(by.id('field_sala'));
  classeSelect = element(by.id('field_classe'));
  cursoSelect = element(by.id('field_curso'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setAnoLectivoInput(anoLectivo: string): Promise<void> {
    await this.anoLectivoInput.sendKeys(anoLectivo);
  }

  async getAnoLectivoInput(): Promise<string> {
    return await this.anoLectivoInput.getAttribute('value');
  }

  async setRegimeInput(regime: string): Promise<void> {
    await this.regimeInput.sendKeys(regime);
  }

  async getRegimeInput(): Promise<string> {
    return await this.regimeInput.getAttribute('value');
  }

  async setTurnoInput(turno: string): Promise<void> {
    await this.turnoInput.sendKeys(turno);
  }

  async getTurnoInput(): Promise<string> {
    return await this.turnoInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  getAtivoInput(): ElementFinder {
    return this.ativoInput;
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

  async salaSelectLastOption(): Promise<void> {
    await this.salaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async salaSelectOption(option: string): Promise<void> {
    await this.salaSelect.sendKeys(option);
  }

  getSalaSelect(): ElementFinder {
    return this.salaSelect;
  }

  async getSalaSelectedOption(): Promise<string> {
    return await this.salaSelect.element(by.css('option:checked')).getText();
  }

  async classeSelectLastOption(): Promise<void> {
    await this.classeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async classeSelectOption(option: string): Promise<void> {
    await this.classeSelect.sendKeys(option);
  }

  getClasseSelect(): ElementFinder {
    return this.classeSelect;
  }

  async getClasseSelectedOption(): Promise<string> {
    return await this.classeSelect.element(by.css('option:checked')).getText();
  }

  async cursoSelectLastOption(): Promise<void> {
    await this.cursoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async cursoSelectOption(option: string): Promise<void> {
    await this.cursoSelect.sendKeys(option);
  }

  getCursoSelect(): ElementFinder {
    return this.cursoSelect;
  }

  async getCursoSelectedOption(): Promise<string> {
    return await this.cursoSelect.element(by.css('option:checked')).getText();
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

export class TurmaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-turma-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-turma'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
