--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1 (Debian 14.1-1.pgdg110+1)
-- Dumped by pg_dump version 14.1

-- Started on 2022-02-01 09:12:05 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE tracker;
--
-- TOC entry 3487 (class 1262 OID 16633)
-- Name: tracker; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE tracker WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE tracker OWNER TO postgres;

\connect tracker

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 16634)
-- Name: tracker; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA tracker;


ALTER SCHEMA tracker OWNER TO postgres;

--
-- TOC entry 853 (class 1247 OID 16656)
-- Name: ruolo_type; Type: TYPE; Schema: tracker; Owner: postgres
--

CREATE TYPE tracker.ruolo_type AS ENUM (
    'M',
    'A',
    'H',
    'D',
    'C',
    'R',
    'X',
    'S'
);


ALTER TYPE tracker.ruolo_type OWNER TO postgres;

--
-- TOC entry 3488 (class 0 OID 0)
-- Dependencies: 853
-- Name: TYPE ruolo_type; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TYPE tracker.ruolo_type IS 'M=Management, A=Amministrazione , H=HR , D=Dipendente , C=Consulente , R=Referente/Capo progetto , X=Admin , S=Sales';


--
-- TOC entry 844 (class 1247 OID 16637)
-- Name: stato_type; Type: TYPE; Schema: tracker; Owner: postgres
--

CREATE TYPE tracker.stato_type AS ENUM (
    'I',
    'C',
    'V'
);


ALTER TYPE tracker.stato_type OWNER TO postgres;

--
-- TOC entry 3489 (class 0 OID 0)
-- Dependencies: 844
-- Name: TYPE stato_type; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TYPE tracker.stato_type IS 'I=Inserito , C=Confermato , V=Verificato';


--
-- TOC entry 847 (class 1247 OID 16644)
-- Name: stato_utente_type; Type: TYPE; Schema: tracker; Owner: postgres
--

CREATE TYPE tracker.stato_utente_type AS ENUM (
    'A',
    'C'
);


ALTER TYPE tracker.stato_utente_type OWNER TO postgres;

--
-- TOC entry 3490 (class 0 OID 0)
-- Dependencies: 847
-- Name: TYPE stato_utente_type; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TYPE tracker.stato_utente_type IS 'A=Attivo, C=Cessato';


--
-- TOC entry 859 (class 1247 OID 16692)
-- Name: tipo_commessa_fatturabile_type; Type: TYPE; Schema: tracker; Owner: postgres
--

CREATE TYPE tracker.tipo_commessa_fatturabile_type AS ENUM (
    'TM',
    'TK',
    'AM'
);


ALTER TYPE tracker.tipo_commessa_fatturabile_type OWNER TO postgres;

--
-- TOC entry 3491 (class 0 OID 0)
-- Dependencies: 859
-- Name: TYPE tipo_commessa_fatturabile_type; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TYPE tracker.tipo_commessa_fatturabile_type IS 'TM, TK, AM';


--
-- TOC entry 850 (class 1247 OID 16650)
-- Name: tipo_commessa_type; Type: TYPE; Schema: tracker; Owner: postgres
--

CREATE TYPE tracker.tipo_commessa_type AS ENUM (
    'F',
    'NF'
);


ALTER TYPE tracker.tipo_commessa_type OWNER TO postgres;

--
-- TOC entry 3492 (class 0 OID 0)
-- Dependencies: 850
-- Name: TYPE tipo_commessa_type; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TYPE tracker.tipo_commessa_type IS 'F, NF';


--
-- TOC entry 862 (class 1247 OID 16700)
-- Name: tipo_costo_nota_spese_type; Type: TYPE; Schema: tracker; Owner: postgres
--

CREATE TYPE tracker.tipo_costo_nota_spese_type AS ENUM (
    'Aereo',
    'Alloggio',
    'Trasporti_e_carburante',
    'Pasti',
    'Conferenze_e_seminari',
    'Kilometri',
    'Rimborso_Kilometrico',
    'Spese_varie'
);


ALTER TYPE tracker.tipo_costo_nota_spese_type OWNER TO postgres;

--
-- TOC entry 3493 (class 0 OID 0)
-- Dependencies: 862
-- Name: TYPE tipo_costo_nota_spese_type; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TYPE tracker.tipo_costo_nota_spese_type IS 'Aereo, Alloggio, Trasporti e carburante, Pasti, Conferenze e seminari, Kilometri, Rimborso Kilometrico, Spese varie';


--
-- TOC entry 856 (class 1247 OID 16674)
-- Name: tipologia_pagamento_type; Type: TYPE; Schema: tracker; Owner: postgres
--

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


ALTER TYPE tracker.tipologia_pagamento_type OWNER TO postgres;

--
-- TOC entry 3494 (class 0 OID 0)
-- Dependencies: 856
-- Name: TYPE tipologia_pagamento_type; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TYPE tracker.tipologia_pagamento_type IS '30GG DF, 30GG FMDF, 60GG DF, 60GG FMDF, 90GG DF, 90GG FMDF, 120GG DF, 120GG FMDF';


--
-- TOC entry 230 (class 1255 OID 16863)
-- Name: set_create_update_timestamp(); Type: FUNCTION; Schema: tracker; Owner: postgres
--

CREATE FUNCTION tracker.set_create_update_timestamp() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
	DECLARE
		time_stamp TIMESTAMP WITHOUT TIME ZONE;
		BEGIN
			time_stamp := now();
			NEW.CREATE_TIMESTAMP := time_stamp;
			NEW.LAST_UPDATE_TIMESTAMP := time_stamp;
			RETURN NEW;
		END;
	$$;


ALTER FUNCTION tracker.set_create_update_timestamp() OWNER TO postgres;

--
-- TOC entry 3495 (class 0 OID 0)
-- Dependencies: 230
-- Name: FUNCTION set_create_update_timestamp(); Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON FUNCTION tracker.set_create_update_timestamp() IS 'Questa funzione ritorna un CREATE_TIMESTAMP e LAST_UPDATE_TIMESTAMP inizializzati all ora attuale';


--
-- TOC entry 231 (class 1255 OID 16873)
-- Name: set_update_timestamp(); Type: FUNCTION; Schema: tracker; Owner: postgres
--

