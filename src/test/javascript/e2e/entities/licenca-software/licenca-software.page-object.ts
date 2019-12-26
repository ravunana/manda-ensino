import { element, by, ElementFinder } from 'protractor';

export class LicencaSoftwareComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-licenca-software div table .btn-danger'));
  title = element.all(by.css('rv-licenca-software div h2#page-heading span')).first();

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

export class LicencaSoftwareUpdatePage {
  pageTitle = element(by.id('rv-licenca-software-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoSubscricaoInput = element(by.id('field_tipoSubscricao'));
  inicioInput = element(by.id('field_inicio'));
  fimInput = element(by.id('field_fim'));
  dataInput = element(by.id('field_data'));
  valorInput = element(by.id('field_valor'));
  codigoInput = element(by.id('field_codigo'));
  numeroUsuarioInput = element(by.id('field_numeroUsuario'));
  numeroInstituicaoEnsinoInput = element(by.id('field_numeroInstituicaoEnsino'));
  softwareSelect = element(by.id('field_software'));
  instituicaoEnsinoSelect = element(by.id('field_instituicaoEnsino'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoSubscricaoInput(tipoSubscricao: string): Promise<void> {
    await this.tipoSubscricaoInput.sendKeys(tipoSubscricao);
  }

  async getTipoSubscricaoInput(): Promise<string> {
    return await this.tipoSubscricaoInput.getAttribute('value');
  }

  async setInicioInput(inicio: string): Promise<void> {
    await this.inicioInput.sendKeys(inicio);
  }

  async getInicioInput(): Promise<string> {
    return await this.inicioInput.getAttribute('value');
  }

  async setFimInput(fim: string): Promise<void> {
    await this.fimInput.sendKeys(fim);
  }

  async getFimInput(): Promise<string> {
    return await this.fimInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async setValorInput(valor: string): Promise<void> {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput(): Promise<string> {
    return await this.valorInput.getAttribute('value');
  }

  async setCodigoInput(codigo: string): Promise<void> {
    await this.codigoInput.sendKeys(codigo);
  }

  async getCodigoInput(): Promise<string> {
    return await this.codigoInput.getAttribute('value');
  }

  async setNumeroUsuarioInput(numeroUsuario: string): Promise<void> {
    await this.numeroUsuarioInput.sendKeys(numeroUsuario);
  }

  async getNumeroUsuarioInput(): Promise<string> {
    return await this.numeroUsuarioInput.getAttribute('value');
  }

  async setNumeroInstituicaoEnsinoInput(numeroInstituicaoEnsino: string): Promise<void> {
    await this.numeroInstituicaoEnsinoInput.sendKeys(numeroInstituicaoEnsino);
  }

  async getNumeroInstituicaoEnsinoInput(): Promise<string> {
    return await this.numeroInstituicaoEnsinoInput.getAttribute('value');
  }

  async softwareSelectLastOption(): Promise<void> {
    await this.softwareSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async softwareSelectOption(option: string): Promise<void> {
    await this.softwareSelect.sendKeys(option);
  }

  getSoftwareSelect(): ElementFinder {
    return this.softwareSelect;
  }

  async getSoftwareSelectedOption(): Promise<string> {
    return await this.softwareSelect.element(by.css('option:checked')).getText();
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

export class LicencaSoftwareDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-licencaSoftware-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-licencaSoftware'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
