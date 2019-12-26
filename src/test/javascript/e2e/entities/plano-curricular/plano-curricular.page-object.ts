import { element, by, ElementFinder } from 'protractor';

export class PlanoCurricularComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-plano-curricular div table .btn-danger'));
  title = element.all(by.css('rv-plano-curricular div h2#page-heading span')).first();

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

export class PlanoCurricularUpdatePage {
  pageTitle = element(by.id('rv-plano-curricular-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  cargaHorariaInput = element(by.id('field_cargaHoraria'));
  descricaoInput = element(by.id('field_descricao'));
  terminalInput = element(by.id('field_terminal'));
  regimeCurricularInput = element(by.id('field_regimeCurricular'));
  componenteInput = element(by.id('field_componente'));
  disciplinaSelect = element(by.id('field_disciplina'));
  classeSelect = element(by.id('field_classe'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCargaHorariaInput(cargaHoraria: string): Promise<void> {
    await this.cargaHorariaInput.sendKeys(cargaHoraria);
  }

  async getCargaHorariaInput(): Promise<string> {
    return await this.cargaHorariaInput.getAttribute('value');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  getTerminalInput(): ElementFinder {
    return this.terminalInput;
  }
  async setRegimeCurricularInput(regimeCurricular: string): Promise<void> {
    await this.regimeCurricularInput.sendKeys(regimeCurricular);
  }

  async getRegimeCurricularInput(): Promise<string> {
    return await this.regimeCurricularInput.getAttribute('value');
  }

  async setComponenteInput(componente: string): Promise<void> {
    await this.componenteInput.sendKeys(componente);
  }

  async getComponenteInput(): Promise<string> {
    return await this.componenteInput.getAttribute('value');
  }

  async disciplinaSelectLastOption(): Promise<void> {
    await this.disciplinaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async disciplinaSelectOption(option: string): Promise<void> {
    await this.disciplinaSelect.sendKeys(option);
  }

  getDisciplinaSelect(): ElementFinder {
    return this.disciplinaSelect;
  }

  async getDisciplinaSelectedOption(): Promise<string> {
    return await this.disciplinaSelect.element(by.css('option:checked')).getText();
  }

  async classeSelectLastOption(): Promise<void> {
    await this.classeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async classeSelectOption(option: string): Promise<void> {
    await this.classeSelect.sendKeys(option);
  }

  getClasseSelect(): ElementFinder {
    return this.classeSelect;
  }

  async getClasseSelectedOption(): Promise<string> {
    return await this.classeSelect.element(by.css('option:checked')).getText();
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

export class PlanoCurricularDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-planoCurricular-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-planoCurricular'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
