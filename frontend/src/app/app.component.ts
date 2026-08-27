import { ChangeDetectionStrategy, Component, computed, signal } from '@angular/core';
import { httpResource } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Ano, Marca, Modelo, Valor } from './models/fipe.models';

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    class: 'app-shell',
  },
  template: `
    <header class="hero">
      <div class="hero__content">
        <span class="eyebrow">CONSULTA AUTOMOTIVA</span>
        <h1>Tabela <span>FIPE</span></h1>
        <p>Encontre o valor médio de carros de forma simples, rápida e confiável.</p>
      </div>
      <div class="hero__badge" aria-hidden="true">R$</div>
    </header>

    <main class="content">
      <section class="search-card" aria-labelledby="consulta-titulo">
        <div class="section-heading">
          <div>
            <span class="step-label">CONSULTA</span>
            <h2 id="consulta-titulo">Selecione um veículo</h2>
          </div>
          <span class="status-dot" aria-label="Serviço online"></span>
        </div>

        <div class="fields-grid">
          <label class="field">
            <span>Marca</span>
            <select
              [ngModel]="codigoMarca()"
              (ngModelChange)="selecionarMarca($event)"
              [disabled]="marcas.isLoading()"
              aria-describedby="marca-ajuda">
              <option value="">Selecione a marca</option>
              @for (marca of marcas.value() ?? []; track marca.codigo) {
                <option [value]="marca.codigo">{{ marca.marca }}</option>
              }
            </select>
            <small id="marca-ajuda">{{ marcas.isLoading() ? 'Carregando marcas...' : 'Escolha uma marca' }}</small>
          </label>

          <label class="field">
            <span>Modelo</span>
            <select
              [ngModel]="codigoModelo()"
              (ngModelChange)="selecionarModelo($event)"
              [disabled]="!codigoMarca() || modelos.isLoading()"
              aria-describedby="modelo-ajuda">
              <option value="">Selecione o modelo</option>
              @for (modelo of modelos.value() ?? []; track modelo.codigo) {
                <option [value]="modelo.codigo">{{ modelo.modelo }}</option>
              }
            </select>
            <small id="modelo-ajuda">{{ codigoMarca() ? 'Escolha um modelo' : 'Selecione a marca primeiro' }}</small>
          </label>

          <label class="field">
            <span>Ano</span>
            <select
              [ngModel]="codigoAno()"
              (ngModelChange)="selecionarAno($event)"
              [disabled]="!codigoModelo() || anos.isLoading()"
              aria-describedby="ano-ajuda">
              <option value="">Selecione o ano</option>
              @for (ano of anos.value() ?? []; track ano.codigo) {
                <option [value]="ano.codigo">{{ ano.ano }}</option>
              }
            </select>
            <small id="ano-ajuda">{{ codigoModelo() ? 'Escolha o ano do veículo' : 'Selecione o modelo primeiro' }}</small>
          </label>
        </div>

        @if (erro()) {
          <div class="alert" role="alert">
            <strong>Não foi possível consultar.</strong>
            <span>Verifique se o backend Spring Boot está em execução e tente novamente.</span>
            <button type="button" class="link-button" (click)="recarregar()">Tentar novamente</button>
          </div>
        }
      </section>

      @if (valor.isLoading()) {
        <section class="result-card loading-card" aria-live="polite">
          <div class="loader" aria-hidden="true"></div>
          <p>Consultando valor na Tabela FIPE...</p>
        </section>
      } @else if (resultado(); as veiculo) {
        <section class="result-card" aria-labelledby="resultado-titulo">
          <div class="result-card__top">
            <div>
              <span class="step-label">RESULTADO DA CONSULTA</span>
              <h2 id="resultado-titulo">{{ veiculo.modelo }}</h2>
              <p>{{ veiculo.marca }} · {{ veiculo.anoModelo }}</p>
            </div>
            <span class="fuel-badge">{{ veiculo.combustivel || 'N/D' }}</span>
          </div>
          <div class="price">
            <span>Valor médio</span>
            <strong>{{ veiculo.valor }}</strong>
          </div>
          <dl class="details">
            <div><dt>Código FIPE</dt><dd>{{ veiculo.codigoFipe }}</dd></div>
            <div><dt>Mês de referência</dt><dd>{{ veiculo.mesReferencia }}</dd></div>
            <div><dt>Tipo de veículo</dt><dd>{{ veiculo.tipoVeiculo }}</dd></div>
          </dl>
        </section>
      } @else {
        <section class="empty-state" aria-live="polite">
          <div class="empty-state__icon" aria-hidden="true">⌕</div>
          <h2>Seu resultado aparecerá aqui</h2>
          <p>Preencha os três campos acima para consultar o valor do veículo.</p>
        </section>
      }
    </main>

    <footer>Dados fornecidos pela API FIPE · Desenvolvido com Spring Boot e Angular</footer>
  `,
  styles: `
    :host { display: block; min-height: 100vh; background: #f5f7fb; color: #19243a; }
    .hero { background: linear-gradient(120deg, #10244d, #1d4ed8); color: #fff; padding: 3.5rem max(1.25rem, calc((100% - 980px) / 2)); display: flex; justify-content: space-between; align-items: center; }
    .hero__content { max-width: 680px; }
    .eyebrow, .step-label { font-size: .72rem; font-weight: 800; letter-spacing: .14em; color: #83a9ff; }
    h1 { font-size: clamp(2.5rem, 7vw, 4.5rem); line-height: 1; margin: .6rem 0 1rem; letter-spacing: -.06em; }
    h1 span { color: #64d6ca; }
    .hero p { color: #d6e2ff; font-size: 1.08rem; margin: 0; }
    .hero__badge { border: 1px solid #6b8fe4; border-radius: 50%; width: 88px; height: 88px; display: grid; place-items: center; font-weight: 800; font-size: 2rem; color: #9ab8ff; }
    .content { width: min(980px, calc(100% - 2.5rem)); margin: -1.5rem auto 0; position: relative; }
    .search-card, .result-card, .empty-state { background: #fff; border: 1px solid #e4e9f2; border-radius: 18px; box-shadow: 0 14px 40px #243b6912; }
    .search-card { padding: clamp(1.25rem, 4vw, 2rem); }
    .section-heading, .result-card__top { display: flex; justify-content: space-between; align-items: flex-start; gap: 1rem; }
    h2 { margin: .35rem 0 0; font-size: clamp(1.35rem, 3vw, 1.8rem); }
    .status-dot { width: 10px; height: 10px; background: #26b580; border-radius: 50%; box-shadow: 0 0 0 5px #26b5801f; margin: .35rem; }
    .fields-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 1rem; margin-top: 1.75rem; }
    .field { display: flex; flex-direction: column; gap: .45rem; font-weight: 700; font-size: .9rem; }
    select { width: 100%; border: 1px solid #d7deeb; border-radius: 10px; padding: .85rem .9rem; font: inherit; color: #19243a; background: #fff; cursor: pointer; }
    select:focus-visible, button:focus-visible { outline: 3px solid #64d6ca; outline-offset: 2px; }
    select:disabled { background: #f1f4f8; color: #8a96aa; cursor: not-allowed; }
    small { color: #8390a5; font-weight: 400; }
    .alert { margin-top: 1.25rem; background: #fff3f1; color: #a43a30; padding: .9rem 1rem; border-radius: 10px; display: flex; flex-wrap: wrap; gap: .4rem .7rem; align-items: center; }
    .link-button { background: none; border: 0; color: #a43a30; text-decoration: underline; cursor: pointer; font: inherit; font-weight: 700; }
    .result-card, .empty-state { margin-top: 1.25rem; padding: clamp(1.25rem, 4vw, 2rem); }
    .result-card__top p { margin: .5rem 0 0; color: #718099; }
    .fuel-badge { background: #e7f8f4; color: #197d6b; border-radius: 999px; padding: .4rem .75rem; font-size: .8rem; font-weight: 800; }
    .price { margin: 1.75rem 0; padding: 1.25rem; border-radius: 12px; background: #f2f6ff; display: flex; flex-direction: column; gap: .3rem; }
    .price span { color: #61718b; font-size: .9rem; }
    .price strong { color: #1746b1; font-size: clamp(2rem, 6vw, 3rem); letter-spacing: -.04em; }
    .details { display: grid; grid-template-columns: repeat(3, 1fr); gap: 1rem; margin: 0; }
    .details div { border-top: 1px solid #e8edf5; padding-top: .8rem; }
    dt { color: #8390a5; font-size: .8rem; margin-bottom: .25rem; }
    dd { margin: 0; font-weight: 700; }
    .empty-state { text-align: center; padding-block: 3rem; }
    .empty-state__icon { margin: auto; width: 56px; height: 56px; border-radius: 50%; background: #edf2ff; color: #4167ca; display: grid; place-items: center; font-size: 2.2rem; }
    .empty-state p { color: #718099; margin-bottom: 0; }
    .loading-card { display: flex; align-items: center; gap: .8rem; color: #61718b; }
    .loader { width: 20px; height: 20px; border: 3px solid #d9e3ff; border-top-color: #2455c4; border-radius: 50%; animation: spin .8s linear infinite; }
    footer { color: #8793a6; text-align: center; padding: 2.5rem 1rem; font-size: .8rem; }
    @keyframes spin { to { transform: rotate(360deg); } }
    @media (max-width: 700px) {
      .hero { padding-top: 2.5rem; padding-bottom: 3rem; }
      .hero__badge { width: 58px; height: 58px; font-size: 1.3rem; }
      .fields-grid, .details { grid-template-columns: 1fr; }
      .content { width: min(100% - 1.25rem, 980px); }
      .alert { align-items: flex-start; flex-direction: column; }
    }
  `,
})
export class AppComponent {
  protected readonly codigoMarca = signal('');
  protected readonly codigoModelo = signal('');
  protected readonly codigoAno = signal('');

