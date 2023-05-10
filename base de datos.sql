CREATE TABLE proyec (
    id nvarchar(10),
    ip nvarchar(10),
    hilos int,
    estado nvarchar(20)
);

INSERT INTO proyec (ip) VALUES ('/10.0.0.1');
INSERT INTO proyec (ip) VALUES ('/10.0.0.2');
INSERT INTO proyec (ip) VALUES ('/10.0.0.3');
INSERT INTO proyec (ip) VALUES ('/10.0.0.4');
INSERT INTO proyec (ip) VALUES ('/10.0.0.5');
INSERT INTO proyec (ip) VALUES ('/10.0.0.6');
INSERT INTO proyec (ip) VALUES ('/10.0.0.7');
INSERT INTO proyec (ip) VALUES ('/10.0.0.8');
INSERT INTO proyec (ip) VALUES ('/10.0.0.9');

UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.1';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.2';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.2';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.3';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.4';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.5';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.6';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.7';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.8';
UPDATE proyec SET estado = 'vigilancia' WHERE ip = '/10.0.0.9';

UPDATE proyec SET id = 'c4' WHERE ip = '/10.0.0.1';
UPDATE proyec SET id = 'c5' WHERE ip = '/10.0.0.2';
UPDATE proyec SET id = 'c6' WHERE ip = '/10.0.0.3';
UPDATE proyec SET id = 'c7' WHERE ip = '/10.0.0.4';
UPDATE proyec SET id = 'c8' WHERE ip = '/10.0.0.5';
UPDATE proyec SET id = 'c9' WHERE ip = '/10.0.0.6';
UPDATE proyec SET id = 'c10' WHERE ip = '/10.0.0.7';
UPDATE proyec SET id = 'c11' WHERE ip = '/10.0.0.8';
UPDATE proyec SET id = 'c12' WHERE ip = '/10.0.0.9';

SELECT* from proyec;
