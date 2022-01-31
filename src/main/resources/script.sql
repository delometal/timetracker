/*
	postgres postgres
	5432
*/

	DROP SCHEMA IF EXISTS tracker CASCADE;
	
    CREATE SCHEMA tracker AUTHORIZATION postgres;

    ALTER DEFAULT PRIVILEGES IN SCHEMA tracker
    GRANT ALL ON TABLES TO postgres WITH GRANT OPTION;

    SET SCHEMA 'tracker';
	
	CREATE TYPE tracker.stato_type AS ENUM (
		'I', -- Inserito
		'C', -- Confermato
		'V'  -- Verificato
	);
	COMMENT ON TYPE tracker.stato_type IS 'I=Inserito , C=Confermato , V=Verificato';
	
	CREATE TYPE tracker.stato_utente_type AS ENUM (
		'A', -- Attivo
		'C'  -- Cessato
	);
	COMMENT ON TYPE tracker.stato_utente_type IS 'A=Attivo, C=Cessato';
	
	CREATE TYPE tracker.tipo_commessa_type AS ENUM (
		'F',
		'NF'
	);
	COMMENT ON TYPE tracker.tipo_commessa_type IS 'F, NF';	

	CREATE TYPE tracker.ruolo_type AS ENUM (
		'M', -- Management
		'A', -- Amministrazione
		'H', -- HR
		'D', -- Dipendente
		'C', -- Consulente
		'R', -- Referente/Capo progetto
		'X', -- Admin
		'S'  -- Sales
	);
	COMMENT ON TYPE tracker.ruolo_type IS 'M=Management, A=Amministrazione , H=HR , D=Dipendente , C=Consulente , R=Referente/Capo progetto , X=Admin , S=Sales';

	CREATE TYPE tracker.tipologia_pagamento_type AS ENUM (
		'30GG DF',
		'30GG FMDF',
		'60GG DF',
		'60GG FMDF',
		'90GG DF',
		'90GG FMDF',
		'120GG DF',
		'120GG FMDF'
	);
	COMMENT ON TYPE tracker.tipologia_pagamento_type IS '30GG DF, 30GG FMDF, 60GG DF, 60GG FMDF, 90GG DF, 90GG FMDF, 120GG DF, 120GG FMDF';
	
	CREATE TYPE tracker.tipo_commessa_fatturabile_type AS ENUM(
	'TM',
	'TK',
	'AM'	
	);
	COMMENT ON TYPE tracker.tipo_commessa_fatturabile_type IS 'TM, TK, AM';
	
	CREATE TYPE tracker.tipo_costo_nota_spese_type AS ENUM(
	'Aereo', 
	'Alloggio', 
	'Trasporti_e_carburante', 
	'Pasti', 
	'Conferenze_e_seminari',
	'Kilometri', 
	'Rimborso_Kilometrico', 
	'Spese_varie'
	);
	COMMENT ON TYPE tracker.tipo_costo_nota_spese_type IS 'Aereo, Alloggio, Trasporti e carburante, Pasti, Conferenze e seminari, Kilometri, Rimborso Kilometrico, Spese varie';
	
--UTENTE

    CREATE TABLE TRACKER.UTENTE(
        CODICE_PERSONA 			VARCHAR(50) PRIMARY KEY, 
        PASSWORD 				VARCHAR(50),
        STATO_UTENTE 			VARCHAR(50),
        CREATE_TIMESTAMP 		TIMESTAMP, 
        LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
        CREATE_USER 			VARCHAR(50),
        LAST_UPDATE_USER 		VARCHAR(50),
		CODICE_RESPONSABILE		VARCHAR(50),
		FOREIGN KEY (CODICE_RESPONSABILE) REFERENCES TRACKER.UTENTE(CODICE_PERSONA)
    );
	COMMENT ON TABLE TRACKER.UTENTE IS 'Questa tabella contiene gli utenti di cui si vuole tenere traccia.';
	COMMENT ON COLUMN TRACKER.UTENTE.STATO_UTENTE IS 'A=Attivo, C=Cessato';
	
	

