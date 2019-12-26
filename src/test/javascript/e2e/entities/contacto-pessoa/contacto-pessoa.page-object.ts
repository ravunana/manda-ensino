import { element, by, ElementFinder } from 'protractor';

export class ContactoPessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-contacto-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-contacto-pessoa div h2#page-heading span')).first();

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

export class ContactoPessoaUpdatePage {
  pageTitle = element(by.id('rv-contacto-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoContactoInput = element(by.id('field_tipoContacto'));
  descricaoInput = element(by.id('field_descricao'));
  contactoInput = element(by.id('field_contacto'));
  pessoaSelect = element(by.id('field_pessoa'));

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

export class ContactoPessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-contactoPessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-contactoPessoa'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
