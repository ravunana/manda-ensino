import { element, by, ElementFinder } from 'protractor';

export class CategoriaAlunoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-categoria-aluno div table .btn-danger'));
  title = element.all(by.css('rv-categoria-aluno div h2#page-heading span')).first();

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

export class CategoriaAlunoUpdatePage {
  pageTitle = element(by.id('rv-categoria-aluno-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  descontoInput = element(by.id('field_desconto'));
  pagaPropinaInput = element(by.id('field_pagaPropina'));
  pagaMultaInput = element(by.id('field_pagaMulta'));
  descricaoInput = element(by.id('field_descricao'));
  diaPagamentoInput = element(by.id('field_diaPagamento'));
  mesAtualInput = element(by.id('field_mesAtual'));
  ativoInput = element(by.id('field_ativo'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setDescontoInput(desconto: string): Promise<void> {
    await this.descontoInput.sendKeys(desconto);
  }

  async getDescontoInput(): Promise<string> {
    return await this.descontoInput.getAttribute('value');
  }

  getPagaPropinaInput(): ElementFinder {
    return this.pagaPropinaInput;
  }
  getPagaMultaInput(): ElementFinder {
    return this.pagaMultaInput;
  }
  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setDiaPagamentoInput(diaPagamento: string): Promise<void> {
    await this.diaPagamentoInput.sendKeys(diaPagamento);
  }

  async getDiaPagamentoInput(): Promise<string> {
    return await this.diaPagamentoInput.getAttribute('value');
  }

  getMesAtualInput(): ElementFinder {
    return this.mesAtualInput;
  }
  getAtivoInput(): ElementFinder {
    return this.ativoInput;
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

export class CategoriaAlunoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-categoriaAluno-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-categoriaAluno'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
