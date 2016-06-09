INSERT INTO public.papel(nome) VALUES ('SERVIDOR');
INSERT INTO public.papel(nome) VALUES ('DIRECAO');
INSERT INTO public.papel(nome) VALUES ('ALUNO');
INSERT INTO public.papel(nome) VALUES ('ADMINISTRACAO');

INSERT INTO public.pessoa(cpf, nome) VALUES ('52531198334', 'Francisco Flávio Santos');
INSERT INTO public.pessoa(cpf, nome) VALUES ('89883217587', 'Guilherme Gomes Pereira');
INSERT INTO public.pessoa(cpf, nome) VALUES ('64789969304', 'Raissa Oliveira Barros');
INSERT INTO public.pessoa(cpf, nome) VALUES ('82880050472', 'Alex Ribeiro Sousa');
INSERT INTO public.pessoa(cpf, nome) VALUES ('95131922372', 'Letícia Melo Pereira');
INSERT INTO public.pessoa(cpf, nome) VALUES ('93912072353', 'Miguel Ribeiro Cardoso');
INSERT INTO public.pessoa(cpf, nome) VALUES ('32319215387', 'Brenda Fernandes Pereira');
INSERT INTO public.pessoa(cpf, nome) VALUES ('79793053372', 'Julieta Dias Cardoso');
INSERT INTO public.pessoa(cpf, nome) VALUES ('92995454304', 'Manuela Cardoso Fernandes');
INSERT INTO public.pessoa(cpf, nome) VALUES ('92995454310', 'Tereza Cardoso Fernandes');
INSERT INTO public.pessoa(cpf, nome) VALUES ('27240450848', 'Rayanne Mendes Oliveira');
INSERT INTO public.pessoa(cpf, nome) VALUES ('45631697300', 'Mariana Carvalho Cunha');

INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (1, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (1, 2);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (1, 3);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (2, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (3, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (4, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (5, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (6, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (7, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (7, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (7, 3);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (7, 3);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (7, 3);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (7, 3);

INSERT INTO servidor(dedicacao, funcao, siape, pessoa_id) VALUES ('EXCLUSIVA', 'DOCENTE', '1111', 1);
INSERT INTO servidor(dedicacao, funcao, siape, pessoa_id) VALUES ('H40', 'DOCENTE', '2222', 2);
INSERT INTO servidor(dedicacao, funcao, siape, pessoa_id) VALUES ('H20', 'DOCENTE', '3333', 3);
INSERT INTO servidor(dedicacao, funcao, siape, pessoa_id) VALUES ('H40', 'STA', '4444', 4);
INSERT INTO servidor(dedicacao, funcao, siape, pessoa_id) VALUES ('H20', 'STA', '5555', 5);
INSERT INTO servidor(dedicacao, funcao, siape, pessoa_id) VALUES ('H40', 'STA', '6666', 6);
INSERT INTO servidor(dedicacao, funcao, siape, pessoa_id) VALUES ('H20', 'STA', '7777', 7);
INSERT INTO servidor(dedicacao, funcao, siape, pessoa_id) VALUES ('EXCLUSIVA', 'DOCENTE', '8888', 8);

INSERT INTO aluno(curso, matricula, pessoa_id) VALUES ('Sistemas de Informação', '1111', 9);
INSERT INTO aluno(curso, matricula, pessoa_id) VALUES ('Engenharia de Software', '2222', 10);
INSERT INTO aluno(curso, matricula, pessoa_id) VALUES ('Redes de Computadores', '3333', 11);
INSERT INTO aluno(curso, matricula, pessoa_id) VALUES ('Ciência da Computação', '4444', 12);
