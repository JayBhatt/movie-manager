-- populate user_role
insert into user_role (active, created_on, updated_on, role, description) values (true, '2022-09-29 10:49:00.493', '2022-09-29 10:49:00.493', 'ADMIN', 'Admin has access to all the components and features');
insert into user_role (active, created_on, updated_on, role, description) values (true, '2022-09-29 10:49:00.493', '2022-09-29 10:49:00.493', 'USER', 'User has access to limited components and features');

-- populate config
insert into config (active, created_on, updated_on, config_key, config_value) values (true, '2022-09-29 10:49:00.493', '2022-09-29 10:49:00.493', 'imdbBaseUrl', 'https://api.themoviedb.org/3/');
insert into config (active, created_on, updated_on, config_key, config_value) values (true, '2022-09-29 10:49:00.493', '2022-09-29 10:49:00.493', 'imdbKey', '49d380bae47579dfed99c4f1db6b4ec1');
insert into config (active, created_on, updated_on, config_key, config_value) values (true, '2022-09-29 10:49:00.493', '2022-09-29 10:49:00.493', 'imdbMaxRetryCount', '3');
insert into config (active, created_on, updated_on, config_key, config_value) values (true, '2022-09-29 10:49:00.493', '2022-09-29 10:49:00.493', 'imdbMaxRetryDelay', '3');
insert into config (active, created_on, updated_on, config_key, config_value) values (true, '2022-09-29 10:49:00.493', '2022-09-29 10:49:00.493', 'imdbRegion', 'AU');

-- populate users (default password Adm@1213)
insert into movie_user (active, created_on, updated_on, email, password, role_id) values (true, '2022-09-29 10:49:00.493', '2022-09-29 10:49:00.493', 'admin@example.com', '$2a$10$Z4Iq78NX/.SlVZoBWK9wSO4d4sWOFXz.qzKvKP6UWfbDmQ4WKHMI.', 1)
