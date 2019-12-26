import { element, by, ElementFinder } from 'protractor';

export class EmolumentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-emolumento div table .btn-danger'));
  title = element.all(by.css('rv-emolumento div h2#page-heading span')).first();

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

export class EmolumentoUpdatePage {
  pageTitle = element(by.id('rv-emolumento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  valorInput = element(by.id('field_valor'));
  valorMultaInput = element(by.id('field_valorMulta'));
  tempoMultaInput = element(by.id('field_tempoMulta'));
  quantidadeInput = element(by.id('field_quantidade'));
  cursoSelect = element(by.id('field_curso'));
  classeSelect = element(by.id('field_classe'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setValorInput(valor: string): Promise<void> {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput(): Promise<string> {
    return await this.valorInput.getAttribute('value');
  }

  async setValorMultaInput(valorMulta: string): Promise<void> {
    await this.valorMultaInput.sendKeys(valorMulta);
  }

  async getValorMultaInput(): Promise<string> {
    return await this.valorMultaInput.getAttribute('value');
  }

  async setTempoMultaInput(tempoMulta: string): Promise<void> {
    await this.tempoMultaInput.sendKeys(tempoMulta);
  }

  async getTempoMultaInput(): Promise<string> {
    return await this.tempoMultaInput.getAttribute('value');
  }

  async setQuantidadeInput(quantidade: string): Promise<void> {
    await this.quantidadeInput.sendKeys(quantidade);
  }

  async getQuantidadeInput(): Promise<string> {
    return await this.quantidadeInput.getAttribute('value');
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

export class EmolumentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-emolumento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-emolumento'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
