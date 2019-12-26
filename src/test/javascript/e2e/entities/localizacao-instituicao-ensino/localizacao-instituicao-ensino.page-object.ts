import { element, by, ElementFinder } from 'protractor';

export class LocalizacaoInstituicaoEnsinoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-localizacao-instituicao-ensino div table .btn-danger'));
  title = element.all(by.css('rv-localizacao-instituicao-ensino div h2#page-heading span')).first();

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

export class LocalizacaoInstituicaoEnsinoUpdatePage {
  pageTitle = element(by.id('rv-localizacao-instituicao-ensino-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  provinciaInput = element(by.id('field_provincia'));
  municipioInput = element(by.id('field_municipio'));
  bairroInput = element(by.id('field_bairro'));
  ruaInput = element(by.id('field_rua'));
  quarteiraoInput = element(by.id('field_quarteirao'));
  numeroPortaInput = element(by.id('field_numeroPorta'));
  caixaPostalInput = element(by.id('field_caixaPostal'));
  instituicaoEnsinoSelect = element(by.id('field_instituicaoEnsino'));

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

  async setCaixaPostalInput(caixaPostal: string): Promise<void> {
    await this.caixaPostalInput.sendKeys(caixaPostal);
  }

  async getCaixaPostalInput(): Promise<string> {
    return await this.caixaPostalInput.getAttribute('value');
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

export class LocalizacaoInstituicaoEnsinoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-localizacaoInstituicaoEnsino-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-localizacaoInstituicaoEnsino'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
