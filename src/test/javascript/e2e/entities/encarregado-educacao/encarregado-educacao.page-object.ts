import { element, by, ElementFinder } from 'protractor';

export class EncarregadoEducacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-encarregado-educacao div table .btn-danger'));
  title = element.all(by.css('rv-encarregado-educacao div h2#page-heading span')).first();

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

export class EncarregadoEducacaoUpdatePage {
  pageTitle = element(by.id('rv-encarregado-educacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  profissaoInput = element(by.id('field_profissao'));
  cargoInput = element(by.id('field_cargo'));
  faixaSalarialInput = element(by.id('field_faixaSalarial'));
  nomeEmpresaInput = element(by.id('field_nomeEmpresa'));
  contactoEmpresaInput = element(by.id('field_contactoEmpresa'));
  pessoaSelect = element(by.id('field_pessoa'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProfissaoInput(profissao: string): Promise<void> {
    await this.profissaoInput.sendKeys(profissao);
  }

  async getProfissaoInput(): Promise<string> {
    return await this.profissaoInput.getAttribute('value');
  }

  async setCargoInput(cargo: string): Promise<void> {
    await this.cargoInput.sendKeys(cargo);
  }

  async getCargoInput(): Promise<string> {
    return await this.cargoInput.getAttribute('value');
  }

  async setFaixaSalarialInput(faixaSalarial: string): Promise<void> {
    await this.faixaSalarialInput.sendKeys(faixaSalarial);
  }

  async getFaixaSalarialInput(): Promise<string> {
    return await this.faixaSalarialInput.getAttribute('value');
  }

  async setNomeEmpresaInput(nomeEmpresa: string): Promise<void> {
    await this.nomeEmpresaInput.sendKeys(nomeEmpresa);
  }

  async getNomeEmpresaInput(): Promise<string> {
    return await this.nomeEmpresaInput.getAttribute('value');
  }

  async setContactoEmpresaInput(contactoEmpresa: string): Promise<void> {
    await this.contactoEmpresaInput.sendKeys(contactoEmpresa);
  }

  async getContactoEmpresaInput(): Promise<string> {
    return await this.contactoEmpresaInput.getAttribute('value');
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

export class EncarregadoEducacaoDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-encarregadoEducacao-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-encarregadoEducacao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
