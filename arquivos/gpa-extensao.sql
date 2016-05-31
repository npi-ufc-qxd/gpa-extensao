INSERT INTO public.papel(nome) VALUES ('USER');
INSERT INTO public.papel(nome) VALUES ('DIRECAO');

INSERT INTO public.pessoa(cpf, nome) VALUES ('52531198334', 'Francisco Flávio Santos');
INSERT INTO public.pessoa(cpf, nome) VALUES ('89883217587', 'Guilherme Gomes Pereira');
INSERT INTO public.pessoa(cpf, nome) VALUES ('64789969304', 'Raissa Oliveira Barros');
INSERT INTO public.pessoa(cpf, nome) VALUES ('82880050472', 'Alex Ribeiro Sousa');
INSERT INTO public.pessoa(cpf, nome) VALUES ('95131922372', 'Letícia Melo Pereira');
INSERT INTO public.pessoa(cpf, nome) VALUES ('93912072353', 'Miguel Ribeiro Cardoso');
INSERT INTO public.pessoa(cpf, nome) VALUES ('32319215387', 'Brenda Fernandes Pereira');

INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (1, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (1, 2);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (2, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (3, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (4, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (5, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (6, 1);
INSERT INTO public.papel_pessoa(pessoa_id, papel_id) VALUES (7, 1);
