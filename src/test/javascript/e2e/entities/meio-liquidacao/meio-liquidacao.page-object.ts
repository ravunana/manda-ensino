import { element, by, ElementFinder } from 'protractor';

export class MeioLiquidacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-meio-liquidacao div table .btn-danger'));
  title = element.all(by.css('rv-meio-liquidacao div h2#page-heading span')).first();

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

export class MeioLiquidacaoUpdatePage {
  pageTitle = element(by.id('rv-meio-liquidacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  siglaInput = element(by.id('field_sigla'));
  iconInput = element(by.id('field_icon'));
  mostrarPontoVendaInput = element(by.id('field_mostrarPontoVenda'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setSiglaInput(sigla: string): Promise<void> {
    await this.siglaInput.sendKeys(sigla);
  }

  async getSiglaInput(): Promise<string> {
    return await this.siglaInput.getAttribute('value');
  }

  async setIconInput(icon: string): Promise<void> {
    await this.iconInput.sendKeys(icon);
  }

  async getIconInput(): Promise<string> {
    return await this.iconInput.getAttribute('value');
  }

  getMostrarPontoVendaInput(): ElementFinder {
    return this.mostrarPontoVendaInput;
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

export class MeioLiquidacaoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-meioLiquidacao-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-meioLiquidacao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
