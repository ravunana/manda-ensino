import { element, by, ElementFinder } from 'protractor';

export class PessoaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-pessoa div table .btn-danger'));
  title = element.all(by.css('rv-pessoa div h2#page-heading span')).first();

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

export class PessoaUpdatePage {
  pageTitle = element(by.id('rv-pessoa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tipoPessoaInput = element(by.id('field_tipoPessoa'));
  nomeInput = element(by.id('field_nome'));
  imagemInput = element(by.id('file_imagem'));
  paiInput = element(by.id('field_pai'));
  maeInput = element(by.id('field_mae'));
  nascimentoInput = element(by.id('field_nascimento'));
  utilizadorSelect = element(by.id('field_utilizador'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTipoPessoaInput(tipoPessoa: string): Promise<void> {
    await this.tipoPessoaInput.sendKeys(tipoPessoa);
  }

  async getTipoPessoaInput(): Promise<string> {
    return await this.tipoPessoaInput.getAttribute('value');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setImagemInput(imagem: string): Promise<void> {
    await this.imagemInput.sendKeys(imagem);
  }

  async getImagemInput(): Promise<string> {
    return await this.imagemInput.getAttribute('value');
  }

  async setPaiInput(pai: string): Promise<void> {
    await this.paiInput.sendKeys(pai);
  }

  async getPaiInput(): Promise<string> {
    return await this.paiInput.getAttribute('value');
  }

  async setMaeInput(mae: string): Promise<void> {
    await this.maeInput.sendKeys(mae);
  }

  async getMaeInput(): Promise<string> {
    return await this.maeInput.getAttribute('value');
  }

  async setNascimentoInput(nascimento: string): Promise<void> {
    await this.nascimentoInput.sendKeys(nascimento);
  }

  async getNascimentoInput(): Promise<string> {
    return await this.nascimentoInput.getAttribute('value');
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

export class PessoaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-pessoa-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-pessoa'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
