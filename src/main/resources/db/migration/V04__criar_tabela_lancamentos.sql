CREATE TABLE lancamentos(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  descricao VARCHAR(50) NOT NULL,
  data_vencimento DATE NOT NULL,
  data_pagamento DATE,
  valor DECIMAL(10,2) NOT NULL,
  observacao VARCHAR(100),
  tipo VARCHAR(20) NOT NULL,
  codigo_categoria BIGINT(20) NOT NULL,
  codigo_pessoa BIGINT(20) NOT NULL,
  FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
  FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES
  ('Salario Mensal', '2017-06-06', null, 6500.0, 'Distribuição de livros', 'RECEITA', 1, 1);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES
  ('Bahamas', '2017-07-02', '2017-09-10', 100.32, null, 'DESPESA', 2, 2);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES
  ('Top Club', '2016-08-09', '2017-09-05', 120, 'Festa', 'RECEITA', 3, 3);
