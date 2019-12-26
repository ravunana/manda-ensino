import { element, by, ElementFinder } from 'protractor';

export class AulaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-aula div table .btn-danger'));
  title = element.all(by.css('rv-aula div h2#page-heading span')).first();

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

export class AulaUpdatePage {
  pageTitle = element(by.id('rv-aula-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dataInput = element(by.id('field_data'));
  sumarioInput = element(by.id('field_sumario'));
  licaoInput = element(by.id('field_licao'));
  dadaInput = element(by.id('field_dada'));
  utilizadorSelect = element(by.id('field_utilizador'));
  planoAulaSelect = element(by.id('field_planoAula'));
  turmaSelect = element(by.id('field_turma'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async setSumarioInput(sumario: string): Promise<void> {
    await this.sumarioInput.sendKeys(sumario);
  }

  async getSumarioInput(): Promise<string> {
    return await this.sumarioInput.getAttribute('value');
  }

  async setLicaoInput(licao: string): Promise<void> {
    await this.licaoInput.sendKeys(licao);
  }

  async getLicaoInput(): Promise<string> {
    return await this.licaoInput.getAttribute('value');
  }

  getDadaInput(): ElementFinder {
    return this.dadaInput;
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

  async planoAulaSelectLastOption(): Promise<void> {
    await this.planoAulaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async planoAulaSelectOption(option: string): Promise<void> {
    await this.planoAulaSelect.sendKeys(option);
  }

  getPlanoAulaSelect(): ElementFinder {
    return this.planoAulaSelect;
  }

  async getPlanoAulaSelectedOption(): Promise<string> {
    return await this.planoAulaSelect.element(by.css('option:checked')).getText();
  }

  async turmaSelectLastOption(): Promise<void> {
    await this.turmaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async turmaSelectOption(option: string): Promise<void> {
    await this.turmaSelect.sendKeys(option);
  }

  getTurmaSelect(): ElementFinder {
    return this.turmaSelect;
  }

  async getTurmaSelectedOption(): Promise<string> {
    return await this.turmaSelect.element(by.css('option:checked')).getText();
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

export class AulaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-aula-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-aula'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