CREATE FUNCTION tracker.set_update_timestamp() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
	DECLARE
		BEGIN
			NEW.LAST_UPDATE_TIMESTAMP := now();
			RETURN NEW;
		END;
	$$;


ALTER FUNCTION tracker.set_update_timestamp() OWNER TO postgres;

--
-- TOC entry 3496 (class 0 OID 0)
-- Dependencies: 231
-- Name: FUNCTION set_update_timestamp(); Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON FUNCTION tracker.set_update_timestamp() IS 'Questa funzione ritorna il LAST_UPDATE_TIMESTAMP inizializzato all ora attuale';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 226 (class 1259 OID 90729)
-- Name: anagrafica_persona; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.anagrafica_persona (
    codice_persona character varying(255) NOT NULL,
    cellulare character varying(255),
    cellulare_contatto_emergenza character varying(255),
    codice_fiscale character varying(255),
    cognome character varying(255) NOT NULL,
    comune_di_domicilio character varying(255),
    comune_di_residenza character varying(255),
    data_di_nascita date,
    iban character varying(255),
    indirizzo_di_domicilio character varying(255),
    indirizzo_di_residenza character varying(255),
    luogo_di_nascita character varying(255),
    mail_aziendale character varying(255),
    mail_privata character varying(255),
    nome character varying(255) NOT NULL,
    nome_contatto_emergenza character varying(255),
    provincia_di_domicilio character varying(255),
    provincia_di_residenza character varying(255),
    tipo character varying(255) NOT NULL,
    codice_azienda character varying(255)
);


ALTER TABLE tracker.anagrafica_persona OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 90736)
-- Name: azienda; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.azienda (
    tipo character varying(31) NOT NULL,
    codice_azienda character varying(255) NOT NULL,
    acronimo_cliente character varying(255),
    codice_destinatario character varying(255),
    codice_fiscale character varying(255),
    note_per_la_fatturazione character varying(255),
    partita_iva character varying(255) NOT NULL,
    progressivo_per_commesse integer,
    ragione_sociale character varying(255),
    sede_legale_cap character varying(255),
    sede_legale_comune character varying(255),
    sede_legale_indirizzo character varying(255),
    sede_operativa_cap character varying(255),
    sede_operativa_comune character varying(255),
    sede_operativa_indirizzo character varying(255),
    tipologia_di_pagamento character varying(255)
);


ALTER TABLE tracker.azienda OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 82550)
-- Name: commessa; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.commessa (
    tipo_commessa character varying(31) NOT NULL,
    codice_commessa character varying(255) NOT NULL,
    descrizione_commessa character varying(255),
    data_fine_commessa date,
    data_inizio_commessa date,
    descrizione_commessa_cliente character varying(255),
    importo_commessa_iniziale_presunto double precision,
    margine_da_inizio_anno double precision,
    margine_da_inizio_commessa double precision,
    margine_iniziale double precision,
    ordine_interno_corrente double precision,
    percentuale_avanzamento_costi double precision,
    percentuale_avanzamento_fatturazione double precision,
    percentuale_sconto double precision,
    responsabile_commerciale character varying(255),
    tipo_commessa_fatturabile character varying(255),
    totale_costi_da_inizio_anno double precision,
    totale_costi_da_inizio_commessa double precision,
    totale_estensioni double precision,
    totale_fatturato_da_inizio_anno double precision,
    totale_fatturato_da_inizio_commessa double precision,
    totale_ordine double precision,
    totale_ordine_cliente_formale double precision,
    totale_ricavi_da_inizio_anno double precision,
    totale_ricavi_da_inizio_commessa double precision,
    codice_azienda character varying(255) NOT NULL
);


ALTER TABLE tracker.commessa OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 90743)
-- Name: consulente; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.consulente (
    codice_persona character varying(255) NOT NULL,
    cellulare character varying(255),
    cellulare_contatto_emergenza character varying(255),
    codice_fiscale character varying(255),
    cognome character varying(255) NOT NULL,
    comune_di_domicilio character varying(255),
    comune_di_residenza character varying(255),
    data_di_nascita date,
    iban character varying(255),
    indirizzo_di_domicilio character varying(255),
    indirizzo_di_residenza character varying(255),
    luogo_di_nascita character varying(255),
    mail_aziendale character varying(255),
    mail_privata character varying(255),
    nome character varying(255) NOT NULL,
    nome_contatto_emergenza character varying(255),
    provincia_di_domicilio character varying(255),
    provincia_di_residenza character varying(255),
    tipo character varying(255) NOT NULL,
    costo numeric(19,2),
    data_assunzione date,
    data_cessazione date,
    partita_iva character varying(255),
    codice_azienda character varying(255)
);


ALTER TABLE tracker.consulente OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 82653)
-- Name: dati_economici_consulente; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.dati_economici_consulente (
    codice_persona character varying(20) NOT NULL,
    data_ingaggio date NOT NULL,
    decorrenza_assegnazione_centro_di_costo date NOT NULL,
    tipo_ingaggio character varying(2) NOT NULL,
    costo_giornaliero numeric(6,2),
    codice_centro_di_costo character varying(20),
    create_timestamp timestamp without time zone,
    last_update_timestamp timestamp without time zone,
    create_user character varying(20),
    last_update_user character varying(20)
);


ALTER TABLE tracker.dati_economici_consulente OWNER TO postgres;

--
-- TOC entry 3501 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE dati_economici_consulente; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TABLE tracker.dati_economici_consulente IS 'Questa tabella contiene i dati economici dei consulenti.';


--
-- TOC entry 3502 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN dati_economici_consulente.tipo_ingaggio; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.dati_economici_consulente.tipo_ingaggio IS 'F=Free lancer a Partita Iva , S=Società';


