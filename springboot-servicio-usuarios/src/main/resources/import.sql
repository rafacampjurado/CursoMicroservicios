INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('andres', '$2a$10$KP12u1H.5L0mxKuLk48ZJeUm76s662TVErIBqwn693l8rthu71Nwq', true, 'Andres', 'Guzman', 'profesor@bolsadeideas.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin', '$2a$10$aDTyDp9hfFEtKSxyy2h03OQzouYerUavBpbhWCNAwRwhNuwnIPDhi', true, 'John', 'Doe', 'jhon.doe@bolsadeideas.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_to_roles (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuarios_to_roles (usuario_id, role_id) VALUES (2,2);
INSERT INTO usuarios_to_roles (usuario_id, role_id) VALUES (2,1);