--DIPENDENTE
    CREATE TABLE TRACKER.ANAGRAFICA_DIPENDENTE(
        CODICE_PERSONA 			VARCHAR(50) PRIMARY KEY,		
        LUOGO_DI_NASCITA 		VARCHAR(50),
        DATA_DI_NASCITA 		DATE,		
        MAIL_AZIENDALE 			VARCHAR(50),
        MAIL_PRIVATA 			VARCHAR(50),
        CELLULARE 				VARCHAR(50),
		PROVINCIA_DI_DOMICILIO 	VARCHAR(50),
		COMUNE_DI_DOMICILIO 	VARCHAR(50),
		INDIRIZZO_DI_DOMICILIO 	VARCHAR(50),
		PROVINCIA_DI_RESIDENZA 	VARCHAR(50),
		COMUNE_DI_RESIDENZA 	VARCHAR(50),
		INDIRIZZO_DI_RESIDENZA 	VARCHAR(50),
        NOME_CONTATTO_EMERGENZA VARCHAR(50),
        CELLULARE_CONTATTO_EMERGENZA VARCHAR(50),
        DATA_ASSUNZIONE 		DATE,
        IBAN 					VARCHAR(50),
        DATA_CESSAZIONE 		DATE,
		CODICE_FISCALE 			CHAR(50),
        CREATE_TIMESTAMP 		TIMESTAMP, 
        LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
        CREATE_USER 			VARCHAR(50),
        LAST_UPDATE_USER 		VARCHAR(50),
		FOREIGN KEY (CODICE_PERSONA) REFERENCES TRACKER.UTENTE(CODICE_PERSONA)
    );
	COMMENT ON TABLE TRACKER.ANAGRAFICA_DIPENDENTE IS 'Questa tabella contiene tutti i dati anagrafici dei dipendenti.';	
	

--CLIENTE
    CREATE TABLE TRACKER.ANAGRAFICA_CLIENTE(
        PARTITA_IVA 			VARCHAR(60) PRIMARY KEY,
        RAGIONE_SOCIALE			VARCHAR(50),
        CODICE_FISCALE 			CHAR(16),
        CODICE_DESTINATARIO 	VARCHAR(50),
        SEDE_LEGALE_COMUNE 		VARCHAR(50),
		SEDE_LEGALE_CAP 		CHAR(20),
        SEDE_LEGALE_INDIRIZZO 	VARCHAR(50),
        SEDE_OPERATIVA_COMUNE 	VARCHAR(50),
		SEDE_OPERATIVA_CAP 		CHAR(20),
        SEDE_OPERATIVA_INDIRIZZO VARCHAR(50),
        ACRONIMO_CLIENTE		CHAR(20),
        PROGRESSIVO_PER_COMMESSE NUMERIC(3,0),
		TIPOLOGIA_DI_PAGAMENTO 	VARCHAR(50),
        NOTE_PER_LA_FATTURAZIONE VARCHAR(255),
        CREATE_TIMESTAMP 		TIMESTAMP,
        LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
        CREATE_USER 			VARCHAR(50),
        LAST_UPDATE_USER 		VARCHAR(50)
	);
	COMMENT ON TABLE TRACKER.ANAGRAFICA_CLIENTE IS 'Questa tabella contiene i dati anagrafici dei clienti.';	
	COMMENT ON COLUMN TRACKER.ANAGRAFICA_CLIENTE.TIPOLOGIA_DI_PAGAMENTO IS '30GG DF, 30GG FMDF, 60GG DF, 60GG FMDF, 90GG DF, 90GG FMDF, 120GG DF, 120GG FMDF';


