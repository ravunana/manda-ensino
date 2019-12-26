import { element, by, ElementFinder } from 'protractor';

export class ProfessorComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-professor div table .btn-danger'));
  title = element.all(by.css('rv-professor div h2#page-heading span')).first();

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

export class ProfessorUpdatePage {
  pageTitle = element(by.id('rv-professor-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numeroAgenteInput = element(by.id('field_numeroAgente'));
  ativoInput = element(by.id('field_ativo'));
  pessoaSelect = element(by.id('field_pessoa'));
  utilizadorSelect = element(by.id('field_utilizador'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumeroAgenteInput(numeroAgente: string): Promise<void> {
    await this.numeroAgenteInput.sendKeys(numeroAgente);
  }

  async getNumeroAgenteInput(): Promise<string> {
    return await this.numeroAgenteInput.getAttribute('value');
  }

  getAtivoInput(): ElementFinder {
    return this.ativoInput;
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

  async utilizadorSelectLastOption(): Promise<void> {
    await this.utilizadorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async utilizadorSelectOption(option: string): Promise<void> {
    await this.utilizadorSelect.sendKeys(option);
  }

  getUtilizadorSelect(): ElementFinder {
    return this.utilizadorSelect;
  }

  async getUtilizadorSelectedOption(): Promise<string> {
    return await this.utilizadorSelect.element(by.css('option:checked')).getText();
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

export class ProfessorDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-professor-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-professor'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
