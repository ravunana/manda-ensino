import { element, by, ElementFinder } from 'protractor';

export class SerieDocumentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-serie-documento div table .btn-danger'));
  title = element.all(by.css('rv-serie-documento div h2#page-heading span')).first();

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

export class SerieDocumentoUpdatePage {
  pageTitle = element(by.id('rv-serie-documento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  serieInput = element(by.id('field_serie'));
  sequenciaInput = element(by.id('field_sequencia'));
  entidadeInput = element(by.id('field_entidade'));
  instituicaoEnsinoSelect = element(by.id('field_instituicaoEnsino'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSerieInput(serie: string): Promise<void> {
    await this.serieInput.sendKeys(serie);
  }

  async getSerieInput(): Promise<string> {
    return await this.serieInput.getAttribute('value');
  }

  async setSequenciaInput(sequencia: string): Promise<void> {
    await this.sequenciaInput.sendKeys(sequencia);
  }

  async getSequenciaInput(): Promise<string> {
    return await this.sequenciaInput.getAttribute('value');
  }

  async setEntidadeInput(entidade: string): Promise<void> {
    await this.entidadeInput.sendKeys(entidade);
  }

  async getEntidadeInput(): Promise<string> {
    return await this.entidadeInput.getAttribute('value');
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

export class SerieDocumentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-serieDocumento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-serieDocumento'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
