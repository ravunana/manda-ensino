import { element, by, ElementFinder } from 'protractor';

export class DetalhePagamentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-detalhe-pagamento div table .btn-danger'));
  title = element.all(by.css('rv-detalhe-pagamento div h2#page-heading span')).first();

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

export class DetalhePagamentoUpdatePage {
  pageTitle = element(by.id('rv-detalhe-pagamento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  mensalidadeInput = element(by.id('field_mensalidade'));
  quantidadeInput = element(by.id('field_quantidade'));
  valorInput = element(by.id('field_valor'));
  descontoInput = element(by.id('field_desconto'));
  multaInput = element(by.id('field_multa'));
  juroInput = element(by.id('field_juro'));
  dataInput = element(by.id('field_data'));
  vencimentoInput = element(by.id('field_vencimento'));
  quitadoInput = element(by.id('field_quitado'));
  utilizadorSelect = element(by.id('field_utilizador'));
  emolumentoSelect = element(by.id('field_emolumento'));
  depositoSelect = element(by.id('field_deposito'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  getMensalidadeInput(): ElementFinder {
    return this.mensalidadeInput;
  }
  async setQuantidadeInput(quantidade: string): Promise<void> {
    await this.quantidadeInput.sendKeys(quantidade);
  }

  async getQuantidadeInput(): Promise<string> {
    return await this.quantidadeInput.getAttribute('value');
  }

  async setValorInput(valor: string): Promise<void> {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput(): Promise<string> {
    return await this.valorInput.getAttribute('value');
  }

  async setDescontoInput(desconto: string): Promise<void> {
    await this.descontoInput.sendKeys(desconto);
  }

  async getDescontoInput(): Promise<string> {
    return await this.descontoInput.getAttribute('value');
  }

  async setMultaInput(multa: string): Promise<void> {
    await this.multaInput.sendKeys(multa);
  }

  async getMultaInput(): Promise<string> {
    return await this.multaInput.getAttribute('value');
  }

  async setJuroInput(juro: string): Promise<void> {
    await this.juroInput.sendKeys(juro);
  }

  async getJuroInput(): Promise<string> {
    return await this.juroInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async setVencimentoInput(vencimento: string): Promise<void> {
    await this.vencimentoInput.sendKeys(vencimento);
  }

  async getVencimentoInput(): Promise<string> {
    return await this.vencimentoInput.getAttribute('value');
  }

  getQuitadoInput(): ElementFinder {
    return this.quitadoInput;
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

  async emolumentoSelectLastOption(): Promise<void> {
    await this.emolumentoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async emolumentoSelectOption(option: string): Promise<void> {
    await this.emolumentoSelect.sendKeys(option);
  }

  getEmolumentoSelect(): ElementFinder {
    return this.emolumentoSelect;
  }

  async getEmolumentoSelectedOption(): Promise<string> {
    return await this.emolumentoSelect.element(by.css('option:checked')).getText();
  }

  async depositoSelectLastOption(): Promise<void> {
    await this.depositoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async depositoSelectOption(option: string): Promise<void> {
    await this.depositoSelect.sendKeys(option);
  }

  getDepositoSelect(): ElementFinder {
    return this.depositoSelect;
  }

  async getDepositoSelectedOption(): Promise<string> {
    return await this.depositoSelect.element(by.css('option:checked')).getText();
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

export class DetalhePagamentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-detalhePagamento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-detalhePagamento'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
