--BASE DE DATOS PROYECTO PAY-FACT
--ESCUELA POLITECNICA NACIONAL
--APLICACIONES EN AMBIENTES LIBRES 2015-B
--GRUPO 3

CREATE TABLE Cliente (
    idCliente INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    , cedulaIdentidad varchar(10) NOT NULL
    , nombre varchar(30) NOT NULL
    , apellido varchar(30) NOT NULL
    , direccion varchar(100) NOT NULL
    , telefono1 varchar(15) NOT NULL
    , telefono2 varchar(15) NOT NULL
);

CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    , username varchar (20)
    , password varchar (60)
);

CREATE TABLE TipoCobranza(
    idTipoCobranza INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    , nombreTipo varchar(20)
    , resultado varchar(100)

);

CREATE TABLE Factura(
    idFactura INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    , identificador VARCHAR (25)
    , idCliente INT NOT NULL
    , total INT NOT NULL
    , CONSTRAINT factura_cliente_fk
    FOREIGN KEY (idCliente)
    REFERENCES Cliente (idCliente)
);

CREATE TABLE Abono (
    idAbono INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    , idFactura INT NOT NULL
    , monto INT NOT NULL
    , fechaPago DATE NOT NULL
    , CONSTRAINT abono_factura FOREIGN KEY (idFactura) REFERENCES Factura (idFactura)
);

CREATE TABLE Cobranza(
    idCobranza INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    , idAbono INT NOT NULL
    , idTipoCobranza INT NOT NULL
    , idUsuario INT NOT NULL
    , fechaDeGestion DATE NOT NULL
    , fechaCompromisoCobro DATE
    , observaciones varchar (100)
    , tipoCobranza varchar(15)
    , CONSTRAINT cobranza_abono FOREIGN KEY (idAbono) REFERENCES Abono (idAbono)
    , CONSTRAINT cobranza_tipoCobranza FOREIGN KEY (idTipoCobranza) REFERENCES TipoCobranza (idTipoCobranza)
    , CONSTRAINT cobranza_usuario FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario)

);

INSERT INTO TipoCobranza (nombreTipo, resultado) VALUES ('Telefonica', 'Cliente no Contesta');
INSERT INTO TipoCobranza (nombreTipo, resultado) VALUES ('Telefonica', 'Se agendo cita para el pago de abono de deuda');
INSERT INTO TipoCobranza (nombreTipo, resultado) VALUES ('Domiciliaria', 'Cliente no esta');
INSERT INTO TipoCobranza (nombreTipo, resultado) VALUES ('Domiciliaria', 'Cliente no pago abono');
INSERT INTO TipoCobranza (nombreTipo, resultado) VALUES ('Domiciliaria', 'Cliente pago totalidad de la deuda');

INSERT INTO Usuario (username, password) VALUES ('Admin', 'Admin');
INSERT INTO Cliente (cedulaIdentidad, nombre, apellido, direccion, telefono1, telefono2)
    VALUES ('1725651630', 'Cristhian', 'Motoche', 'Mi casa', '09999999999', '09999999999');
INSERT INTO Factura (IDENTIFICADOR, IDCLIENTE, TOTAL) 
VALUES ('123456789'
    , (SELECT idCliente FROM Cliente WHERE cedulaIdentidad = '1725651630')
    , 1000
);
INSERT INTO Factura (IDENTIFICADOR, IDCLIENTE, TOTAL) 
VALUES ('155556789'
    , (SELECT idCliente FROM Cliente WHERE cedulaIdentidad = '1725651630')
    , 2500
);
