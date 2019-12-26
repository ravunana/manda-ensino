import { element, by, ElementFinder } from 'protractor';

export class CategoriaValiacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-categoria-valiacao div table .btn-danger'));
  title = element.all(by.css('rv-categoria-valiacao div h2#page-heading span')).first();

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

export class CategoriaValiacaoUpdatePage {
  pageTitle = element(by.id('rv-categoria-valiacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  siglaInternaInput = element(by.id('field_siglaInterna'));
  siglaPautaInput = element(by.id('field_siglaPauta'));
  areaFormacaoSelect = element(by.id('field_areaFormacao'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setSiglaInternaInput(siglaInterna: string): Promise<void> {
    await this.siglaInternaInput.sendKeys(siglaInterna);
  }

  async getSiglaInternaInput(): Promise<string> {
    return await this.siglaInternaInput.getAttribute('value');
  }

  async setSiglaPautaInput(siglaPauta: string): Promise<void> {
    await this.siglaPautaInput.sendKeys(siglaPauta);
  }

  async getSiglaPautaInput(): Promise<string> {
    return await this.siglaPautaInput.getAttribute('value');
  }

  async areaFormacaoSelectLastOption(): Promise<void> {
    await this.areaFormacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async areaFormacaoSelectOption(option: string): Promise<void> {
    await this.areaFormacaoSelect.sendKeys(option);
  }

  getAreaFormacaoSelect(): ElementFinder {
    return this.areaFormacaoSelect;
  }

  async getAreaFormacaoSelectedOption(): Promise<string> {
    return await this.areaFormacaoSelect.element(by.css('option:checked')).getText();
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

export class CategoriaValiacaoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-categoriaValiacao-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-categoriaValiacao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
