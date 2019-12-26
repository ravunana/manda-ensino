import { element, by, ElementFinder } from 'protractor';

export class CriterioAvaliacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-criterio-avaliacao div table .btn-danger'));
  title = element.all(by.css('rv-criterio-avaliacao div h2#page-heading span')).first();

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

export class CriterioAvaliacaoUpdatePage {
  pageTitle = element(by.id('rv-criterio-avaliacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  aprovaComInput = element(by.id('field_aprovaCom'));
  reporvaComInput = element(by.id('field_reporvaCom'));
  recursoComInput = element(by.id('field_recursoCom'));
  fazExameInput = element(by.id('field_fazExame'));
  fazRecursoInput = element(by.id('field_fazRecurso'));
  fazExameEspecialInput = element(by.id('field_fazExameEspecial'));
  numeroFaltaReprovaInput = element(by.id('field_numeroFaltaReprova'));
  menorNotaInput = element(by.id('field_menorNota'));
  maiorNotaInput = element(by.id('field_maiorNota'));
  notaMinimaAprovacaoInput = element(by.id('field_notaMinimaAprovacao'));
  planoCurricularSelect = element(by.id('field_planoCurricular'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAprovaComInput(aprovaCom: string): Promise<void> {
    await this.aprovaComInput.sendKeys(aprovaCom);
  }

  async getAprovaComInput(): Promise<string> {
    return await this.aprovaComInput.getAttribute('value');
  }

  async setReporvaComInput(reporvaCom: string): Promise<void> {
    await this.reporvaComInput.sendKeys(reporvaCom);
  }

  async getReporvaComInput(): Promise<string> {
    return await this.reporvaComInput.getAttribute('value');
  }

  async setRecursoComInput(recursoCom: string): Promise<void> {
    await this.recursoComInput.sendKeys(recursoCom);
  }

  async getRecursoComInput(): Promise<string> {
    return await this.recursoComInput.getAttribute('value');
  }

  getFazExameInput(): ElementFinder {
    return this.fazExameInput;
  }
  getFazRecursoInput(): ElementFinder {
    return this.fazRecursoInput;
  }
  getFazExameEspecialInput(): ElementFinder {
    return this.fazExameEspecialInput;
  }
  async setNumeroFaltaReprovaInput(numeroFaltaReprova: string): Promise<void> {
    await this.numeroFaltaReprovaInput.sendKeys(numeroFaltaReprova);
  }

  async getNumeroFaltaReprovaInput(): Promise<string> {
    return await this.numeroFaltaReprovaInput.getAttribute('value');
  }

  async setMenorNotaInput(menorNota: string): Promise<void> {
    await this.menorNotaInput.sendKeys(menorNota);
  }

  async getMenorNotaInput(): Promise<string> {
    return await this.menorNotaInput.getAttribute('value');
  }

  async setMaiorNotaInput(maiorNota: string): Promise<void> {
    await this.maiorNotaInput.sendKeys(maiorNota);
  }

  async getMaiorNotaInput(): Promise<string> {
    return await this.maiorNotaInput.getAttribute('value');
  }

  async setNotaMinimaAprovacaoInput(notaMinimaAprovacao: string): Promise<void> {
    await this.notaMinimaAprovacaoInput.sendKeys(notaMinimaAprovacao);
  }

  async getNotaMinimaAprovacaoInput(): Promise<string> {
    return await this.notaMinimaAprovacaoInput.getAttribute('value');
  }

  async planoCurricularSelectLastOption(): Promise<void> {
    await this.planoCurricularSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async planoCurricularSelectOption(option: string): Promise<void> {
    await this.planoCurricularSelect.sendKeys(option);
  }

  getPlanoCurricularSelect(): ElementFinder {
    return this.planoCurricularSelect;
  }

  async getPlanoCurricularSelectedOption(): Promise<string> {
    return await this.planoCurricularSelect.element(by.css('option:checked')).getText();
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

export class CriterioAvaliacaoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-criterioAvaliacao-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-criterioAvaliacao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
