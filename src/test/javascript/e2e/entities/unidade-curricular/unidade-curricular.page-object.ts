import { element, by, ElementFinder } from 'protractor';

export class UnidadeCurricularComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-unidade-curricular div table .btn-danger'));
  title = element.all(by.css('rv-unidade-curricular div h2#page-heading span')).first();

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

export class UnidadeCurricularUpdatePage {
  pageTitle = element(by.id('rv-unidade-curricular-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descricaoInput = element(by.id('field_descricao'));
  unidadeInput = element(by.id('field_unidade'));
  numeroInput = element(by.id('field_numero'));
  disciplinaSelect = element(by.id('field_disciplina'));
  classeSelect = element(by.id('field_classe'));
  herarquiaSelect = element(by.id('field_herarquia'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setUnidadeInput(unidade: string): Promise<void> {
    await this.unidadeInput.sendKeys(unidade);
  }

  async getUnidadeInput(): Promise<string> {
    return await this.unidadeInput.getAttribute('value');
  }

  async setNumeroInput(numero: string): Promise<void> {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput(): Promise<string> {
    return await this.numeroInput.getAttribute('value');
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

  async herarquiaSelectLastOption(): Promise<void> {
    await this.herarquiaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async herarquiaSelectOption(option: string): Promise<void> {
    await this.herarquiaSelect.sendKeys(option);
  }

  getHerarquiaSelect(): ElementFinder {
    return this.herarquiaSelect;
  }

  async getHerarquiaSelectedOption(): Promise<string> {
    return await this.herarquiaSelect.element(by.css('option:checked')).getText();
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

export class UnidadeCurricularDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-unidadeCurricular-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-unidadeCurricular'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
