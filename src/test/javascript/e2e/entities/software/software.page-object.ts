import { element, by, ElementFinder } from 'protractor';

export class SoftwareComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-software div table .btn-danger'));
  title = element.all(by.css('rv-software div h2#page-heading span')).first();

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

export class SoftwareUpdatePage {
  pageTitle = element(by.id('rv-software-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  instituicaoEnsinoInput = element(by.id('field_instituicaoEnsino'));
  tipoSistemaInput = element(by.id('field_tipoSistema'));
  nifInput = element(by.id('field_nif'));
  numeroValidacaoAGTInput = element(by.id('field_numeroValidacaoAGT'));
  nomeInput = element(by.id('field_nome'));
  versaoInput = element(by.id('field_versao'));
  hashCodeInput = element(by.id('field_hashCode'));
  hashControlInput = element(by.id('field_hashControl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setInstituicaoEnsinoInput(instituicaoEnsino: string): Promise<void> {
    await this.instituicaoEnsinoInput.sendKeys(instituicaoEnsino);
  }

  async getInstituicaoEnsinoInput(): Promise<string> {
    return await this.instituicaoEnsinoInput.getAttribute('value');
  }

  async setTipoSistemaInput(tipoSistema: string): Promise<void> {
    await this.tipoSistemaInput.sendKeys(tipoSistema);
  }

  async getTipoSistemaInput(): Promise<string> {
    return await this.tipoSistemaInput.getAttribute('value');
  }

  async setNifInput(nif: string): Promise<void> {
    await this.nifInput.sendKeys(nif);
  }

  async getNifInput(): Promise<string> {
    return await this.nifInput.getAttribute('value');
  }

  async setNumeroValidacaoAGTInput(numeroValidacaoAGT: string): Promise<void> {
    await this.numeroValidacaoAGTInput.sendKeys(numeroValidacaoAGT);
  }

  async getNumeroValidacaoAGTInput(): Promise<string> {
    return await this.numeroValidacaoAGTInput.getAttribute('value');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setVersaoInput(versao: string): Promise<void> {
    await this.versaoInput.sendKeys(versao);
  }

  async getVersaoInput(): Promise<string> {
    return await this.versaoInput.getAttribute('value');
  }

  async setHashCodeInput(hashCode: string): Promise<void> {
    await this.hashCodeInput.sendKeys(hashCode);
  }

  async getHashCodeInput(): Promise<string> {
    return await this.hashCodeInput.getAttribute('value');
  }

  async setHashControlInput(hashControl: string): Promise<void> {
    await this.hashControlInput.sendKeys(hashControl);
  }

  async getHashControlInput(): Promise<string> {
    return await this.hashControlInput.getAttribute('value');
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

export class SoftwareDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-software-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-software'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
