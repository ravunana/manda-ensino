import { element, by, ElementFinder } from 'protractor';

export class PagamentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-pagamento div table .btn-danger'));
  title = element.all(by.css('rv-pagamento div h2#page-heading span')).first();

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

export class PagamentoUpdatePage {
  pageTitle = element(by.id('rv-pagamento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dataInput = element(by.id('field_data'));
  numeroInput = element(by.id('field_numero'));
  utilizadorSelect = element(by.id('field_utilizador'));
  alunoSelect = element(by.id('field_aluno'));
  formaLiquidacaoSelect = element(by.id('field_formaLiquidacao'));

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

  async formaLiquidacaoSelectLastOption(): Promise<void> {
    await this.formaLiquidacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formaLiquidacaoSelectOption(option: string): Promise<void> {
    await this.formaLiquidacaoSelect.sendKeys(option);
  }

  getFormaLiquidacaoSelect(): ElementFinder {
    return this.formaLiquidacaoSelect;
  }

  async getFormaLiquidacaoSelectedOption(): Promise<string> {
    return await this.formaLiquidacaoSelect.element(by.css('option:checked')).getText();
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

export class PagamentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-pagamento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-pagamento'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
