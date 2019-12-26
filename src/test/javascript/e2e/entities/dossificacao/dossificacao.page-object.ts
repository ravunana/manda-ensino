import { element, by, ElementFinder } from 'protractor';

export class DossificacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-dossificacao div table .btn-danger'));
  title = element.all(by.css('rv-dossificacao div h2#page-heading span')).first();

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

export class DossificacaoUpdatePage {
  pageTitle = element(by.id('rv-dossificacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  periodoLectivoInput = element(by.id('field_periodoLectivo'));
  anoLectivoInput = element(by.id('field_anoLectivo'));
  objectivoGeralInput = element(by.id('field_objectivoGeral'));
  objectivoEspecificoInput = element(by.id('field_objectivoEspecifico'));
  semanaLectivaInput = element(by.id('field_semanaLectiva'));
  deInput = element(by.id('field_de'));
  ateInput = element(by.id('field_ate'));
  conteudoInput = element(by.id('field_conteudo'));
  procedimentoEnsinoInput = element(by.id('field_procedimentoEnsino'));
  recursosDidaticoInput = element(by.id('field_recursosDidatico'));
  tempoAulaInput = element(by.id('field_tempoAula'));
  formaAvaliacaoInput = element(by.id('field_formaAvaliacao'));
  cursoSelect = element(by.id('field_curso'));
  classeSelect = element(by.id('field_classe'));
  disciplinaSelect = element(by.id('field_disciplina'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPeriodoLectivoInput(periodoLectivo: string): Promise<void> {
    await this.periodoLectivoInput.sendKeys(periodoLectivo);
  }

  async getPeriodoLectivoInput(): Promise<string> {
    return await this.periodoLectivoInput.getAttribute('value');
  }

  async setAnoLectivoInput(anoLectivo: string): Promise<void> {
    await this.anoLectivoInput.sendKeys(anoLectivo);
  }

  async getAnoLectivoInput(): Promise<string> {
    return await this.anoLectivoInput.getAttribute('value');
  }

  async setObjectivoGeralInput(objectivoGeral: string): Promise<void> {
    await this.objectivoGeralInput.sendKeys(objectivoGeral);
  }

  async getObjectivoGeralInput(): Promise<string> {
    return await this.objectivoGeralInput.getAttribute('value');
  }

  async setObjectivoEspecificoInput(objectivoEspecifico: string): Promise<void> {
    await this.objectivoEspecificoInput.sendKeys(objectivoEspecifico);
  }

  async getObjectivoEspecificoInput(): Promise<string> {
    return await this.objectivoEspecificoInput.getAttribute('value');
  }

  async setSemanaLectivaInput(semanaLectiva: string): Promise<void> {
    await this.semanaLectivaInput.sendKeys(semanaLectiva);
  }

  async getSemanaLectivaInput(): Promise<string> {
    return await this.semanaLectivaInput.getAttribute('value');
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

  async setConteudoInput(conteudo: string): Promise<void> {
    await this.conteudoInput.sendKeys(conteudo);
  }

  async getConteudoInput(): Promise<string> {
    return await this.conteudoInput.getAttribute('value');
  }

  async setProcedimentoEnsinoInput(procedimentoEnsino: string): Promise<void> {
    await this.procedimentoEnsinoInput.sendKeys(procedimentoEnsino);
  }

  async getProcedimentoEnsinoInput(): Promise<string> {
    return await this.procedimentoEnsinoInput.getAttribute('value');
  }

  async setRecursosDidaticoInput(recursosDidatico: string): Promise<void> {
    await this.recursosDidaticoInput.sendKeys(recursosDidatico);
  }

  async getRecursosDidaticoInput(): Promise<string> {
    return await this.recursosDidaticoInput.getAttribute('value');
  }

  async setTempoAulaInput(tempoAula: string): Promise<void> {
    await this.tempoAulaInput.sendKeys(tempoAula);
  }

  async getTempoAulaInput(): Promise<string> {
    return await this.tempoAulaInput.getAttribute('value');
  }

  async setFormaAvaliacaoInput(formaAvaliacao: string): Promise<void> {
    await this.formaAvaliacaoInput.sendKeys(formaAvaliacao);
  }

  async getFormaAvaliacaoInput(): Promise<string> {
    return await this.formaAvaliacaoInput.getAttribute('value');
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

export class DossificacaoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-dossificacao-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-dossificacao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
