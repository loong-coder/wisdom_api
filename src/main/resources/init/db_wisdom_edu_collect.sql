-- auto-generated definition
create table edu_collect
(
    id             bigint(19) auto_increment
        primary key,
    course_id      bigint(19) null,
    user_id        bigint(19) null,
    is_collect     bit        not null,
    deleted        bit        not null,
    create_user_id bigint(19) null,
    update_user_id bigint(19) null,
    create_date    datetime   not null,
    update_date    datetime   null
)
    charset = utf8;

