import { element, by, ElementFinder } from 'protractor';

export class AlunoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-aluno div table .btn-danger'));
  title = element.all(by.css('rv-aluno div h2#page-heading span')).first();

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

export class AlunoUpdatePage {
  pageTitle = element(by.id('rv-aluno-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numeroProcessoInput = element(by.id('field_numeroProcesso'));
  transferidoInput = element(by.id('field_transferido'));
  dataInput = element(by.id('field_data'));
  turmaAnteriorInput = element(by.id('field_turmaAnterior'));
  anoConclusaoInput = element(by.id('field_anoConclusao'));
  cursoFrequentadoInput = element(by.id('field_cursoFrequentado'));
  nomeEscolaAnterirorInput = element(by.id('field_nomeEscolaAnteriror'));
  enderecoEscolaAnteriorInput = element(by.id('field_enderecoEscolaAnterior'));
  classeConcluidaInput = element(by.id('field_classeConcluida'));
  numeroProcessoAnteriorInput = element(by.id('field_numeroProcessoAnterior'));
  situacaoAnteriorInput = element(by.id('field_situacaoAnterior'));
  pessoaSelect = element(by.id('field_pessoa'));
  utilizadorSelect = element(by.id('field_utilizador'));
  encarregadoEducacaoSelect = element(by.id('field_encarregadoEducacao'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumeroProcessoInput(numeroProcesso: string): Promise<void> {
    await this.numeroProcessoInput.sendKeys(numeroProcesso);
  }

  async getNumeroProcessoInput(): Promise<string> {
    return await this.numeroProcessoInput.getAttribute('value');
  }

  getTransferidoInput(): ElementFinder {
    return this.transferidoInput;
  }
  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async setTurmaAnteriorInput(turmaAnterior: string): Promise<void> {
    await this.turmaAnteriorInput.sendKeys(turmaAnterior);
  }

  async getTurmaAnteriorInput(): Promise<string> {
    return await this.turmaAnteriorInput.getAttribute('value');
  }

  async setAnoConclusaoInput(anoConclusao: string): Promise<void> {
    await this.anoConclusaoInput.sendKeys(anoConclusao);
  }

  async getAnoConclusaoInput(): Promise<string> {
    return await this.anoConclusaoInput.getAttribute('value');
  }

  async setCursoFrequentadoInput(cursoFrequentado: string): Promise<void> {
    await this.cursoFrequentadoInput.sendKeys(cursoFrequentado);
  }

  async getCursoFrequentadoInput(): Promise<string> {
    return await this.cursoFrequentadoInput.getAttribute('value');
  }

  async setNomeEscolaAnterirorInput(nomeEscolaAnteriror: string): Promise<void> {
    await this.nomeEscolaAnterirorInput.sendKeys(nomeEscolaAnteriror);
  }

  async getNomeEscolaAnterirorInput(): Promise<string> {
    return await this.nomeEscolaAnterirorInput.getAttribute('value');
  }

  async setEnderecoEscolaAnteriorInput(enderecoEscolaAnterior: string): Promise<void> {
    await this.enderecoEscolaAnteriorInput.sendKeys(enderecoEscolaAnterior);
  }

  async getEnderecoEscolaAnteriorInput(): Promise<string> {
    return await this.enderecoEscolaAnteriorInput.getAttribute('value');
  }

  async setClasseConcluidaInput(classeConcluida: string): Promise<void> {
    await this.classeConcluidaInput.sendKeys(classeConcluida);
  }

  async getClasseConcluidaInput(): Promise<string> {
    return await this.classeConcluidaInput.getAttribute('value');
  }

  async setNumeroProcessoAnteriorInput(numeroProcessoAnterior: string): Promise<void> {
    await this.numeroProcessoAnteriorInput.sendKeys(numeroProcessoAnterior);
  }

  async getNumeroProcessoAnteriorInput(): Promise<string> {
    return await this.numeroProcessoAnteriorInput.getAttribute('value');
  }

  async setSituacaoAnteriorInput(situacaoAnterior: string): Promise<void> {
    await this.situacaoAnteriorInput.sendKeys(situacaoAnterior);
  }

  async getSituacaoAnteriorInput(): Promise<string> {
    return await this.situacaoAnteriorInput.getAttribute('value');
  }

  async pessoaSelectLastOption(): Promise<void> {
    await this.pessoaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async pessoaSelectOption(option: string): Promise<void> {
    await this.pessoaSelect.sendKeys(option);
  }

  getPessoaSelect(): ElementFinder {
    return this.pessoaSelect;
  }

  async getPessoaSelectedOption(): Promise<string> {
    return await this.pessoaSelect.element(by.css('option:checked')).getText();
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

  async encarregadoEducacaoSelectLastOption(): Promise<void> {
    await this.encarregadoEducacaoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async encarregadoEducacaoSelectOption(option: string): Promise<void> {
    await this.encarregadoEducacaoSelect.sendKeys(option);
  }

  getEncarregadoEducacaoSelect(): ElementFinder {
    return this.encarregadoEducacaoSelect;
  }

  async getEncarregadoEducacaoSelectedOption(): Promise<string> {
    return await this.encarregadoEducacaoSelect.element(by.css('option:checked')).getText();
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

export class AlunoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-aluno-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-aluno'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
