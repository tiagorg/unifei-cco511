SET SQL DIALECT 3;

CREATE DATABASE 'F:\Java\CCO511\lab6\bd\ESCOLA.FDB'
PAGE_SIZE 4096
DEFAULT CHARACTER SET NONE;

/* Table: ALUNO, Owner: SYSDBA */

CREATE TABLE "ALUNO" 
(
  "CODIGO"	 INTEGER NOT NULL,
  "NOME"	 VARCHAR(25) NOT NULL,
  "RG"	 VARCHAR(15) NOT NULL,
  "DT_NASC"	 DATE NOT NULL,
  "FONE"	 VARCHAR(12),
 PRIMARY KEY ("CODIGO")
);

/* Table: DISCIP, Owner: SYSDBA */

CREATE TABLE "DISCIP" 
(
  "CODIGO"	 INTEGER NOT NULL,
  "NOME"	 VARCHAR(25) NOT NULL,
  "CREDITOS"	 INTEGER NOT NULL,
 PRIMARY KEY ("CODIGO")
);

/* Table: MATRICULA, Owner: SYSDBA */

CREATE TABLE "MATRICULA" 
(
  "CODALUNO"	 INTEGER NOT NULL,
  "CODDISCIP"	 INTEGER NOT NULL,
 PRIMARY KEY ("CODALUNO", "CODDISCIP")
);
ALTER TABLE "MATRICULA" ADD FOREIGN KEY ("CODALUNO") REFERENCES "ALUNO" ("CODIGO");
ALTER TABLE "MATRICULA" ADD FOREIGN KEY ("CODDISCIP") REFERENCES "DISCIP" ("CODIGO");

CREATE GENERATOR "ALUNO_CODIGO";
CREATE GENERATOR "DISC_CODIGO";
SET TERM ^ ;


/* Triggers only will work for SQL triggers */

CREATE TRIGGER "CODIGO_ALUNO" FOR "ALUNO" 
ACTIVE BEFORE INSERT POSITION 0
AS
	BEGIN
		NEW.CODIGO = GEN_ID(ALUNO_CODIGO, 1);
	END
 ^

CREATE TRIGGER "CODIGO_DISCIP" FOR "DISCIP" 
ACTIVE BEFORE INSERT POSITION 0
AS
	BEGIN
		NEW.CODIGO = GEN_ID(DISC_CODIGO, 1);
	END
 ^

COMMIT WORK ^
SET TERM ;^

/* Grant Roles for this database */


/* Grant permissions for this database */

GRANT DELETE, INSERT, SELECT, UPDATE, REFERENCES ON "ALUNO" TO CCO511;
GRANT DELETE, INSERT, SELECT, UPDATE, REFERENCES ON "DISCIP" TO CCO511;
GRANT DELETE, INSERT, SELECT, UPDATE, REFERENCES ON "MATRICULA" TO CCO511;

