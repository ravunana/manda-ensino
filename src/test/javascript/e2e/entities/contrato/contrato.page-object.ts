import { element, by, ElementFinder } from 'protractor';

export class ContratoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-contrato div table .btn-danger'));
  title = element.all(by.css('rv-contrato div h2#page-heading span')).first();

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

export class ContratoUpdatePage {
  pageTitle = element(by.id('rv-contrato-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  deInput = element(by.id('field_de'));
  ateInput = element(by.id('field_ate'));
  contratoInput = element(by.id('field_contrato'));
  aceitaTermosInput = element(by.id('field_aceitaTermos'));
  observacaoInput = element(by.id('field_observacao'));
  termosInput = element(by.id('file_termos'));
  emVigorInput = element(by.id('field_emVigor'));
  alunoSelect = element(by.id('field_aluno'));

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

  async setContratoInput(contrato: string): Promise<void> {
    await this.contratoInput.sendKeys(contrato);
  }

  async getContratoInput(): Promise<string> {
    return await this.contratoInput.getAttribute('value');
  }

  getAceitaTermosInput(): ElementFinder {
    return this.aceitaTermosInput;
  }
  async setObservacaoInput(observacao: string): Promise<void> {
    await this.observacaoInput.sendKeys(observacao);
  }

  async getObservacaoInput(): Promise<string> {
    return await this.observacaoInput.getAttribute('value');
  }

  async setTermosInput(termos: string): Promise<void> {
    await this.termosInput.sendKeys(termos);
  }

  async getTermosInput(): Promise<string> {
    return await this.termosInput.getAttribute('value');
  }

  getEmVigorInput(): ElementFinder {
    return this.emVigorInput;
  }

  async alunoSelectLastOption(): Promise<void> {
    await this.alunoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async alunoSelectOption(option: string): Promise<void> {
    await this.alunoSelect.sendKeys(option);
  }

  getAlunoSelect(): ElementFinder {
    return this.alunoSelect;
  }

  async getAlunoSelectedOption(): Promise<string> {
    return await this.alunoSelect.element(by.css('option:checked')).getText();
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

export class ContratoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-contrato-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-contrato'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
