import { element, by, ElementFinder } from 'protractor';

export class FormaLiquidacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-forma-liquidacao div table .btn-danger'));
  title = element.all(by.css('rv-forma-liquidacao div h2#page-heading span')).first();

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

export class FormaLiquidacaoUpdatePage {
  pageTitle = element(by.id('rv-forma-liquidacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  juroInput = element(by.id('field_juro'));
  prazoLiquidacaoInput = element(by.id('field_prazoLiquidacao'));
  quantidadeInput = element(by.id('field_quantidade'));
  iconInput = element(by.id('field_icon'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setJuroInput(juro: string): Promise<void> {
    await this.juroInput.sendKeys(juro);
  }

  async getJuroInput(): Promise<string> {
    return await this.juroInput.getAttribute('value');
  }

  async setPrazoLiquidacaoInput(prazoLiquidacao: string): Promise<void> {
    await this.prazoLiquidacaoInput.sendKeys(prazoLiquidacao);
  }

  async getPrazoLiquidacaoInput(): Promise<string> {
    return await this.prazoLiquidacaoInput.getAttribute('value');
  }

  async setQuantidadeInput(quantidade: string): Promise<void> {
    await this.quantidadeInput.sendKeys(quantidade);
  }

  async getQuantidadeInput(): Promise<string> {
    return await this.quantidadeInput.getAttribute('value');
  }

  async setIconInput(icon: string): Promise<void> {
    await this.iconInput.sendKeys(icon);
  }

  async getIconInput(): Promise<string> {
    return await this.iconInput.getAttribute('value');
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

export class FormaLiquidacaoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-formaLiquidacao-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-formaLiquidacao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
