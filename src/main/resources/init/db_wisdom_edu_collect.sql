-- auto-generated definition
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

