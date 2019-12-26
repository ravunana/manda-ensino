import { element, by, ElementFinder } from 'protractor';

export class ArquivoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-arquivo div table .btn-danger'));
  title = element.all(by.css('rv-arquivo div h2#page-heading span')).first();

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

export class ArquivoUpdatePage {
  pageTitle = element(by.id('rv-arquivo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  entidadeInput = element(by.id('field_entidade'));
  anexoInput = element(by.id('file_anexo'));
  codigoEntidadeInput = element(by.id('field_codigoEntidade'));
  dataInput = element(by.id('field_data'));
  utilizadorSelect = element(by.id('field_utilizador'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setEntidadeInput(entidade: string): Promise<void> {
    await this.entidadeInput.sendKeys(entidade);
  }

  async getEntidadeInput(): Promise<string> {
    return await this.entidadeInput.getAttribute('value');
  }

  async setAnexoInput(anexo: string): Promise<void> {
    await this.anexoInput.sendKeys(anexo);
  }

  async getAnexoInput(): Promise<string> {
    return await this.anexoInput.getAttribute('value');
  }

  async setCodigoEntidadeInput(codigoEntidade: string): Promise<void> {
    await this.codigoEntidadeInput.sendKeys(codigoEntidade);
  }

  async getCodigoEntidadeInput(): Promise<string> {
    return await this.codigoEntidadeInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
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

export class ArquivoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-arquivo-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-arquivo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
