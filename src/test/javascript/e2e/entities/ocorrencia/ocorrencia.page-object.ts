import { element, by, ElementFinder } from 'protractor';

export class OcorrenciaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-ocorrencia div table .btn-danger'));
  title = element.all(by.css('rv-ocorrencia div h2#page-heading span')).first();

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

export class OcorrenciaUpdatePage {
  pageTitle = element(by.id('rv-ocorrencia-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoInput = element(by.id('field_tipo'));
  dataInput = element(by.id('field_data'));
  numeroInput = element(by.id('field_numero'));
  reportarEncarregadoInput = element(by.id('field_reportarEncarregado'));
  utilizadorSelect = element(by.id('field_utilizador'));
  matriculaSelect = element(by.id('field_matricula'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoInput(tipo: string): Promise<void> {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput(): Promise<string> {
    return await this.tipoInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async setNumeroInput(numero: string): Promise<void> {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput(): Promise<string> {
    return await this.numeroInput.getAttribute('value');
  }

  getReportarEncarregadoInput(): ElementFinder {
    return this.reportarEncarregadoInput;
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

  async matriculaSelectLastOption(): Promise<void> {
    await this.matriculaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async matriculaSelectOption(option: string): Promise<void> {
    await this.matriculaSelect.sendKeys(option);
  }

  getMatriculaSelect(): ElementFinder {
    return this.matriculaSelect;
  }

  async getMatriculaSelectedOption(): Promise<string> {
    return await this.matriculaSelect.element(by.css('option:checked')).getText();
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

export class OcorrenciaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-ocorrencia-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-ocorrencia'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