--COMMESSE
    CREATE TABLE TRACKER.COMMESSA (
        CODICE_COMMESSA 		VARCHAR(50) PRIMARY KEY,
        TIPO_COMMESSA 			VARCHAR(50),
		PARTITA_IVA 			VARCHAR(50),
        CREATE_TIMESTAMP 		TIMESTAMP,
        LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
        CREATE_USER 			VARCHAR(50),
        LAST_UPDATE_USER 		VARCHAR(50)
    );
	COMMENT ON TABLE TRACKER.COMMESSA IS 'Questa tabella contiene i dati relativi alle commesse.';	
	COMMENT ON COLUMN TRACKER.COMMESSA.TIPO_COMMESSA IS 'F, NF';


    CREATE TABLE TRACKER.COMMESSA_NON_FATTURABILE (
        CODICE_COMMESSA VARCHAR(50) PRIMARY KEY,
        DESCRIZIONE 	VARCHAR(100),
		FOREIGN KEY (CODICE_COMMESSA) REFERENCES TRACKER.COMMESSA(CODICE_COMMESSA)
    );
	COMMENT ON TABLE TRACKER.COMMESSA_NON_FATTURABILE IS 'Questa tabella contiene i dati relativi alle commesse non fatturabili.';	


    CREATE TABLE TRACKER.COMMESSA_FATTURABILE (
        CODICE_COMMESSA 					VARCHAR(50),
        PARTITA_IVA				 			VARCHAR(50),
        DESCRIZIONE_COMMESSA_PERIGEA 		VARCHAR(50),
        DESCRIZIONE_COMMESSA_CLIENTE 		VARCHAR(50),
        DATA_INIZIO_COMMESSA 				DATE,
        DATA_FINE_COMMESSA 					DATE,
        TIPO_COMMESSA 						VARCHAR(50),
        IMPORTO_COMMESSA_INIZIALE_PRESUNTO 	NUMERIC(9,2),
        TOTALE_ESTENSIONI 					NUMERIC(9,2),
        ORDINE_INTERNO_CORRENTE 			NUMERIC(9,2),
        TOTALE_ORDINE_CLIENTE_FORMALE 		NUMERIC(9,2),
        TOTALE_ORDINE 						NUMERIC(9,2),
        TOTALE_RICAVI_DA_INIZIO_COMMESSA 	NUMERIC(9,2),
        TOTALE_RICAVI_DA_INIZIO_ANNO 		NUMERIC(9,2),
        TOTALE_COSTI_DA_INIZIO_COMMESSA 	NUMERIC(9,2),
        TOTALE_COSTI_DA_INIZIO_ANNO 		NUMERIC(9,2),
        TOTALE_FATTURATO_DA_INIZIO_COMMESSA NUMERIC(9,2),
        TOTALE_FATTURATO_DA_INIZIO_ANNO 	NUMERIC(9,2),
        MARGINE_INIZIALE 					NUMERIC(5,2),
        MARGINE_DA_INIZIO_COMMESSA 			NUMERIC(5,2),
        MARGINE_DA_INIZIO_ANNO 				NUMERIC(5,2),
        PERCENTUALE_AVANZAMENTO_COSTI 		NUMERIC(5,2),
        PERCENTUALE_AVANZAMENTO_FATTURAZIONE NUMERIC(5,2),
        PERCENTUALE_SCONTO 					NUMERIC(5,2),
		RESPONSABILE_COMMERCIALE 			VARCHAR(50),
		CREATE_TIMESTAMP 					TIMESTAMP, 
        LAST_UPDATE_TIMESTAMP 				TIMESTAMP, 
        CREATE_USER 						VARCHAR(50),
        LAST_UPDATE_USER 					VARCHAR(50),
		PRIMARY KEY (CODICE_COMMESSA),
		FOREIGN KEY (CODICE_COMMESSA) REFERENCES TRACKER.COMMESSA(CODICE_COMMESSA),
		FOREIGN KEY (PARTITA_IVA) REFERENCES TRACKER.ANAGRAFICA_CLIENTE(PARTITA_IVA)
    );
	COMMENT ON TABLE TRACKER.COMMESSA_FATTURABILE IS 'Questa tabella contiene i dati relativi alle commesse fatturabili.';	
	COMMENT ON COLUMN TRACKER.COMMESSA_FATTURABILE.TIPO_COMMESSA IS 'TM, TK, AM';

