import { element, by, ElementFinder } from 'protractor';

export class MatrizCurricularComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-matriz-curricular div table .btn-danger'));
  title = element.all(by.css('rv-matriz-curricular div h2#page-heading span')).first();

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

export class MatrizCurricularUpdatePage {
  pageTitle = element(by.id('rv-matriz-curricular-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  cursoSelect = element(by.id('field_curso'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async cursoSelectLastOption(): Promise<void> {
    await this.cursoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async cursoSelectOption(option: string): Promise<void> {
    await this.cursoSelect.sendKeys(option);
  }

  getCursoSelect(): ElementFinder {
    return this.cursoSelect;
  }

  async getCursoSelectedOption(): Promise<string> {
    return await this.cursoSelect.element(by.css('option:checked')).getText();
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

export class MatrizCurricularDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-matrizCurricular-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-matrizCurricular'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