--
-- TOC entry 224 (class 1259 OID 82643)
-- Name: dati_economici_dipendente; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.dati_economici_dipendente (
    codice_persona character varying(20) NOT NULL,
    livello_iniziale character varying(20),
    tipo_contratto_iniziale character varying(20),
    ral_iniziale numeric(7,0),
    ral_attuale numeric(7,0),
    decorrenza_ral_attuale date,
    data_assegnazione_ticket date,
    rimborso_giornaliero numeric(4,2),
    decorrenza_rimborso date,
    livello_attuale character varying(20),
    decorrenza_livello date,
    tipo_contratto_attuale character varying(20),
    job_title character varying(20),
    scelta_tredicesima character varying(1),
    ultimo_premio numeric(5,0),
    data_ultimo_bonus date,
    modello_auto character varying(20),
    rimborso_per_km numeric(4,2),
    km_per_giorno numeric(4,1),
    costo_giornaliero numeric(6,2),
    data_decorrenza_costo date,
    codice_centro_di_costo character varying(20),
    decorrenza_assegnazione_centro_costo date,
    create_timestamp timestamp without time zone,
    last_update_timestamp timestamp without time zone,
    create_user character varying(20),
    last_update_user character varying(20),
    data_ultimo_premio date,
    decorrenza_assegnazione_centro_di_costo date,
    kmpergiorno real,
    rimbors_giornaliero real
);


ALTER TABLE tracker.dati_economici_dipendente OWNER TO postgres;

--
-- TOC entry 3504 (class 0 OID 0)
-- Dependencies: 224
-- Name: TABLE dati_economici_dipendente; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TABLE tracker.dati_economici_dipendente IS 'Questa tabella contiene i dati economici relativi ad ogni dipendente.';


--
-- TOC entry 3505 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dati_economici_dipendente.livello_iniziale; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.dati_economici_dipendente.livello_iniziale IS '1, 2, 3, 4, 5, 5S, 6, 7, 7Q, D';


--
-- TOC entry 3506 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dati_economici_dipendente.tipo_contratto_iniziale; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.dati_economici_dipendente.tipo_contratto_iniziale IS 'Stage, Apprendistato, Determinato, Indeterminato';


--
-- TOC entry 3507 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dati_economici_dipendente.livello_attuale; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.dati_economici_dipendente.livello_attuale IS '1, 2, 3, 4, 5, 5S, 6, 7, 7Q, D';


--
-- TOC entry 3508 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dati_economici_dipendente.tipo_contratto_attuale; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.dati_economici_dipendente.tipo_contratto_attuale IS 'Stage, Apprendistato, Determinato, Indeterminato';


--
-- TOC entry 3509 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dati_economici_dipendente.job_title; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.dati_economici_dipendente.job_title IS 'Junior Prof, Professional, Senior Prof, Expert, Manager, Senior Manager, Director';


--
-- TOC entry 3510 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dati_economici_dipendente.scelta_tredicesima; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.dati_economici_dipendente.scelta_tredicesima IS 'M=Mensile, A=Annuale';


--
-- TOC entry 229 (class 1259 OID 90750)
-- Name: dipendente; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.dipendente (
    codice_persona character varying(255) NOT NULL,
    cellulare character varying(255),
    cellulare_contatto_emergenza character varying(255),
    codice_fiscale character varying(255),
    cognome character varying(255) NOT NULL,
    comune_di_domicilio character varying(255),
    comune_di_residenza character varying(255),
    data_di_nascita date,
    iban character varying(255),
    indirizzo_di_domicilio character varying(255),
    indirizzo_di_residenza character varying(255),
    luogo_di_nascita character varying(255),
    mail_aziendale character varying(255),
    mail_privata character varying(255),
    nome character varying(255) NOT NULL,
    nome_contatto_emergenza character varying(255),
    provincia_di_domicilio character varying(255),
    provincia_di_residenza character varying(255),
    tipo character varying(255) NOT NULL,
    data_assunzione date,
    data_cessazione date,
    codice_azienda character varying(255)
);


ALTER TABLE tracker.dipendente OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 82557)
-- Name: dipendente_commessa; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.dipendente_commessa (
    codice_commessa character varying(255) NOT NULL,
    codice_persona character varying(255) NOT NULL,
    data_fine_allocazione date,
    data_inizio_allocazione date,
    giorni_erogati integer,
    giorni_previsti integer,
    giorni_residui integer,
    importo_erogato double precision,
    importo_previsto double precision,
    importo_residuo double precision,
    tariffa double precision
);


ALTER TABLE tracker.dipendente_commessa OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16822)
-- Name: festivita; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.festivita (
    id integer NOT NULL,
    data date,
    nome_festivo character varying(60)
);


ALTER TABLE tracker.festivita OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16821)
-- Name: festivita_id_seq; Type: SEQUENCE; Schema: tracker; Owner: postgres
--

CREATE SEQUENCE tracker.festivita_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tracker.festivita_id_seq OWNER TO postgres;

--
-- TOC entry 3515 (class 0 OID 0)
-- Dependencies: 212
-- Name: festivita_id_seq; Type: SEQUENCE OWNED BY; Schema: tracker; Owner: postgres
--

ALTER SEQUENCE tracker.festivita_id_seq OWNED BY tracker.festivita.id;


--
-- TOC entry 217 (class 1259 OID 66081)
-- Name: gruppo; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.gruppo (
    codice_gruppo bigint NOT NULL,
    descrizione character varying(255),
    nome character varying(255) NOT NULL
);


ALTER TABLE tracker.gruppo OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 66088)
-- Name: gruppo_contatto; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.gruppo_contatto (
    codice_gruppo bigint NOT NULL,
    codice_persona character varying(255) NOT NULL
);


ALTER TABLE tracker.gruppo_contatto OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 57874)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: tracker; Owner: postgres
--

CREATE SEQUENCE tracker.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tracker.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16776)
-- Name: nota_spese; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.nota_spese (
    anno_di_riferimento integer NOT NULL,
    mese_di_riferimento integer NOT NULL,
    codice_persona character varying(50) NOT NULL,
    codice_commessa character varying(50) NOT NULL,
    giorno_di_riferimento integer NOT NULL,
    tipo_costo character varying(50) NOT NULL,
    importo numeric(7,2),
    stato_nota_spese character varying(50),
    create_timestamp timestamp without time zone,
    last_update_timestamp timestamp without time zone,
    create_user character varying(50),
    last_update_user character varying(50)
);


ALTER TABLE tracker.nota_spese OWNER TO postgres;

--
-- TOC entry 3518 (class 0 OID 0)
-- Dependencies: 211
-- Name: TABLE nota_spese; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TABLE tracker.nota_spese IS 'Questa tabella contiene le note spese.';


--
-- TOC entry 3519 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN nota_spese.tipo_costo; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.nota_spese.tipo_costo IS 'Aereo, Alloggio, Trasporti e carburante, Pasti, Conferenze e seminari, Kilometri, Rimborso Kilometrico, Spese varie';


