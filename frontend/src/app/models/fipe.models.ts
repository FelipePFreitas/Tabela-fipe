export interface Marca {
  codigo: string;
  marca: string;
}

export interface Modelo {
  codigo: string;
  modelo: string;
}

export interface Ano {
  codigo: string;
  ano: string;
}

export interface Valor {
  tipoVeiculo: string;
  valor: string;
  marca: string;
  modelo: string;
  anoModelo: string;
  combustivel: string;
  codigoFipe: string;
  mesReferencia: string;
  siglaCombustivel: string;
}
