create table edu_thumb_up
(
    id             bigint(19) auto_increment
        primary key,
    user_id        bigint(19) null,
    business_id    bigint(19) null,
    business_type  tinyint    null,
    thumb_up       bit        null,
    deleted        bit        null,
    create_user_id bigint(19) null,
    update_user_id bigint(19) null,
    create_date    datetime   not null,
    update_date    datetime   null
)
    charset = utf8;

INSERT INTO db_wisdom.edu_thumb_up (id, user_id, business_id, business_type, thumb_up, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (1, 1, 23, 1, true, false, 1, 1, '2022-03-19 13:58:46', '2022-03-19 14:06:56');