--NOTA SPESE
    CREATE TABLE TRACKER.NOTA_SPESE (
        ANNO_DI_RIFERIMENTO 	NUMERIC(4,0),
        MESE_DI_RIFERIMENTO 	NUMERIC(2,0),
        CODICE_PERSONA 			VARCHAR(50),
        CODICE_COMMESSA 		VARCHAR(50),
        GIORNO_DI_RIFERIMENTO 	NUMERIC(2,0),
        TIPO_COSTO 				VARCHAR(50),
        IMPORTO 				NUMERIC(7,2),
        STATO_NOTA_SPESE 		VARCHAR(50),
		CREATE_TIMESTAMP 		TIMESTAMP, 
        LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
        CREATE_USER 			VARCHAR(50),
        LAST_UPDATE_USER 		VARCHAR(50),
        PRIMARY KEY	(ANNO_DI_RIFERIMENTO, MESE_DI_RIFERIMENTO, CODICE_PERSONA, CODICE_COMMESSA, GIORNO_DI_RIFERIMENTO, TIPO_COSTO),
		FOREIGN KEY (CODICE_PERSONA) REFERENCES TRACKER.UTENTE(CODICE_PERSONA),
		FOREIGN KEY (CODICE_COMMESSA) REFERENCES TRACKER.COMMESSA(CODICE_COMMESSA),
		FOREIGN KEY (GIORNO_DI_RIFERIMENTO, MESE_DI_RIFERIMENTO, ANNO_DI_RIFERIMENTO, CODICE_PERSONA, CODICE_COMMESSA) REFERENCES TRACKER.TIME_SHEET_ENTRY(GIORNO_DI_RIFERIMENTO, MESE_DI_RIFERIMENTO, ANNO_DI_RIFERIMENTO, CODICE_PERSONA, CODICE_COMMESSA)
    );
	COMMENT ON TABLE TRACKER.NOTA_SPESE IS 'Questa tabella contiene le note spese.';
	COMMENT ON COLUMN TRACKER.NOTA_SPESE.TIPO_COSTO IS 'Aereo, Alloggio, Trasporti e carburante, Pasti, Conferenze e seminari, Kilometri, Rimborso Kilometrico, Spese varie';
	COMMENT ON COLUMN TRACKER.NOTA_SPESE.STATO_NOTA_SPESE IS 'I=Inserito , C=Confermato , V=Verificato';

   CREATE TABLE TRACKER.ORDINE_COMMESSA (
        CODICE_COMMESSA 		VARCHAR(50),
        NUMERO_ORDINE_CLIENTE	VARCHAR(50),
        PARTITA_IVA VARCHAR(50) NOT NULL,
        DATA_ORDINE 			DATE,
        IMPORTO_ORDINE 			NUMERIC(9,2),
        DATA_INIZIO 			DATE,
        DATA_FINE 				DATE,
        IMPORTO_RESIDUO 		NUMERIC(9,2),
		CREATE_TIMESTAMP 		TIMESTAMP, 
        LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
        CREATE_USER 			VARCHAR(50),
        LAST_UPDATE_USER 		VARCHAR(50),
        PRIMARY KEY	(CODICE_COMMESSA, NUMERO_ORDINE_CLIENTE),
		FOREIGN KEY (CODICE_COMMESSA) REFERENCES TRACKER.COMMESSA_FATTURABILE(CODICE_COMMESSA),
	   	FOREIGN KEY (PARTITA_IVA) REFERENCES TRACKER.ANAGRAFICA_CLIENTE(PARTITA_IVA)
    );
	COMMENT ON TABLE TRACKER.ORDINE_COMMESSA IS 'Questa tabella contiene i dati relativi all ordine delle commesse.';	


--FIGLIA DI ORDINI_COMMESSA
    CREATE TABLE TRACKER.DIPENDENTE_COMMESSA (
        CODICE_COMMESSA 		VARCHAR(50),
        CODICE_PERSONA 			VARCHAR(50),
        DATA_INIZIO_ALLOCAZIONE DATE,
        DATA_FINE_ALLOCAZIONE 	DATE,
        TARIFFA 				NUMERIC(6,2),
        GIORNI_PREVISTI 		NUMERIC(5,2),
        GIORNI_EROGATI 			NUMERIC(5,2),
        GIORNI_RESIDUI 			NUMERIC(5,2),
        IMPORTO_PREVISTO 		NUMERIC(9,2),
        IMPORTO_EROGATO 		NUMERIC(9,2),
        IMPORTO_RESIDUO 		NUMERIC(9,2),
		CREATE_TIMESTAMP 		TIMESTAMP, 
        LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
        CREATE_USER 			VARCHAR(50),
        LAST_UPDATE_USER 		VARCHAR(50),
        PRIMARY KEY	(CODICE_COMMESSA, CODICE_PERSONA),
		FOREIGN KEY (CODICE_PERSONA) REFERENCES TRACKER.UTENTE(CODICE_PERSONA),
		FOREIGN KEY (CODICE_COMMESSA) REFERENCES TRACKER.COMMESSA(CODICE_COMMESSA)
    );
	COMMENT ON TABLE TRACKER.DIPENDENTE_COMMESSA IS 'Questa tabella rappresenta la relazione N a N tra CODICE_PERSONA e CODICE_COMMESSA.';	