--
-- TOC entry 3520 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN nota_spese.stato_nota_spese; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.nota_spese.stato_nota_spese IS 'I=Inserito , C=Confermato , V=Verificato';


--
-- TOC entry 223 (class 1259 OID 82564)
-- Name: ordine_commessa; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.ordine_commessa (
    codice_azienda character varying(255) NOT NULL,
    codice_commessa character varying(255) NOT NULL,
    numero_ordine_cliente character varying(255) NOT NULL,
    data_fine date,
    data_inizio date,
    data_ordine date,
    importo_ordine double precision,
    importo_residuo double precision
);


ALTER TABLE tracker.ordine_commessa OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 82476)
-- Name: ruoli; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.ruoli (
    id character varying(255) NOT NULL,
    descrizione_ruolo character varying(255)
);


ALTER TABLE tracker.ruoli OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 41488)
-- Name: time_sheet; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.time_sheet (
    anno_di_riferimento integer NOT NULL,
    codice_persona character varying(255) NOT NULL,
    mese_di_riferimento integer NOT NULL,
    ore_totali integer,
    stato_time_sheet character varying(255)
);


ALTER TABLE tracker.time_sheet OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 41471)
-- Name: time_sheet_entry; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.time_sheet_entry (
    anno_di_riferimento integer NOT NULL,
    codice_commessa character varying(255) NOT NULL,
    codice_persona character varying(255) NOT NULL,
    giorno_di_riferimento integer NOT NULL,
    mese_di_riferimento integer NOT NULL,
    ore integer,
    tipo_commessa character varying(255),
    trasferta boolean
);


ALTER TABLE tracker.time_sheet_entry OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16717)
-- Name: utente; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.utente (
    codice_persona character varying(50) NOT NULL,
    password character varying(50),
    stato_utente character varying(50),
    create_timestamp timestamp without time zone,
    last_update_timestamp timestamp without time zone,
    create_user character varying(50),
    last_update_user character varying(50),
    codice_responsabile character varying(50)
);


ALTER TABLE tracker.utente OWNER TO postgres;

--
-- TOC entry 3526 (class 0 OID 0)
-- Dependencies: 210
-- Name: TABLE utente; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON TABLE tracker.utente IS 'Questa tabella contiene gli utenti di cui si vuole tenere traccia.';


--
-- TOC entry 3527 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN utente.stato_utente; Type: COMMENT; Schema: tracker; Owner: postgres
--

COMMENT ON COLUMN tracker.utente.stato_utente IS 'A=Attivo, C=Cessato';


--
-- TOC entry 220 (class 1259 OID 82498)
-- Name: utente_ruolo; Type: TABLE; Schema: tracker; Owner: postgres
--

CREATE TABLE tracker.utente_ruolo (
    codice_persona character varying(255) NOT NULL,
    id character varying(255) NOT NULL
);


ALTER TABLE tracker.utente_ruolo OWNER TO postgres;

--
-- TOC entry 3261 (class 2604 OID 16825)
-- Name: festivita id; Type: DEFAULT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.festivita ALTER COLUMN id SET DEFAULT nextval('tracker.festivita_id_seq'::regclass);


--
-- TOC entry 3478 (class 0 OID 90729)
-- Dependencies: 226
-- Data for Name: anagrafica_persona; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.anagrafica_persona VALUES ('bfef6e8c-1ad3-48ab-aaa7-24b6864218d0', '342342', 'string', 'werwrwer', 'cont', 'string', 'string', '2022-01-31', 'string', 'string', 'string', 'string', 'string', 'string', 'tatto', 'string', 'string', 'string', 'C', NULL);


