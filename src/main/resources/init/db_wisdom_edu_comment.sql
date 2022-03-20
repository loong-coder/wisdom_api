create table edu_comment
(
    id             bigint(19) auto_increment
        primary key,
    course_id      bigint(19) null,
    user_id        bigint(19) null,
    comment        text       null,
    deleted        bit        not null,
    create_user_id bigint(19) null,
    update_user_id bigint(19) null,
    create_date    datetime   not null,
    update_date    datetime   null
)
    charset = utf8;

INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (1, 23, 1, '老师讲的很详细 nice！', false, 1, null, '2022-03-13 13:16:27', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (2, 23, 1, '喜欢这个老师的授课方式！', false, 1, null, '2022-03-13 13:16:44', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (3, 23, 1, '老师讲的很仔细', false, 1, null, '2022-03-13 13:16:52', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (4, 23, 1, '+1', false, 1, null, '2022-03-13 13:27:01', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (5, 23, 1, '+2', false, 1, null, '2022-03-13 13:27:25', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (6, 23, 1, '+3', false, 1, null, '2022-03-13 13:27:43', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (7, 23, 1, '+4', false, 1, null, '2022-03-13 13:39:50', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (8, 23, 1, '+5', false, 1, null, '2022-03-13 13:39:54', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (9, 23, 1, '+6', false, 1, null, '2022-03-13 13:39:57', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (10, 23, 1, '+7', false, 1, null, '2022-03-13 13:40:00', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (11, 23, 1, '+8', false, 1, null, '2022-03-13 13:40:08', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (12, 23, 1, '+9', false, 1, null, '2022-03-13 13:40:17', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (13, 23, 1, 'Java 在线平台', false, 1, null, '2022-03-13 13:48:47', null);
INSERT INTO db_wisdom.edu_comment (id, course_id, user_id, comment, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (14, 23, 1, 'DDDD', false, 1, null, '2022-03-13 13:49:47', null);