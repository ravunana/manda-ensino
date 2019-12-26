import { element, by, ElementFinder } from 'protractor';

export class DocumentacaoPessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-documentacao-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-documentacao-pessoa div h2#page-heading span')).first();

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

export class DocumentacaoPessoaUpdatePage {
  pageTitle = element(by.id('rv-documentacao-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoInput = element(by.id('field_tipo'));
  numeroInput = element(by.id('field_numero'));
  emissaoInput = element(by.id('field_emissao'));
  validadeInput = element(by.id('field_validade'));
  naturalidadeInput = element(by.id('field_naturalidade'));
  nacionalidadeInput = element(by.id('field_nacionalidade'));
  localEmissaoInput = element(by.id('field_localEmissao'));
  nifInput = element(by.id('field_nif'));
  pessoaSelect = element(by.id('field_pessoa'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoInput(tipo: string): Promise<void> {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput(): Promise<string> {
    return await this.tipoInput.getAttribute('value');
  }

  async setNumeroInput(numero: string): Promise<void> {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput(): Promise<string> {
    return await this.numeroInput.getAttribute('value');
  }

  async setEmissaoInput(emissao: string): Promise<void> {
    await this.emissaoInput.sendKeys(emissao);
  }

  async getEmissaoInput(): Promise<string> {
    return await this.emissaoInput.getAttribute('value');
  }

  async setValidadeInput(validade: string): Promise<void> {
    await this.validadeInput.sendKeys(validade);
  }

  async getValidadeInput(): Promise<string> {
    return await this.validadeInput.getAttribute('value');
  }

  async setNaturalidadeInput(naturalidade: string): Promise<void> {
    await this.naturalidadeInput.sendKeys(naturalidade);
  }

  async getNaturalidadeInput(): Promise<string> {
    return await this.naturalidadeInput.getAttribute('value');
  }

  async setNacionalidadeInput(nacionalidade: string): Promise<void> {
    await this.nacionalidadeInput.sendKeys(nacionalidade);
  }

  async getNacionalidadeInput(): Promise<string> {
    return await this.nacionalidadeInput.getAttribute('value');
  }

  async setLocalEmissaoInput(localEmissao: string): Promise<void> {
    await this.localEmissaoInput.sendKeys(localEmissao);
  }

  async getLocalEmissaoInput(): Promise<string> {
    return await this.localEmissaoInput.getAttribute('value');
  }

  async setNifInput(nif: string): Promise<void> {
    await this.nifInput.sendKeys(nif);
  }

  async getNifInput(): Promise<string> {
    return await this.nifInput.getAttribute('value');
  }

  async pessoaSelectLastOption(): Promise<void> {
    await this.pessoaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async pessoaSelectOption(option: string): Promise<void> {
    await this.pessoaSelect.sendKeys(option);
  }

  getPessoaSelect(): ElementFinder {
    return this.pessoaSelect;
  }

  async getPessoaSelectedOption(): Promise<string> {
    return await this.pessoaSelect.element(by.css('option:checked')).getText();
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

export class DocumentacaoPessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-documentacaoPessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-documentacaoPessoa'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
