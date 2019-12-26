import { element, by, ElementFinder } from 'protractor';

export class MatriculaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-matricula div table .btn-danger'));
  title = element.all(by.css('rv-matricula div h2#page-heading span')).first();

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

export class MatriculaUpdatePage {
  pageTitle = element(by.id('rv-matricula-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dataInput = element(by.id('field_data'));
  numeroInput = element(by.id('field_numero'));
  observacaoInput = element(by.id('field_observacao'));
  anoLectivoInput = element(by.id('field_anoLectivo'));
  peridoLectivoInput = element(by.id('field_peridoLectivo'));
  utilizadorSelect = element(by.id('field_utilizador'));
  alunoSelect = element(by.id('field_aluno'));
  confirmacaoSelect = element(by.id('field_confirmacao'));
  categoriaSelect = element(by.id('field_categoria'));
  turmaSelect = element(by.id('field_turma'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async setNumeroInput(numero: string): Promise<void> {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput(): Promise<string> {
    return await this.numeroInput.getAttribute('value');
  }

  async setObservacaoInput(observacao: string): Promise<void> {
    await this.observacaoInput.sendKeys(observacao);
  }

  async getObservacaoInput(): Promise<string> {
    return await this.observacaoInput.getAttribute('value');
  }

  async setAnoLectivoInput(anoLectivo: string): Promise<void> {
    await this.anoLectivoInput.sendKeys(anoLectivo);
  }

  async getAnoLectivoInput(): Promise<string> {
    return await this.anoLectivoInput.getAttribute('value');
  }

  async setPeridoLectivoInput(peridoLectivo: string): Promise<void> {
    await this.peridoLectivoInput.sendKeys(peridoLectivo);
  }

  async getPeridoLectivoInput(): Promise<string> {
    return await this.peridoLectivoInput.getAttribute('value');
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

  async alunoSelectLastOption(): Promise<void> {
    await this.alunoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async alunoSelectOption(option: string): Promise<void> {
    await this.alunoSelect.sendKeys(option);
  }

  getAlunoSelect(): ElementFinder {
    return this.alunoSelect;
  }

  async getAlunoSelectedOption(): Promise<string> {
    return await this.alunoSelect.element(by.css('option:checked')).getText();
  }

  async confirmacaoSelectLastOption(): Promise<void> {
    await this.confirmacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async confirmacaoSelectOption(option: string): Promise<void> {
    await this.confirmacaoSelect.sendKeys(option);
  }

  getConfirmacaoSelect(): ElementFinder {
    return this.confirmacaoSelect;
  }

  async getConfirmacaoSelectedOption(): Promise<string> {
    return await this.confirmacaoSelect.element(by.css('option:checked')).getText();
  }

  async categoriaSelectLastOption(): Promise<void> {
    await this.categoriaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async categoriaSelectOption(option: string): Promise<void> {
    await this.categoriaSelect.sendKeys(option);
  }

  getCategoriaSelect(): ElementFinder {
    return this.categoriaSelect;
  }

  async getCategoriaSelectedOption(): Promise<string> {
    return await this.categoriaSelect.element(by.css('option:checked')).getText();
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

export class MatriculaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-matricula-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-matricula'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
