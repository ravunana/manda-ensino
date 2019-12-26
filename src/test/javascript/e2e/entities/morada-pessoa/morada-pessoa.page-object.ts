import { element, by, ElementFinder } from 'protractor';

export class MoradaPessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-morada-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-morada-pessoa div h2#page-heading span')).first();

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

export class MoradaPessoaUpdatePage {
  pageTitle = element(by.id('rv-morada-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  provinciaInput = element(by.id('field_provincia'));
  municipioInput = element(by.id('field_municipio'));
  bairroInput = element(by.id('field_bairro'));
  ruaInput = element(by.id('field_rua'));
  quarteiraoInput = element(by.id('field_quarteirao'));
  numeroPortaInput = element(by.id('field_numeroPorta'));
  pessoaSelect = element(by.id('field_pessoa'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProvinciaInput(provincia: string): Promise<void> {
    await this.provinciaInput.sendKeys(provincia);
  }

  async getProvinciaInput(): Promise<string> {
    return await this.provinciaInput.getAttribute('value');
  }

  async setMunicipioInput(municipio: string): Promise<void> {
    await this.municipioInput.sendKeys(municipio);
  }

  async getMunicipioInput(): Promise<string> {
    return await this.municipioInput.getAttribute('value');
  }

  async setBairroInput(bairro: string): Promise<void> {
    await this.bairroInput.sendKeys(bairro);
  }

  async getBairroInput(): Promise<string> {
    return await this.bairroInput.getAttribute('value');
  }

  async setRuaInput(rua: string): Promise<void> {
    await this.ruaInput.sendKeys(rua);
  }

  async getRuaInput(): Promise<string> {
    return await this.ruaInput.getAttribute('value');
  }

  async setQuarteiraoInput(quarteirao: string): Promise<void> {
    await this.quarteiraoInput.sendKeys(quarteirao);
  }

  async getQuarteiraoInput(): Promise<string> {
    return await this.quarteiraoInput.getAttribute('value');
  }

  async setNumeroPortaInput(numeroPorta: string): Promise<void> {
    await this.numeroPortaInput.sendKeys(numeroPorta);
  }

  async getNumeroPortaInput(): Promise<string> {
    return await this.numeroPortaInput.getAttribute('value');
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

export class MoradaPessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-moradaPessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-moradaPessoa'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
