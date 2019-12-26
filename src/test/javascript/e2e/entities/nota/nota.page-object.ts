import { element, by, ElementFinder } from 'protractor';

export class NotaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-nota div table .btn-danger'));
  title = element.all(by.css('rv-nota div h2#page-heading span')).first();

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

export class NotaUpdatePage {
  pageTitle = element(by.id('rv-nota-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valorInput = element(by.id('field_valor'));
  dataInput = element(by.id('field_data'));
  anoLectivoInput = element(by.id('field_anoLectivo'));
  periodoLectivoInput = element(by.id('field_periodoLectivo'));
  utilizadorSelect = element(by.id('field_utilizador'));
  disciplinaSelect = element(by.id('field_disciplina'));
  turmaSelect = element(by.id('field_turma'));
  categoriaAvaliacaoSelect = element(by.id('field_categoriaAvaliacao'));
  matriculaSelect = element(by.id('field_matricula'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setValorInput(valor: string): Promise<void> {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput(): Promise<string> {
    return await this.valorInput.getAttribute('value');
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

  async setPeriodoLectivoInput(periodoLectivo: string): Promise<void> {
    await this.periodoLectivoInput.sendKeys(periodoLectivo);
  }

  async getPeriodoLectivoInput(): Promise<string> {
    return await this.periodoLectivoInput.getAttribute('value');
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

  async categoriaAvaliacaoSelectLastOption(): Promise<void> {
    await this.categoriaAvaliacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async categoriaAvaliacaoSelectOption(option: string): Promise<void> {
    await this.categoriaAvaliacaoSelect.sendKeys(option);
  }

  getCategoriaAvaliacaoSelect(): ElementFinder {
    return this.categoriaAvaliacaoSelect;
  }

  async getCategoriaAvaliacaoSelectedOption(): Promise<string> {
    return await this.categoriaAvaliacaoSelect.element(by.css('option:checked')).getText();
  }

  async matriculaSelectLastOption(): Promise<void> {
    await this.matriculaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async matriculaSelectOption(option: string): Promise<void> {
    await this.matriculaSelect.sendKeys(option);
  }

  getMatriculaSelect(): ElementFinder {
    return this.matriculaSelect;
  }

  async getMatriculaSelectedOption(): Promise<string> {
    return await this.matriculaSelect.element(by.css('option:checked')).getText();
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

export class NotaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-nota-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-nota'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
