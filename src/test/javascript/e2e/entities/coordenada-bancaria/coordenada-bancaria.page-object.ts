import { element, by, ElementFinder } from 'protractor';

export class CoordenadaBancariaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-coordenada-bancaria div table .btn-danger'));
  title = element.all(by.css('rv-coordenada-bancaria div h2#page-heading span')).first();

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

export class CoordenadaBancariaUpdatePage {
  pageTitle = element(by.id('rv-coordenada-bancaria-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  proprietarioInput = element(by.id('field_proprietario'));
  numeroContaInput = element(by.id('field_numeroConta'));
  ibanInput = element(by.id('field_iban'));
  ativoInput = element(by.id('field_ativo'));
  mostrarDocumentoInput = element(by.id('field_mostrarDocumento'));
  mostrarPontoVendaInput = element(by.id('field_mostrarPontoVenda'));
  padraoRecebimentoInput = element(by.id('field_padraoRecebimento'));
  padraoPagamentoInput = element(by.id('field_padraoPagamento'));
  instituicaoEnsinoSelect = element(by.id('field_instituicaoEnsino'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setProprietarioInput(proprietario: string): Promise<void> {
    await this.proprietarioInput.sendKeys(proprietario);
  }

  async getProprietarioInput(): Promise<string> {
    return await this.proprietarioInput.getAttribute('value');
  }

  async setNumeroContaInput(numeroConta: string): Promise<void> {
    await this.numeroContaInput.sendKeys(numeroConta);
  }

  async getNumeroContaInput(): Promise<string> {
    return await this.numeroContaInput.getAttribute('value');
  }

  async setIbanInput(iban: string): Promise<void> {
    await this.ibanInput.sendKeys(iban);
  }

  async getIbanInput(): Promise<string> {
    return await this.ibanInput.getAttribute('value');
  }

  getAtivoInput(): ElementFinder {
    return this.ativoInput;
  }
  getMostrarDocumentoInput(): ElementFinder {
    return this.mostrarDocumentoInput;
  }
  getMostrarPontoVendaInput(): ElementFinder {
    return this.mostrarPontoVendaInput;
  }
  getPadraoRecebimentoInput(): ElementFinder {
    return this.padraoRecebimentoInput;
  }
  getPadraoPagamentoInput(): ElementFinder {
    return this.padraoPagamentoInput;
  }

  async instituicaoEnsinoSelectLastOption(): Promise<void> {
    await this.instituicaoEnsinoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async instituicaoEnsinoSelectOption(option: string): Promise<void> {
    await this.instituicaoEnsinoSelect.sendKeys(option);
  }

  getInstituicaoEnsinoSelect(): ElementFinder {
    return this.instituicaoEnsinoSelect;
  }

  async getInstituicaoEnsinoSelectedOption(): Promise<string> {
    return await this.instituicaoEnsinoSelect.element(by.css('option:checked')).getText();
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

export class CoordenadaBancariaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-coordenadaBancaria-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-coordenadaBancaria'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
