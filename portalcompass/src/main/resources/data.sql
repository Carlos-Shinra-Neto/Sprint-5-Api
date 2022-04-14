/*Usuários */
INSERT INTO USUARIO(id, nome, email, senha) VALUES (1, 'USUARIO', 'instrutor@email.com', '$2a$10$sFKmbxbG4ryhwPNx/l3pgOJSt.fW1z6YcUnuE2X8APA/Z3NI/oSpq');
INSERT INTO USUARIO(id, nome, email, senha) VALUES (2, 'USUARIO', 'bolsista@email.com', '$2a$10$sFKmbxbG4ryhwPNx/l3pgOJSt.fW1z6YcUnuE2X8APA/Z3NI/oSpq');

/*Perfis */
INSERT INTO PERFIL(id, nome) VALUES (1, 'ROLE_ADMIN');
INSERT INTO PERFIL(id, nome) VALUES (2, 'ROLE_BOLSISTA');

/*Relacionamento*/
INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id) VALUES (1, 1);
INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id) VALUES (2, 2);