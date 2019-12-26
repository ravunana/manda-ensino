import { element, by, ElementFinder } from 'protractor';

export class RequerimentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-requerimento div table .btn-danger'));
  title = element.all(by.css('rv-requerimento div h2#page-heading span')).first();

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

export class RequerimentoUpdatePage {
  pageTitle = element(by.id('rv-requerimento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  requerimentoInput = element(by.id('file_requerimento'));
  dataInput = element(by.id('field_data'));
  statusRequerimentoInput = element(by.id('field_statusRequerimento'));
  utilizadorSelect = element(by.id('field_utilizador'));
  categoriaSelect = element(by.id('field_categoria'));
  alunoSelect = element(by.id('field_aluno'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRequerimentoInput(requerimento: string): Promise<void> {
    await this.requerimentoInput.sendKeys(requerimento);
  }

  async getRequerimentoInput(): Promise<string> {
    return await this.requerimentoInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async setStatusRequerimentoInput(statusRequerimento: string): Promise<void> {
    await this.statusRequerimentoInput.sendKeys(statusRequerimento);
  }

  async getStatusRequerimentoInput(): Promise<string> {
    return await this.statusRequerimentoInput.getAttribute('value');
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

export class RequerimentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-requerimento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-requerimento'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
