create table edu_subject
(
    id             bigint(19) auto_increment
        primary key,
    parent_id      bigint(19)  null,
    title          varchar(64) not null,
    sort           smallint    null,
    deleted        tinyint(1)  null,
    create_user_id bigint(19)  null,
    update_user_id bigint(19)  null,
    create_date    datetime    not null,
    update_date    datetime    null
)
    charset = utf8;

INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (1, -1, '后端', 1, 0, 1, 1, '2022-03-08 08:20:07', '2022-03-09 03:11:19');
INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (2, 1, 'Java', 1, 0, 1, 1, '2022-03-08 09:20:33', '2022-03-09 03:16:04');
INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (3, 1, 'Golang', 3, 0, 1, 1, '2022-03-08 08:20:53', '2022-03-09 03:15:53');
INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (4, -1, '前端', 3, 0, 1, 1, '2022-03-08 08:21:44', '2022-03-08 14:49:04');
INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (5, 4, 'Css', 2, 0, 1, 1, '2022-03-08 08:22:10', null);
INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (6, 4, 'Vue', 2, 0, 1, 1, '2022-03-08 08:22:28', null);
INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (8, -1, '数据库', 2, 0, 1, 1, '2022-03-08 13:32:03', '2022-03-09 03:10:37');
INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (11, 8, 'Mysql', 5, 0, 1, 1, '2022-03-08 13:34:21', '2022-03-09 03:07:30');
INSERT INTO db_wisdom.edu_subject (id, parent_id, title, sort, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (12, 8, 'Sqlserver', 2, 0, 1, 1, '2022-03-08 13:34:45', '2022-03-09 03:08:12');