--FESTIVITA
	CREATE TABLE TRACKER.FESTIVITA (
	ID SERIAL PRIMARY KEY,
	DATA DATE,
	NOME_FESTIVO VARCHAR(60)
	);

--TIMESHEETMENSILE
	CREATE TABLE TRACKER.TIME_SHEET (
		ANNO_DI_RIFERIMENTO 	NUMERIC(4,0),
		MESE_DI_RIFERIMENTO 	NUMERIC(2,0),
		CODICE_PERSONA 			VARCHAR(50),
		ORE_TOTALI				NUMERIC (3,1),
		STATO_TIME_SHEET 		VARCHAR(50),
		CREATE_TIMESTAMP 		TIMESTAMP, 
		LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
		CREATE_USER 			VARCHAR(50),
		LAST_UPDATE_USER 		VARCHAR(50),
		PRIMARY KEY	(ANNO_DI_RIFERIMENTO, MESE_DI_RIFERIMENTO, CODICE_PERSONA),
		FOREIGN KEY (CODICE_PERSONA) REFERENCES TRACKER.UTENTE(CODICE_PERSONA)
	);
	COMMENT ON TABLE TRACKER.TIME_SHEET IS 'Questa tabella contiene il TIME_SHEET mensile per ogni dipendente.';
	COMMENT ON COLUMN TRACKER.TIME_SHEET.STATO_TIME_SHEET IS 'I=Inserito , C=Confermato , V=Verificato';
	
	
--TIMESHEETDATA
	CREATE TABLE TRACKER.TIME_SHEET_ENTRY (
		ANNO_DI_RIFERIMENTO 	NUMERIC(4,0),
		MESE_DI_RIFERIMENTO 	NUMERIC(2,0),
		CODICE_PERSONA 			VARCHAR(50),		
		CODICE_COMMESSA 		VARCHAR(10),
		GIORNO_DI_RIFERIMENTO 	NUMERIC(2,0),
		ORE 					NUMERIC(3,1),
		TRASFERTA 				BOOLEAN,
		CREATE_TIMESTAMP 		TIMESTAMP, 
		LAST_UPDATE_TIMESTAMP 	TIMESTAMP, 
		CREATE_USER 			VARCHAR(50),
		LAST_UPDATE_USER 		VARCHAR(50),
		PRIMARY KEY	(ANNO_DI_RIFERIMENTO, MESE_DI_RIFERIMENTO, CODICE_PERSONA, CODICE_COMMESSA, GIORNO_DI_RIFERIMENTO),
		FOREIGN KEY (CODICE_PERSONA, ANNO_DI_RIFERIMENTO, MESE_DI_RIFERIMENTO) REFERENCES TRACKER. TIME_SHEET(CODICE_PERSONA, ANNO_DI_RIFERIMENTO, MESE_DI_RIFERIMENTO),
		FOREIGN KEY (CODICE_COMMESSA) REFERENCES TRACKER.COMMESSA(CODICE_COMMESSA)
	);
	
	
