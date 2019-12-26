import { element, by, ElementFinder } from 'protractor';

export class FichaMedicaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-ficha-medica div table .btn-danger'));
  title = element.all(by.css('rv-ficha-medica div h2#page-heading span')).first();

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

export class FichaMedicaUpdatePage {
  pageTitle = element(by.id('rv-ficha-medica-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fazEducacaoFisicaInput = element(by.id('field_fazEducacaoFisica'));
  grupoSanguinioInput = element(by.id('field_grupoSanguinio'));
  alturaInput = element(by.id('field_altura'));
  pesoInput = element(by.id('field_peso'));
  autorizaMedicamentoInput = element(by.id('field_autorizaMedicamento'));
  observacaoInput = element(by.id('field_observacao'));
  nomeMedicoInput = element(by.id('field_nomeMedico'));
  contactoMedicoInput = element(by.id('field_contactoMedico'));
  desmaioConstanteInput = element(by.id('field_desmaioConstante'));
  complicacoesSaudeInput = element(by.id('field_complicacoesSaude'));
  alunoSelect = element(by.id('field_aluno'));
  utilizadorSelect = element(by.id('field_utilizador'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getFazEducacaoFisicaInput(): ElementFinder {
    return this.fazEducacaoFisicaInput;
  }
  async setGrupoSanguinioInput(grupoSanguinio: string): Promise<void> {
    await this.grupoSanguinioInput.sendKeys(grupoSanguinio);
  }

  async getGrupoSanguinioInput(): Promise<string> {
    return await this.grupoSanguinioInput.getAttribute('value');
  }

  async setAlturaInput(altura: string): Promise<void> {
    await this.alturaInput.sendKeys(altura);
  }

  async getAlturaInput(): Promise<string> {
    return await this.alturaInput.getAttribute('value');
  }

  async setPesoInput(peso: string): Promise<void> {
    await this.pesoInput.sendKeys(peso);
  }

  async getPesoInput(): Promise<string> {
    return await this.pesoInput.getAttribute('value');
  }

  getAutorizaMedicamentoInput(): ElementFinder {
    return this.autorizaMedicamentoInput;
  }
  async setObservacaoInput(observacao: string): Promise<void> {
    await this.observacaoInput.sendKeys(observacao);
  }

  async getObservacaoInput(): Promise<string> {
    return await this.observacaoInput.getAttribute('value');
  }

  async setNomeMedicoInput(nomeMedico: string): Promise<void> {
    await this.nomeMedicoInput.sendKeys(nomeMedico);
  }

  async getNomeMedicoInput(): Promise<string> {
    return await this.nomeMedicoInput.getAttribute('value');
  }

  async setContactoMedicoInput(contactoMedico: string): Promise<void> {
    await this.contactoMedicoInput.sendKeys(contactoMedico);
  }

  async getContactoMedicoInput(): Promise<string> {
    return await this.contactoMedicoInput.getAttribute('value');
  }

  getDesmaioConstanteInput(): ElementFinder {
    return this.desmaioConstanteInput;
  }
  async setComplicacoesSaudeInput(complicacoesSaude: string): Promise<void> {
    await this.complicacoesSaudeInput.sendKeys(complicacoesSaude);
  }

  async getComplicacoesSaudeInput(): Promise<string> {
    return await this.complicacoesSaudeInput.getAttribute('value');
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

export class FichaMedicaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-fichaMedica-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-fichaMedica'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
