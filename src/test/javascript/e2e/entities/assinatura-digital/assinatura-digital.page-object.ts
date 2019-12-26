import { element, by, ElementFinder } from 'protractor';

export class AssinaturaDigitalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-assinatura-digital div table .btn-danger'));
  title = element.all(by.css('rv-assinatura-digital div h2#page-heading span')).first();

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

export class AssinaturaDigitalUpdatePage {
  pageTitle = element(by.id('rv-assinatura-digital-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoInput = element(by.id('field_tipo'));
  assinaturaInput = element(by.id('file_assinatura'));
  hashcodeInput = element(by.id('field_hashcode'));
  dataInput = element(by.id('field_data'));
  instituicaoSelect = element(by.id('field_instituicao'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoInput(tipo: string): Promise<void> {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput(): Promise<string> {
    return await this.tipoInput.getAttribute('value');
  }

  async setAssinaturaInput(assinatura: string): Promise<void> {
    await this.assinaturaInput.sendKeys(assinatura);
  }

  async getAssinaturaInput(): Promise<string> {
    return await this.assinaturaInput.getAttribute('value');
  }

  async setHashcodeInput(hashcode: string): Promise<void> {
    await this.hashcodeInput.sendKeys(hashcode);
  }

  async getHashcodeInput(): Promise<string> {
    return await this.hashcodeInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async instituicaoSelectLastOption(): Promise<void> {
    await this.instituicaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async instituicaoSelectOption(option: string): Promise<void> {
    await this.instituicaoSelect.sendKeys(option);
  }

  getInstituicaoSelect(): ElementFinder {
    return this.instituicaoSelect;
  }

  async getInstituicaoSelectedOption(): Promise<string> {
    return await this.instituicaoSelect.element(by.css('option:checked')).getText();
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

export class AssinaturaDigitalDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-assinaturaDigital-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-assinaturaDigital'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
