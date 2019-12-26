import { element, by, ElementFinder } from 'protractor';

export class RelacionamentoPessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-relacionamento-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-relacionamento-pessoa div h2#page-heading span')).first();

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

export class RelacionamentoPessoaUpdatePage {
  pageTitle = element(by.id('rv-relacionamento-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  grauParentescoInput = element(by.id('field_grauParentesco'));
  deSelect = element(by.id('field_de'));
  paraSelect = element(by.id('field_para'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setGrauParentescoInput(grauParentesco: string): Promise<void> {
    await this.grauParentescoInput.sendKeys(grauParentesco);
  }

  async getGrauParentescoInput(): Promise<string> {
    return await this.grauParentescoInput.getAttribute('value');
  }

  async deSelectLastOption(): Promise<void> {
    await this.deSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deSelectOption(option: string): Promise<void> {
    await this.deSelect.sendKeys(option);
  }

  getDeSelect(): ElementFinder {
    return this.deSelect;
  }

  async getDeSelectedOption(): Promise<string> {
    return await this.deSelect.element(by.css('option:checked')).getText();
  }

  async paraSelectLastOption(): Promise<void> {
    await this.paraSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async paraSelectOption(option: string): Promise<void> {
    await this.paraSelect.sendKeys(option);
  }

  getParaSelect(): ElementFinder {
    return this.paraSelect;
  }

  async getParaSelectedOption(): Promise<string> {
    return await this.paraSelect.element(by.css('option:checked')).getText();
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

export class RelacionamentoPessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-relacionamentoPessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-relacionamentoPessoa'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
