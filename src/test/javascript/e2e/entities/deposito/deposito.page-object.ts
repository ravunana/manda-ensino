import { element, by, ElementFinder } from 'protractor';

export class DepositoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-deposito div table .btn-danger'));
  title = element.all(by.css('rv-deposito div h2#page-heading span')).first();

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

export class DepositoUpdatePage {
  pageTitle = element(by.id('rv-deposito-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numeroTalaoInput = element(by.id('field_numeroTalao'));
  dataDepositoInput = element(by.id('field_dataDeposito'));
  valorInput = element(by.id('field_valor'));
  saldoInput = element(by.id('field_saldo'));
  utilizadorSelect = element(by.id('field_utilizador'));
  meioLiquidacaoSelect = element(by.id('field_meioLiquidacao'));
  alunoSelect = element(by.id('field_aluno'));
  contaSelect = element(by.id('field_conta'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumeroTalaoInput(numeroTalao: string): Promise<void> {
    await this.numeroTalaoInput.sendKeys(numeroTalao);
  }

  async getNumeroTalaoInput(): Promise<string> {
    return await this.numeroTalaoInput.getAttribute('value');
  }

  async setDataDepositoInput(dataDeposito: string): Promise<void> {
    await this.dataDepositoInput.sendKeys(dataDeposito);
  }

  async getDataDepositoInput(): Promise<string> {
    return await this.dataDepositoInput.getAttribute('value');
  }

  async setValorInput(valor: string): Promise<void> {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput(): Promise<string> {
    return await this.valorInput.getAttribute('value');
  }

  async setSaldoInput(saldo: string): Promise<void> {
    await this.saldoInput.sendKeys(saldo);
  }

  async getSaldoInput(): Promise<string> {
    return await this.saldoInput.getAttribute('value');
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

  async meioLiquidacaoSelectLastOption(): Promise<void> {
    await this.meioLiquidacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async meioLiquidacaoSelectOption(option: string): Promise<void> {
    await this.meioLiquidacaoSelect.sendKeys(option);
  }

  getMeioLiquidacaoSelect(): ElementFinder {
    return this.meioLiquidacaoSelect;
  }

  async getMeioLiquidacaoSelectedOption(): Promise<string> {
    return await this.meioLiquidacaoSelect.element(by.css('option:checked')).getText();
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

  async contaSelectLastOption(): Promise<void> {
    await this.contaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contaSelectOption(option: string): Promise<void> {
    await this.contaSelect.sendKeys(option);
  }

  getContaSelect(): ElementFinder {
    return this.contaSelect;
  }

  async getContaSelectedOption(): Promise<string> {
    return await this.contaSelect.element(by.css('option:checked')).getText();
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

export class DepositoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-deposito-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-deposito'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
