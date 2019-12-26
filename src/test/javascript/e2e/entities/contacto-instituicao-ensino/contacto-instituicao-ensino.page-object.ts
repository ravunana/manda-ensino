import { element, by, ElementFinder } from 'protractor';

export class ContactoInstituicaoEnsinoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-contacto-instituicao-ensino div table .btn-danger'));
  title = element.all(by.css('rv-contacto-instituicao-ensino div h2#page-heading span')).first();

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

export class ContactoInstituicaoEnsinoUpdatePage {
  pageTitle = element(by.id('rv-contacto-instituicao-ensino-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoContactoInput = element(by.id('field_tipoContacto'));
  descricaoInput = element(by.id('field_descricao'));
  contactoInput = element(by.id('field_contacto'));
  instituicaoEnsinoSelect = element(by.id('field_instituicaoEnsino'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoContactoInput(tipoContacto: string): Promise<void> {
    await this.tipoContactoInput.sendKeys(tipoContacto);
  }

  async getTipoContactoInput(): Promise<string> {
    return await this.tipoContactoInput.getAttribute('value');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setContactoInput(contacto: string): Promise<void> {
    await this.contactoInput.sendKeys(contacto);
  }

  async getContactoInput(): Promise<string> {
    return await this.contactoInput.getAttribute('value');
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

export class ContactoInstituicaoEnsinoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-contactoInstituicaoEnsino-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-contactoInstituicaoEnsino'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
