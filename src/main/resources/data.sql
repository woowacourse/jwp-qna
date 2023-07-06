insert into user(id, user_id, password, name, email, created_at, updated_at)
values (1, 'user', 'password', 'name', 'email', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into question(id, title, contents, writer_id, deleted, created_at, updated_at)
values (1, 'title', 'contents', 1, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into question(id, title, contents, writer_id, deleted, created_at, updated_at)
values (2, 'title2', 'contents2', 1, true, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into delete_history(id, content_type, content_id, deleted_by_id, create_date)
values (1, 'QUESTION', 1, 2, CURRENT_TIMESTAMP());

insert into answer(id, writer_id, question_id, contents, deleted, created_at, updated_at)
values (1, 1, 1, 'contents', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into answer(id, writer_id, question_id, contents, deleted, created_at, updated_at)
values (2, 1, 1, 'contents2', true, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
