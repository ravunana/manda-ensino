import { element, by, ElementFinder } from 'protractor';

export class CategoriaRequerimentoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-categoria-requerimento div table .btn-danger'));
  title = element.all(by.css('rv-categoria-requerimento div h2#page-heading span')).first();

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

export class CategoriaRequerimentoUpdatePage {
  pageTitle = element(by.id('rv-categoria-requerimento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  tempoRespostaInput = element(by.id('field_tempoResposta'));
  pagaseInput = element(by.id('field_pagase'));
  descricaoInput = element(by.id('field_descricao'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setTempoRespostaInput(tempoResposta: string): Promise<void> {
    await this.tempoRespostaInput.sendKeys(tempoResposta);
  }

  async getTempoRespostaInput(): Promise<string> {
    return await this.tempoRespostaInput.getAttribute('value');
  }

  getPagaseInput(): ElementFinder {
    return this.pagaseInput;
  }
  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
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

export class CategoriaRequerimentoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-categoriaRequerimento-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-categoriaRequerimento'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