  protected readonly marcas = httpResource<Marca[]>(() => '/api/fipe/carros/marcas');
  protected readonly modelos = httpResource<Modelo[]>(() =>
    this.codigoMarca() ? `/api/fipe/carros/marcas/${this.codigoMarca()}/modelos` : undefined);
  protected readonly anos = httpResource<Ano[]>(() =>
    this.codigoMarca() && this.codigoModelo()
      ? `/api/fipe/carros/marcas/${this.codigoMarca()}/modelos/${this.codigoModelo()}/anos`
      : undefined);
  protected readonly valor = httpResource<Valor>(() =>
    this.codigoMarca() && this.codigoModelo() && this.codigoAno()
      ? `/api/fipe/carros/marcas/${this.codigoMarca()}/modelos/${this.codigoModelo()}/anos/${this.codigoAno()}`
      : undefined);

  protected readonly resultado = computed(() => this.valor.value());
  protected readonly erro = computed(() => this.marcas.error() ?? this.modelos.error() ?? this.anos.error() ?? this.valor.error());

  protected selecionarMarca(codigo: string): void {
    this.codigoMarca.set(codigo);
    this.codigoModelo.set('');
    this.codigoAno.set('');
  }

  protected selecionarModelo(codigo: string): void {
    this.codigoModelo.set(codigo);
    this.codigoAno.set('');
  }

  protected selecionarAno(codigo: string): void {
    this.codigoAno.set(codigo);
  }

  protected recarregar(): void {
    this.marcas.reload();
    this.modelos.reload();
    this.anos.reload();
    this.valor.reload();
  }
}
