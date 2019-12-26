import { element, by, ElementFinder } from 'protractor';

export class PlanoAulaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-plano-aula div table .btn-danger'));
  title = element.all(by.css('rv-plano-aula div h2#page-heading span')).first();

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

export class PlanoAulaUpdatePage {
  pageTitle = element(by.id('rv-plano-aula-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  objectivoGeralInput = element(by.id('field_objectivoGeral'));
  objectivoEspecificoInput = element(by.id('field_objectivoEspecifico'));
  conteudoInput = element(by.id('field_conteudo'));
  estrategiaInput = element(by.id('field_estrategia'));
  actividadesInput = element(by.id('field_actividades'));
  tempoInput = element(by.id('field_tempo'));
  recursosEnsinoInput = element(by.id('field_recursosEnsino'));
  avaliacaoInput = element(by.id('field_avaliacao'));
  observacaoInput = element(by.id('field_observacao'));
  bibliografiaInput = element(by.id('field_bibliografia'));
  perfilEntradaInput = element(by.id('field_perfilEntrada'));
  perfilSaidaInput = element(by.id('field_perfilSaida'));
  anexo1Input = element(by.id('file_anexo1'));
  anexo2Input = element(by.id('file_anexo2'));
  anexo3Input = element(by.id('file_anexo3'));
  utilizadorSelect = element(by.id('field_utilizador'));
  turmaSelect = element(by.id('field_turma'));
  disciplinaSelect = element(by.id('field_disciplina'));
  dossificacaoSelect = element(by.id('field_dossificacao'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setConteudoInput(conteudo: string): Promise<void> {
    await this.conteudoInput.sendKeys(conteudo);
  }

  async getConteudoInput(): Promise<string> {
    return await this.conteudoInput.getAttribute('value');
  }

  async setEstrategiaInput(estrategia: string): Promise<void> {
    await this.estrategiaInput.sendKeys(estrategia);
  }

  async getEstrategiaInput(): Promise<string> {
    return await this.estrategiaInput.getAttribute('value');
  }

  async setActividadesInput(actividades: string): Promise<void> {
    await this.actividadesInput.sendKeys(actividades);
  }

  async getActividadesInput(): Promise<string> {
    return await this.actividadesInput.getAttribute('value');
  }

  async setTempoInput(tempo: string): Promise<void> {
    await this.tempoInput.sendKeys(tempo);
  }

  async getTempoInput(): Promise<string> {
    return await this.tempoInput.getAttribute('value');
  }

  async setRecursosEnsinoInput(recursosEnsino: string): Promise<void> {
    await this.recursosEnsinoInput.sendKeys(recursosEnsino);
  }

  async getRecursosEnsinoInput(): Promise<string> {
    return await this.recursosEnsinoInput.getAttribute('value');
  }

  async setAvaliacaoInput(avaliacao: string): Promise<void> {
    await this.avaliacaoInput.sendKeys(avaliacao);
  }

  async getAvaliacaoInput(): Promise<string> {
    return await this.avaliacaoInput.getAttribute('value');
  }

  async setObservacaoInput(observacao: string): Promise<void> {
    await this.observacaoInput.sendKeys(observacao);
  }

  async getObservacaoInput(): Promise<string> {
    return await this.observacaoInput.getAttribute('value');
  }

  async setBibliografiaInput(bibliografia: string): Promise<void> {
    await this.bibliografiaInput.sendKeys(bibliografia);
  }

  async getBibliografiaInput(): Promise<string> {
    return await this.bibliografiaInput.getAttribute('value');
  }

  async setPerfilEntradaInput(perfilEntrada: string): Promise<void> {
    await this.perfilEntradaInput.sendKeys(perfilEntrada);
  }

  async getPerfilEntradaInput(): Promise<string> {
    return await this.perfilEntradaInput.getAttribute('value');
  }

  async setPerfilSaidaInput(perfilSaida: string): Promise<void> {
    await this.perfilSaidaInput.sendKeys(perfilSaida);
  }

  async getPerfilSaidaInput(): Promise<string> {
    return await this.perfilSaidaInput.getAttribute('value');
  }

  async setAnexo1Input(anexo1: string): Promise<void> {
    await this.anexo1Input.sendKeys(anexo1);
  }

  async getAnexo1Input(): Promise<string> {
    return await this.anexo1Input.getAttribute('value');
  }

  async setAnexo2Input(anexo2: string): Promise<void> {
    await this.anexo2Input.sendKeys(anexo2);
  }

  async getAnexo2Input(): Promise<string> {
    return await this.anexo2Input.getAttribute('value');
  }

  async setAnexo3Input(anexo3: string): Promise<void> {
    await this.anexo3Input.sendKeys(anexo3);
  }

  async getAnexo3Input(): Promise<string> {
    return await this.anexo3Input.getAttribute('value');
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

  async dossificacaoSelectLastOption(): Promise<void> {
    await this.dossificacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async dossificacaoSelectOption(option: string): Promise<void> {
    await this.dossificacaoSelect.sendKeys(option);
  }

  getDossificacaoSelect(): ElementFinder {
    return this.dossificacaoSelect;
  }

  async getDossificacaoSelectedOption(): Promise<string> {
    return await this.dossificacaoSelect.element(by.css('option:checked')).getText();
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

export class PlanoAulaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-planoAula-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-planoAula'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
