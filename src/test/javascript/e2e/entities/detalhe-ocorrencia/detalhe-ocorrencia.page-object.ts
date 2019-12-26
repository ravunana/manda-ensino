import { element, by, ElementFinder } from 'protractor';

export class DetalheOcorrenciaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-detalhe-ocorrencia div table .btn-danger'));
  title = element.all(by.css('rv-detalhe-ocorrencia div h2#page-heading span')).first();

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

export class DetalheOcorrenciaUpdatePage {
  pageTitle = element(by.id('rv-detalhe-ocorrencia-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  deInput = element(by.id('field_de'));
  ateInput = element(by.id('field_ate'));
  motivoInput = element(by.id('field_motivo'));
  ocorrenciaSelect = element(by.id('field_ocorrencia'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDeInput(de: string): Promise<void> {
    await this.deInput.sendKeys(de);
  }

  async getDeInput(): Promise<string> {
    return await this.deInput.getAttribute('value');
  }

  async setAteInput(ate: string): Promise<void> {
    await this.ateInput.sendKeys(ate);
  }

  async getAteInput(): Promise<string> {
    return await this.ateInput.getAttribute('value');
  }

  async setMotivoInput(motivo: string): Promise<void> {
    await this.motivoInput.sendKeys(motivo);
  }

  async getMotivoInput(): Promise<string> {
    return await this.motivoInput.getAttribute('value');
  }

  async ocorrenciaSelectLastOption(): Promise<void> {
    await this.ocorrenciaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async ocorrenciaSelectOption(option: string): Promise<void> {
    await this.ocorrenciaSelect.sendKeys(option);
  }

  getOcorrenciaSelect(): ElementFinder {
    return this.ocorrenciaSelect;
  }

  async getOcorrenciaSelectedOption(): Promise<string> {
    return await this.ocorrenciaSelect.element(by.css('option:checked')).getText();
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

export class DetalheOcorrenciaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-detalheOcorrencia-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-detalheOcorrencia'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