-- RUOLO
	CREATE TABLE TRACKER.RUOLI(
        RUOLO 				VARCHAR(50) PRIMARY KEY,
        DESCRIZIONE_RUOLO 	VARCHAR(50)
    );
	COMMENT ON TABLE TRACKER.RUOLI IS 'Questa tabella contiene i ruoli che determinano la visibilità dei dati del database.';
	COMMENT ON COLUMN TRACKER.RUOLI.RUOLO IS 'M=Management, A=Amministrazione , H=HR , D=Dipendente , C=Consulente , R=Referente/Capo progetto , X=Admin , S=Sales';


    CREATE TABLE TRACKER.UTENTE_RUOLO(
        CODICE_PERSONA 	VARCHAR(50),
        RUOLO 			VARCHAR(50),
        PRIMARY KEY (CODICE_PERSONA, RUOLO),
		FOREIGN KEY (CODICE_PERSONA) REFERENCES TRACKER.UTENTE(CODICE_PERSONA),
		FOREIGN KEY (RUOLO) REFERENCES TRACKER.RUOLI(RUOLO)
    );
	COMMENT ON TABLE TRACKER.UTENTE_RUOLO IS 'Questa tabella permette una relazione N a N tra UTENTE e RUOLO.';
	COMMENT ON COLUMN TRACKER.UTENTE_RUOLO.RUOLO IS 'M=Management, A=Amministrazione , H=HR , D=Dipendente , C=Consulente , R=Referente/Capo progetto , X=Admin , S=Sales';
-- TRIGGERS

	CREATE TABLE IF NOT EXISTS tracker.gruppo