--
-- TOC entry 3479 (class 0 OID 90736)
-- Dependencies: 227
-- Data for Name: azienda; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.azienda VALUES ('F', '3485398739', 'Forni Tore srl', 'string', 'string', 'string', 'string', 0, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'A');
INSERT INTO tracker.azienda VALUES ('C', '23094234', 'Pippo SRL', 'string', 'string', 'string', 'string', 0, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'A');
INSERT INTO tracker.azienda VALUES ('C', '111111', NULL, NULL, NULL, NULL, '111111', NULL, 'ragione1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO tracker.azienda VALUES ('C', '222222', NULL, NULL, NULL, NULL, '222222', NULL, 'ragione2', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO tracker.azienda VALUES ('C', '333333', NULL, NULL, NULL, NULL, '333333', NULL, 'ragione3', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO tracker.azienda VALUES ('C', '444444', NULL, NULL, NULL, NULL, '444444', NULL, 'ragione4', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO tracker.azienda VALUES ('C', '555555', NULL, NULL, NULL, NULL, '555555', NULL, 'ragione5', NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 3473 (class 0 OID 82550)
-- Dependencies: 221
-- Data for Name: commessa; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.commessa VALUES ('F', '111', 'descrizione1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '111111');
INSERT INTO tracker.commessa VALUES ('F', '222', 'descrizione2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '222222');
INSERT INTO tracker.commessa VALUES ('F', '333', 'descrizione3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '333333');
INSERT INTO tracker.commessa VALUES ('F', '444', 'descrizione4', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '444444');
INSERT INTO tracker.commessa VALUES ('F', '555', 'descrizione5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '555555');


--
-- TOC entry 3480 (class 0 OID 90743)
-- Dependencies: 228
-- Data for Name: consulente; Type: TABLE DATA; Schema: tracker; Owner: postgres
--



--
-- TOC entry 3477 (class 0 OID 82653)
-- Dependencies: 225
-- Data for Name: dati_economici_consulente; Type: TABLE DATA; Schema: tracker; Owner: postgres
--



--
-- TOC entry 3476 (class 0 OID 82643)
-- Dependencies: 224
-- Data for Name: dati_economici_dipendente; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.dati_economici_dipendente VALUES ('01', 'LIVELLO_1', 'Stage', 0, 0, '2022-01-31', '2022-01-31', NULL, '2022-01-31', '0', '2022-01-31', '0', '0', '0', 0, NULL, 'string', 0.00, NULL, 0.00, '2022-01-31', 'string', NULL, NULL, NULL, NULL, NULL, '2022-01-31', '2022-01-31', 0, 0);


--
-- TOC entry 3481 (class 0 OID 90750)
-- Dependencies: 229
-- Data for Name: dipendente; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.dipendente VALUES ('01', 'string', 'string', 'string', 'de lorenzo', 'string', 'string', '2022-01-31', 'er22r23', 'string', 'string', 'string', 'string', 'string', 'fabio', 'string', 'string', 'string', 'E', '2022-01-31', '2022-01-31', NULL);


--
-- TOC entry 3474 (class 0 OID 82557)
-- Dependencies: 222
-- Data for Name: dipendente_commessa; Type: TABLE DATA; Schema: tracker; Owner: postgres
--



--
-- TOC entry 3465 (class 0 OID 16822)
-- Dependencies: 213
-- Data for Name: festivita; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.festivita VALUES (1, '2022-01-01', 'PRIMO_DELL_ANNO');
INSERT INTO tracker.festivita VALUES (2, '2022-01-06', 'EPIFANIA');
INSERT INTO tracker.festivita VALUES (3, '2022-04-17', 'PASQUA');
INSERT INTO tracker.festivita VALUES (4, '2022-04-25', 'FESTA_DELLA_LIBERAZIONE');
INSERT INTO tracker.festivita VALUES (5, '2022-05-01', 'FESTA_DEI_LAVORATORI');
INSERT INTO tracker.festivita VALUES (6, '2022-06-02', 'FESTA_DELLA_REPUBBLICA');
INSERT INTO tracker.festivita VALUES (7, '2022-08-15', 'FERRAGOSTO');
INSERT INTO tracker.festivita VALUES (8, '2022-11-01', 'TUTTI_I_SANTI');
INSERT INTO tracker.festivita VALUES (9, '2022-12-07', 'SANT_AMBROGIO');
INSERT INTO tracker.festivita VALUES (10, '2022-12-08', 'IMMACOLATA');
INSERT INTO tracker.festivita VALUES (11, '2022-12-25', 'NATALE');
INSERT INTO tracker.festivita VALUES (12, '2022-12-26', 'SANTO_STEFANO');
INSERT INTO tracker.festivita VALUES (13, '2022-04-18', 'PASQUETTA');


--
-- TOC entry 3469 (class 0 OID 66081)
-- Dependencies: 217
-- Data for Name: gruppo; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.gruppo VALUES (21, 'TUTORS', 'TUTORS');


--
-- TOC entry 3470 (class 0 OID 66088)
-- Dependencies: 218
-- Data for Name: gruppo_contatto; Type: TABLE DATA; Schema: tracker; Owner: postgres
--



--
-- TOC entry 3463 (class 0 OID 16776)
-- Dependencies: 211
-- Data for Name: nota_spese; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.nota_spese VALUES (2022, 1, '01', '111', 3, 'AEREO', 10.00, NULL, '2022-01-31 18:29:48.60585', '2022-01-31 18:29:48.60585', NULL, NULL);
INSERT INTO tracker.nota_spese VALUES (2022, 1, '01', '111', 4, 'PASTI', 20.00, NULL, '2022-01-31 18:29:48.60585', '2022-01-31 18:29:48.60585', NULL, NULL);
INSERT INTO tracker.nota_spese VALUES (2022, 1, '01', '222', 5, 'ALLOGGIO', 30.00, NULL, '2022-01-31 18:29:48.60585', '2022-01-31 18:29:48.60585', NULL, NULL);


--
-- TOC entry 3475 (class 0 OID 82564)
-- Dependencies: 223
-- Data for Name: ordine_commessa; Type: TABLE DATA; Schema: tracker; Owner: postgres
--



--
-- TOC entry 3471 (class 0 OID 82476)
-- Dependencies: 219
-- Data for Name: ruoli; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.ruoli VALUES ('M', 'string');
INSERT INTO tracker.ruoli VALUES ('A', 'Amministrazione');
INSERT INTO tracker.ruoli VALUES ('H', 'HR');
INSERT INTO tracker.ruoli VALUES ('D', 'Dipendente');
INSERT INTO tracker.ruoli VALUES ('C', 'Consulente');
INSERT INTO tracker.ruoli VALUES ('R', 'Referente/Capo progetto');
INSERT INTO tracker.ruoli VALUES ('X', 'Admin');
INSERT INTO tracker.ruoli VALUES ('S', 'Sales');
INSERT INTO tracker.ruoli VALUES ('P', 'Contatto');


--
-- TOC entry 3467 (class 0 OID 41488)
-- Dependencies: 215
-- Data for Name: time_sheet; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.time_sheet VALUES (2022, '01', 1, 24, 'I');


--
-- TOC entry 3466 (class 0 OID 41471)
-- Dependencies: 214
-- Data for Name: time_sheet_entry; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.time_sheet_entry VALUES (2022, '111', '01', 3, 1, 8, 'F', true);
INSERT INTO tracker.time_sheet_entry VALUES (2022, '111', '01', 4, 1, 8, 'F', true);
INSERT INTO tracker.time_sheet_entry VALUES (2022, '222', '01', 5, 1, 8, 'F', true);


--
-- TOC entry 3462 (class 0 OID 16717)
-- Dependencies: 210
-- Data for Name: utente; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.utente VALUES ('01', 'string', 'A', '2022-01-31 12:35:02.697588', '2022-01-31 12:35:02.697588', NULL, NULL, NULL);
INSERT INTO tracker.utente VALUES ('bfef6e8c-1ad3-48ab-aaa7-24b6864218d0', NULL, 'A', '2022-01-31 12:40:20.328362', '2022-01-31 12:40:20.328362', NULL, NULL, NULL);


--
-- TOC entry 3472 (class 0 OID 82498)
-- Dependencies: 220
-- Data for Name: utente_ruolo; Type: TABLE DATA; Schema: tracker; Owner: postgres
--

INSERT INTO tracker.utente_ruolo VALUES ('01', 'M');
INSERT INTO tracker.utente_ruolo VALUES ('bfef6e8c-1ad3-48ab-aaa7-24b6864218d0', 'P');


--
-- TOC entry 3530 (class 0 OID 0)
-- Dependencies: 212
-- Name: festivita_id_seq; Type: SEQUENCE SET; Schema: tracker; Owner: postgres
--

SELECT pg_catalog.setval('tracker.festivita_id_seq', 12, true);


--
-- TOC entry 3531 (class 0 OID 0)
-- Dependencies: 216
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: tracker; Owner: postgres
--

SELECT pg_catalog.setval('tracker.hibernate_sequence', 21, true);


--
-- TOC entry 3289 (class 2606 OID 90735)
-- Name: anagrafica_persona anagrafica_persona_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.anagrafica_persona
    ADD CONSTRAINT anagrafica_persona_pkey PRIMARY KEY (codice_persona);


--
-- TOC entry 3291 (class 2606 OID 90742)
-- Name: azienda azienda_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.azienda
    ADD CONSTRAINT azienda_pkey PRIMARY KEY (codice_azienda);


--
-- TOC entry 3279 (class 2606 OID 82556)
-- Name: commessa commessa_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.commessa
    ADD CONSTRAINT commessa_pkey PRIMARY KEY (codice_commessa);


--
-- TOC entry 3293 (class 2606 OID 90749)
-- Name: consulente consulente_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.consulente
    ADD CONSTRAINT consulente_pkey PRIMARY KEY (codice_persona);


--
-- TOC entry 3287 (class 2606 OID 82657)
-- Name: dati_economici_consulente dati_economici_consulente_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dati_economici_consulente
    ADD CONSTRAINT dati_economici_consulente_pkey PRIMARY KEY (codice_persona, data_ingaggio, decorrenza_assegnazione_centro_di_costo);


--
-- TOC entry 3285 (class 2606 OID 82647)
-- Name: dati_economici_dipendente dati_economici_dipendente_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dati_economici_dipendente
    ADD CONSTRAINT dati_economici_dipendente_pkey PRIMARY KEY (codice_persona);


--
-- TOC entry 3281 (class 2606 OID 82563)
-- Name: dipendente_commessa dipendente_commessa_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dipendente_commessa
    ADD CONSTRAINT dipendente_commessa_pkey PRIMARY KEY (codice_commessa, codice_persona);


--
-- TOC entry 3295 (class 2606 OID 90756)
-- Name: dipendente dipendente_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dipendente
    ADD CONSTRAINT dipendente_pkey PRIMARY KEY (codice_persona);


--
-- TOC entry 3267 (class 2606 OID 16827)
-- Name: festivita festivita_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.festivita
    ADD CONSTRAINT festivita_pkey PRIMARY KEY (id);


--
-- TOC entry 3273 (class 2606 OID 66087)
-- Name: gruppo gruppo_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.gruppo
    ADD CONSTRAINT gruppo_pkey PRIMARY KEY (codice_gruppo);


--
-- TOC entry 3265 (class 2606 OID 49676)
-- Name: nota_spese nota_spese_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.nota_spese
    ADD CONSTRAINT nota_spese_pkey PRIMARY KEY (anno_di_riferimento, mese_di_riferimento, codice_persona, codice_commessa, giorno_di_riferimento, tipo_costo);


--
-- TOC entry 3283 (class 2606 OID 82570)
-- Name: ordine_commessa ordine_commessa_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.ordine_commessa
    ADD CONSTRAINT ordine_commessa_pkey PRIMARY KEY (codice_azienda, codice_commessa, numero_ordine_cliente);


--
-- TOC entry 3277 (class 2606 OID 82482)
-- Name: ruoli ruoli_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.ruoli
    ADD CONSTRAINT ruoli_pkey PRIMARY KEY (id);


--
-- TOC entry 3269 (class 2606 OID 41477)
-- Name: time_sheet_entry time_sheet_entry_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.time_sheet_entry
    ADD CONSTRAINT time_sheet_entry_pkey PRIMARY KEY (anno_di_riferimento, codice_commessa, codice_persona, giorno_di_riferimento, mese_di_riferimento);


--
-- TOC entry 3271 (class 2606 OID 41494)
-- Name: time_sheet time_sheet_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.time_sheet
    ADD CONSTRAINT time_sheet_pkey PRIMARY KEY (anno_di_riferimento, codice_persona, mese_di_riferimento);


--
-- TOC entry 3275 (class 2606 OID 66092)
-- Name: gruppo uk_bpd53y9cf7nhdw00mmvqxlchm; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.gruppo
    ADD CONSTRAINT uk_bpd53y9cf7nhdw00mmvqxlchm UNIQUE (nome);


--
-- TOC entry 3263 (class 2606 OID 16721)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (codice_persona);


--
-- TOC entry 3321 (class 2620 OID 16869)
-- Name: nota_spese set_create_update_timestamp; Type: TRIGGER; Schema: tracker; Owner: postgres
--

CREATE TRIGGER set_create_update_timestamp BEFORE INSERT ON tracker.nota_spese FOR EACH ROW EXECUTE FUNCTION tracker.set_create_update_timestamp();


--
-- TOC entry 3319 (class 2620 OID 16864)
-- Name: utente set_create_update_timestamp; Type: TRIGGER; Schema: tracker; Owner: postgres
--

CREATE TRIGGER set_create_update_timestamp BEFORE INSERT ON tracker.utente FOR EACH ROW EXECUTE FUNCTION tracker.set_create_update_timestamp();


--
-- TOC entry 3322 (class 2620 OID 16879)
-- Name: nota_spese set_update_timestamp; Type: TRIGGER; Schema: tracker; Owner: postgres
--

CREATE TRIGGER set_update_timestamp BEFORE UPDATE ON tracker.nota_spese FOR EACH ROW EXECUTE FUNCTION tracker.set_update_timestamp();


--
-- TOC entry 3320 (class 2620 OID 16874)
-- Name: utente set_update_timestamp; Type: TRIGGER; Schema: tracker; Owner: postgres
--

CREATE TRIGGER set_update_timestamp BEFORE UPDATE ON tracker.utente FOR EACH ROW EXECUTE FUNCTION tracker.set_update_timestamp();


--
-- TOC entry 3314 (class 2606 OID 90837)
-- Name: anagrafica_persona fk481tdrtliphsok7fvaq19d3s2; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.anagrafica_persona
    ADD CONSTRAINT fk481tdrtliphsok7fvaq19d3s2 FOREIGN KEY (codice_persona) REFERENCES tracker.utente(codice_persona);


--
-- TOC entry 3300 (class 2606 OID 41500)
-- Name: time_sheet_entry fk4e73htg3xaxwvmp27q9vlq8qd; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.time_sheet_entry
    ADD CONSTRAINT fk4e73htg3xaxwvmp27q9vlq8qd FOREIGN KEY (anno_di_riferimento, codice_persona, mese_di_riferimento) REFERENCES tracker.time_sheet(anno_di_riferimento, codice_persona, mese_di_riferimento);


--
-- TOC entry 3309 (class 2606 OID 82596)
-- Name: ordine_commessa fk58gify6yolkm10eprje002qfp; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.ordine_commessa
    ADD CONSTRAINT fk58gify6yolkm10eprje002qfp FOREIGN KEY (codice_commessa) REFERENCES tracker.commessa(codice_commessa);


--
-- TOC entry 3302 (class 2606 OID 41495)
-- Name: time_sheet fk6l8kxdj2ojncbhu0cnfiu4ib; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.time_sheet
    ADD CONSTRAINT fk6l8kxdj2ojncbhu0cnfiu4ib FOREIGN KEY (codice_persona) REFERENCES tracker.utente(codice_persona);


--
-- TOC entry 3310 (class 2606 OID 90791)
-- Name: ordine_commessa fk82lshqsisnqnqvdikovx0d97g; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.ordine_commessa
    ADD CONSTRAINT fk82lshqsisnqnqvdikovx0d97g FOREIGN KEY (codice_azienda) REFERENCES tracker.azienda(codice_azienda);


--
-- TOC entry 3306 (class 2606 OID 90766)
-- Name: commessa fk8hxawn9v3ddo4ja6ralxnko10; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.commessa
    ADD CONSTRAINT fk8hxawn9v3ddo4ja6ralxnko10 FOREIGN KEY (codice_azienda) REFERENCES tracker.azienda(codice_azienda);


--
-- TOC entry 3316 (class 2606 OID 90847)
-- Name: consulente fk_641le13ridw0k2oqm22hv775e; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.consulente
    ADD CONSTRAINT fk_641le13ridw0k2oqm22hv775e FOREIGN KEY (codice_persona) REFERENCES tracker.utente(codice_persona);


--
-- TOC entry 3315 (class 2606 OID 90842)
-- Name: consulente fk_6bknpfjfgjm25mgdcyb207uk; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.consulente
    ADD CONSTRAINT fk_6bknpfjfgjm25mgdcyb207uk FOREIGN KEY (codice_azienda) REFERENCES tracker.azienda(codice_azienda);


--
-- TOC entry 3317 (class 2606 OID 90852)
-- Name: dipendente fk_de5b1m0oop6m0jfppwqx3q2id; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dipendente
    ADD CONSTRAINT fk_de5b1m0oop6m0jfppwqx3q2id FOREIGN KEY (codice_azienda) REFERENCES tracker.azienda(codice_azienda);


--
-- TOC entry 3318 (class 2606 OID 90857)
-- Name: dipendente fk_qvqf68t4b9efvg06t7whj5g8c; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dipendente
    ADD CONSTRAINT fk_qvqf68t4b9efvg06t7whj5g8c FOREIGN KEY (codice_persona) REFERENCES tracker.utente(codice_persona);


--
-- TOC entry 3312 (class 2606 OID 90776)
-- Name: dati_economici_consulente fkc4nejqadrdmj2rcr7dh1mvb45; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dati_economici_consulente
    ADD CONSTRAINT fkc4nejqadrdmj2rcr7dh1mvb45 FOREIGN KEY (codice_persona) REFERENCES tracker.consulente(codice_persona);


--
-- TOC entry 3307 (class 2606 OID 82576)
-- Name: dipendente_commessa fkflmgp394qb0rk97kkgtphp9bi; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dipendente_commessa
    ADD CONSTRAINT fkflmgp394qb0rk97kkgtphp9bi FOREIGN KEY (codice_commessa) REFERENCES tracker.commessa(codice_commessa);


--
-- TOC entry 3303 (class 2606 OID 66098)
-- Name: gruppo_contatto fkgd0dt4i9tgaad188mnnaevash; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.gruppo_contatto
    ADD CONSTRAINT fkgd0dt4i9tgaad188mnnaevash FOREIGN KEY (codice_gruppo) REFERENCES tracker.gruppo(codice_gruppo);


--
-- TOC entry 3301 (class 2606 OID 82628)
-- Name: time_sheet_entry fkj3dowh594r7ciewo3n06cvneu; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.time_sheet_entry
    ADD CONSTRAINT fkj3dowh594r7ciewo3n06cvneu FOREIGN KEY (codice_commessa) REFERENCES tracker.commessa(codice_commessa);


--
-- TOC entry 3311 (class 2606 OID 90781)
-- Name: dati_economici_dipendente fkj71113l1l484ostem9gmvfb6r; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dati_economici_dipendente
    ADD CONSTRAINT fkj71113l1l484ostem9gmvfb6r FOREIGN KEY (codice_persona) REFERENCES tracker.dipendente(codice_persona);


--
-- TOC entry 3308 (class 2606 OID 82581)
-- Name: dipendente_commessa fkkspk90tdvt1o3hqy62scj5p3x; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.dipendente_commessa
    ADD CONSTRAINT fkkspk90tdvt1o3hqy62scj5p3x FOREIGN KEY (codice_persona) REFERENCES tracker.utente(codice_persona);


--
-- TOC entry 3304 (class 2606 OID 82503)
-- Name: utente_ruolo fko55mkyms7chxeyj5cc2ya4nwb; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.utente_ruolo
    ADD CONSTRAINT fko55mkyms7chxeyj5cc2ya4nwb FOREIGN KEY (id) REFERENCES tracker.ruoli(id);


--
-- TOC entry 3305 (class 2606 OID 82508)
-- Name: utente_ruolo fkqn48f6d975ci2o30k1tggofue; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.utente_ruolo
    ADD CONSTRAINT fkqn48f6d975ci2o30k1tggofue FOREIGN KEY (codice_persona) REFERENCES tracker.utente(codice_persona);


--
-- TOC entry 3299 (class 2606 OID 82586)
-- Name: nota_spese fkqupfg36f0a4pde6mbof13mg0g; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.nota_spese
    ADD CONSTRAINT fkqupfg36f0a4pde6mbof13mg0g FOREIGN KEY (codice_commessa) REFERENCES tracker.commessa(codice_commessa);


--
-- TOC entry 3298 (class 2606 OID 49681)
-- Name: nota_spese fkr4kcqfksr7mc7wt71s4jlhlqx; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.nota_spese
    ADD CONSTRAINT fkr4kcqfksr7mc7wt71s4jlhlqx FOREIGN KEY (anno_di_riferimento, codice_commessa, codice_persona, giorno_di_riferimento, mese_di_riferimento) REFERENCES tracker.time_sheet_entry(anno_di_riferimento, codice_commessa, codice_persona, giorno_di_riferimento, mese_di_riferimento);


--
-- TOC entry 3313 (class 2606 OID 90832)
-- Name: anagrafica_persona fktb4h7uvhofo2sce83r37m5jqp; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.anagrafica_persona
    ADD CONSTRAINT fktb4h7uvhofo2sce83r37m5jqp FOREIGN KEY (codice_azienda) REFERENCES tracker.azienda(codice_azienda);


--
-- TOC entry 3297 (class 2606 OID 16781)
-- Name: nota_spese nota_spese_codice_persona_fkey; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.nota_spese
    ADD CONSTRAINT nota_spese_codice_persona_fkey FOREIGN KEY (codice_persona) REFERENCES tracker.utente(codice_persona);


--
-- TOC entry 3296 (class 2606 OID 16722)
-- Name: utente utente_codice_responsabile_fkey; Type: FK CONSTRAINT; Schema: tracker; Owner: postgres
--

ALTER TABLE ONLY tracker.utente
    ADD CONSTRAINT utente_codice_responsabile_fkey FOREIGN KEY (codice_responsabile) REFERENCES tracker.utente(codice_persona);


--
-- TOC entry 3497 (class 0 OID 0)
-- Dependencies: 226
-- Name: TABLE anagrafica_persona; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.anagrafica_persona FROM postgres;
GRANT ALL ON TABLE tracker.anagrafica_persona TO postgres WITH GRANT OPTION;


--
-- TOC entry 3498 (class 0 OID 0)
-- Dependencies: 227
-- Name: TABLE azienda; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.azienda FROM postgres;
GRANT ALL ON TABLE tracker.azienda TO postgres WITH GRANT OPTION;


--
-- TOC entry 3499 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE commessa; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.commessa FROM postgres;
GRANT ALL ON TABLE tracker.commessa TO postgres WITH GRANT OPTION;


--
-- TOC entry 3500 (class 0 OID 0)
-- Dependencies: 228
-- Name: TABLE consulente; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.consulente FROM postgres;
GRANT ALL ON TABLE tracker.consulente TO postgres WITH GRANT OPTION;


--
-- TOC entry 3503 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE dati_economici_consulente; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.dati_economici_consulente FROM postgres;
GRANT ALL ON TABLE tracker.dati_economici_consulente TO postgres WITH GRANT OPTION;


--
-- TOC entry 3511 (class 0 OID 0)
-- Dependencies: 224
-- Name: TABLE dati_economici_dipendente; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.dati_economici_dipendente FROM postgres;
GRANT ALL ON TABLE tracker.dati_economici_dipendente TO postgres WITH GRANT OPTION;


--
-- TOC entry 3512 (class 0 OID 0)
-- Dependencies: 229
-- Name: TABLE dipendente; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.dipendente FROM postgres;
GRANT ALL ON TABLE tracker.dipendente TO postgres WITH GRANT OPTION;


--
-- TOC entry 3513 (class 0 OID 0)
-- Dependencies: 222
-- Name: TABLE dipendente_commessa; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.dipendente_commessa FROM postgres;
GRANT ALL ON TABLE tracker.dipendente_commessa TO postgres WITH GRANT OPTION;


--
-- TOC entry 3514 (class 0 OID 0)
-- Dependencies: 213
-- Name: TABLE festivita; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.festivita FROM postgres;
GRANT ALL ON TABLE tracker.festivita TO postgres WITH GRANT OPTION;


--
-- TOC entry 3516 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE gruppo; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.gruppo FROM postgres;
GRANT ALL ON TABLE tracker.gruppo TO postgres WITH GRANT OPTION;


--
-- TOC entry 3517 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE gruppo_contatto; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.gruppo_contatto FROM postgres;
GRANT ALL ON TABLE tracker.gruppo_contatto TO postgres WITH GRANT OPTION;


--
-- TOC entry 3521 (class 0 OID 0)
-- Dependencies: 211
-- Name: TABLE nota_spese; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.nota_spese FROM postgres;
GRANT ALL ON TABLE tracker.nota_spese TO postgres WITH GRANT OPTION;


--
-- TOC entry 3522 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE ordine_commessa; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.ordine_commessa FROM postgres;
GRANT ALL ON TABLE tracker.ordine_commessa TO postgres WITH GRANT OPTION;


--
-- TOC entry 3523 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE ruoli; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.ruoli FROM postgres;
GRANT ALL ON TABLE tracker.ruoli TO postgres WITH GRANT OPTION;


--
-- TOC entry 3524 (class 0 OID 0)
-- Dependencies: 215
-- Name: TABLE time_sheet; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.time_sheet FROM postgres;
GRANT ALL ON TABLE tracker.time_sheet TO postgres WITH GRANT OPTION;


--
-- TOC entry 3525 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE time_sheet_entry; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.time_sheet_entry FROM postgres;
GRANT ALL ON TABLE tracker.time_sheet_entry TO postgres WITH GRANT OPTION;


--
-- TOC entry 3528 (class 0 OID 0)
-- Dependencies: 210
-- Name: TABLE utente; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.utente FROM postgres;
GRANT ALL ON TABLE tracker.utente TO postgres WITH GRANT OPTION;


--
-- TOC entry 3529 (class 0 OID 0)
-- Dependencies: 220
-- Name: TABLE utente_ruolo; Type: ACL; Schema: tracker; Owner: postgres
--

REVOKE ALL ON TABLE tracker.utente_ruolo FROM postgres;
GRANT ALL ON TABLE tracker.utente_ruolo TO postgres WITH GRANT OPTION;


--
-- TOC entry 2118 (class 826 OID 16635)
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: tracker; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA tracker GRANT ALL ON TABLES  TO postgres WITH GRANT OPTION;


-- Completed on 2022-02-01 09:12:07 UTC

--
-- PostgreSQL database dump complete
--

