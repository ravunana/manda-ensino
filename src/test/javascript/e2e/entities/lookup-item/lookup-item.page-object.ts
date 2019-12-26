import { element, by, ElementFinder } from 'protractor';

export class LookupItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-lookup-item div table .btn-danger'));
  title = element.all(by.css('rv-lookup-item div h2#page-heading span')).first();

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

export class LookupItemUpdatePage {
  pageTitle = element(by.id('rv-lookup-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valorInput = element(by.id('field_valor'));
  nomeInput = element(by.id('field_nome'));
  typeSelect = element(by.id('field_type'));
  lookupSelect = element(by.id('field_lookup'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setValorInput(valor: string): Promise<void> {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput(): Promise<string> {
    return await this.valorInput.getAttribute('value');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setTypeSelect(type: string): Promise<void> {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async lookupSelectLastOption(): Promise<void> {
    await this.lookupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async lookupSelectOption(option: string): Promise<void> {
    await this.lookupSelect.sendKeys(option);
  }

  getLookupSelect(): ElementFinder {
    return this.lookupSelect;
  }

  async getLookupSelectedOption(): Promise<string> {
    return await this.lookupSelect.element(by.css('option:checked')).getText();
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

export class LookupItemDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-lookupItem-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-lookupItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