(
    id_gruppo integer NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    nome character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT gruppo_pkey PRIMARY KEY (id_gruppo),
    CONSTRAINT gruppo_nome_key UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS tracker.gruppo_contatto
(
    id_contatto integer NOT NULL,
    id_gruppo integer NOT NULL,
    CONSTRAINT gruppo_contatto_pkey PRIMARY KEY (id_contatto, id_gruppo),
    CONSTRAINT fkij6c22g0j8w4b33ev80v96mfn FOREIGN KEY (id_contatto)
        REFERENCES tracker.contatto (id_contatto) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fksdjcnyaciahnmdeg0nf94sqsv FOREIGN KEY (id_gruppo)
        REFERENCES tracker.gruppo (id_gruppo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS tracker.contatto
(
    id_contatto integer NOT NULL,
    cellulare character varying(255) COLLATE pg_catalog."default",
    codice_fiscale character varying(255) COLLATE pg_catalog."default",
    cognome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    comune_di_domicilio character varying(255) COLLATE pg_catalog."default",
    indirizzo_di_domicilio character varying(255) COLLATE pg_catalog."default",
    mail_aziendale character varying(255) COLLATE pg_catalog."default",
    mail_privata character varying(255) COLLATE pg_catalog."default",
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    provincia_di_domicilio character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT contatto_pkey PRIMARY KEY (id_contatto),
    CONSTRAINT contatto_cellulare_key UNIQUE (cellulare),
    CONSTRAINT contatto_codice_fiscale_key UNIQUE (codice_fiscale),
    CONSTRAINT contatto_mail_aziendale_key UNIQUE (mail_aziendale),
    CONSTRAINT contatto_mail_privata_key UNIQUE (mail_privata)
);

	-- FUNZIONE PER GENERARE IL CREATE_TIMESTAMP E LAST_UPDATE_TIMESTAMP
	CREATE FUNCTION TRACKER.SET_CREATE_UPDATE_TIMESTAMP() RETURNS trigger AS $$
	DECLARE
		time_stamp TIMESTAMP WITHOUT TIME ZONE;
		BEGIN
			time_stamp := now();
			NEW.CREATE_TIMESTAMP := time_stamp;
			NEW.LAST_UPDATE_TIMESTAMP := time_stamp;
			RETURN NEW;
		END;
	$$ LANGUAGE plpgsql;
	COMMENT ON FUNCTION TRACKER.SET_CREATE_UPDATE_TIMESTAMP() IS 'Questa funzione ritorna un CREATE_TIMESTAMP e LAST_UPDATE_TIMESTAMP inizializzati all ora attuale';


	-- APPLICO IL TRIGGER PER INSERIRE CREATE_TIMESTAMP E LAST_UPDATE_TIMESTAMP A TUTTE LE TABELLE CON LA COLONNA CREATE_TIMESTAMP
	DO $$
	DECLARE
		t text;
	BEGIN
		FOR t IN 
			SELECT table_name FROM information_schema.columns
			WHERE upper(column_name) = 'CREATE_TIMESTAMP'
		LOOP
			EXECUTE format('CREATE TRIGGER SET_CREATE_UPDATE_TIMESTAMP
							BEFORE INSERT ON %I
							FOR EACH ROW EXECUTE PROCEDURE SET_CREATE_UPDATE_TIMESTAMP()',
							t);
		END LOOP;
	END;
	$$ LANGUAGE plpgsql;


	-- FUNZIONE PER GENERARE IL LAST_UPDATE_TIMESTAMP
	CREATE FUNCTION TRACKER.SET_UPDATE_TIMESTAMP() RETURNS trigger AS $$
	DECLARE
		BEGIN
			NEW.LAST_UPDATE_TIMESTAMP := now();
			RETURN NEW;
		END;
	$$ LANGUAGE plpgsql;
	COMMENT ON FUNCTION TRACKER.SET_UPDATE_TIMESTAMP() IS 'Questa funzione ritorna il LAST_UPDATE_TIMESTAMP inizializzato all ora attuale';

	-- APPLICO IL TRIGGER PER AGGIORNARE IL LAST_UPDATE_TIMESTAMP A TUTTE LE TABELLE CON LA COLONNA LAST_UPDATE_TIMESTAMP
	DO $$
	DECLARE
		t text;
	BEGIN
		FOR t IN 
			SELECT table_name FROM information_schema.columns
			WHERE upper(column_name) = 'LAST_UPDATE_TIMESTAMP'
		LOOP
			EXECUTE format('CREATE TRIGGER SET_UPDATE_TIMESTAMP
							BEFORE UPDATE ON %I
							FOR EACH ROW EXECUTE PROCEDURE SET_UPDATE_TIMESTAMP()',
							t);
		END LOOP;
	END;
	$$ LANGUAGE plpgsql;


INSERT INTO TRACKER.CLIENTE(CODICE_AZIENDA, PARTITA_IVA, RAGIONE_SOCIALE, CODICE_FISCALE) VALUES ('10233860963','10233860963', 'PERIGEA SRL', '10233860963');

-- INSERT COMMESSA NF
INSERT INTO TRACKER.COMMESSA(CODICE_COMMESSA,TIPO_COMMESSA, PARTITA_IVA)
VALUES  ('Ferie','NF', '10233860963'),
		('Malattia','NF', '10233860963'),
		('Permessi','NF', '10233860963'),
		('Festivita','NF', '10233860963'),
		('Maternita','NF', '10233860963'),
		('Paternita','NF', '10233860963'),
		('Formazione','NF', '10233860963'),
		('Legge 104','NF', '10233860963'),
		('Lutto','NF', '10233860963'),
		('Rip. Comp.','NF', '10233860963'),
		('Patrono','NF', '10233860963'),
		('Supp Vend','NF', '10233860963'),
		('Lav Uff.','NF', '10233860963');

INSERT INTO TRACKER.FESTIVITA(DATA,NOME_FESTIVO)
	VALUES ('01/01/2022','PRIMO_DELL_ANNO'),
			('06/01/2022','EPIFANIA'),
			('17/04/2022','PASQUA'),
			('18/04/2022','PASQUETTA'),
			('25/04/2022','FESTA_DELLA_LIBERAZIONE'),
			('01/05/2022','FESTA_DEI_LAVORATORI'),
			('02/06/2022','FESTA_DELLA_REPUBBLICA'),
			('15/08/2022','FERRAGOSTO'),
			('01/11/2022','TUTTI_I_SANTI'),
			('07/12/2022','SANT_AMBROGIO'),
			('08/12/2022','IMMACOLTA'),
			('25/12/2022','NATALE'),
			('26/12/2022','SANTO_STEFANO');
			
			
insert into TRACKER.RUOLI(RUOLO, DESCRIZIONE_RUOLO) VALUES
('M', 'Management'),
('A', 'Amministrazione'),
('H', 'HR'),
('D', 'Dipendente'),
('C', 'Consulente'),
('R', 'Referente/Capo progetto'),
('X', 'Admin'),
('P', 'Contatto'),
('S', 'Sales');

/*
 * 
 *INSERT INTO TRACKER.UTENTE (CODICE_PERSONA,NOME,COGNOME,CODICE_RESPONSABILE) VALUES ('01','FABIO','DE_LORENZO',null);
*/