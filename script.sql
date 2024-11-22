CREATE TABLE [dbo].[tb_usuario] (
    id BIGINT NOT NULL IDENTITY, -- Identificador único
    name VARCHAR(255) NOT NULL, -- Nome do usuário
    email VARCHAR(255) NOT NULL, -- Email do usuário
    pontosAcumulados INT NOT NULL DEFAULT 0, -- Pontos acumulados (inicialmente 0)
    PRIMARY KEY (id)
);

CREATE TABLE [dbo].[tb_dispositivos] (
    id BIGINT NOT NULL IDENTITY, -- Identificador único do dispositivo
    dispositivo VARCHAR(255) NOT NULL, -- Nome do dispositivo
    potencia FLOAT NOT NULL CHECK (potencia > 0), -- Potência do dispositivo (deve ser maior que zero)
    horasPorDia INT NOT NULL CHECK (horasPorDia >= 1 AND horasPorDia <= 24), -- Horas de uso por dia (1 a 24)
    diasPorMes INT NOT NULL CHECK (diasPorMes >= 1 AND diasPorMes <= 31), -- Dias de uso por mês (1 a 31)
    usuario_id BIGINT NOT NULL, -- Referência ao usuário que possui o dispositivo
    PRIMARY KEY (id),
);

    ALTER TABLE [dbo].[tb_dispositivos] ADD CONSTRAINT FK_USUARIO_ENTREGA FOREIGN KEY (usuario_id) REFERENCES [dbo].[tb_usuario] (id); -- Chave estrangeira para tb_usuario