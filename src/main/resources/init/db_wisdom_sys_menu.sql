create table sys_menu
(
    id             bigint(19) auto_increment
        primary key,
    parent_id      bigint(19)   not null,
    layer          tinyint      not null,
    title          varchar(64)  not null,
    path           varchar(128) null,
    icon           varchar(128) null,
    deleted        bit          null,
    create_date    datetime     not null,
    update_date    datetime     null,
    create_user_id bigint(19)   null,
    update_user_id bigint(19)   null,
    disable        bit          not null
)
    charset = utf8;

INSERT INTO db_wisdom.sys_menu (id, parent_id, layer, title, path, icon, deleted, create_date, update_date, create_user_id, update_user_id, disable) VALUES (1, -1, 1, '系统管理', null, 'el-icon-setting', false, '2022-03-08 15:41:29', null, null, null, false);
INSERT INTO db_wisdom.sys_menu (id, parent_id, layer, title, path, icon, deleted, create_date, update_date, create_user_id, update_user_id, disable) VALUES (2, 1, 2, '用户管理', '/sys/users', 'el-icon-user', false, '2022-03-08 15:41:29', null, null, null, false);
INSERT INTO db_wisdom.sys_menu (id, parent_id, layer, title, path, icon, deleted, create_date, update_date, create_user_id, update_user_id, disable) VALUES (3, -1, 1, '内容管理', '', 'el-icon-s-operation', false, '2022-03-08 15:41:29', null, null, null, false);
INSERT INTO db_wisdom.sys_menu (id, parent_id, layer, title, path, icon, deleted, create_date, update_date, create_user_id, update_user_id, disable) VALUES (4, 3, 2, '课程主题', '/sys/subjectManger', 'el-icon-s-promotion', false, '2022-03-08 15:41:29', null, null, null, false);
INSERT INTO db_wisdom.sys_menu (id, parent_id, layer, title, path, icon, deleted, create_date, update_date, create_user_id, update_user_id, disable) VALUES (5, 3, 2, '课程列表', '/sys/courseManger', 'el-icon-share', false, '2022-03-08 15:41:29', null, null, null, false);
INSERT INTO db_wisdom.sys_menu (id, parent_id, layer, title, path, icon, deleted, create_date, update_date, create_user_id, update_user_id, disable) VALUES (7, -1, 1, '报表统计', '', 'el-icon-s-data', false, '2022-03-08 15:41:29', null, null, null, false);
INSERT INTO db_wisdom.sys_menu (id, parent_id, layer, title, path, icon, deleted, create_date, update_date, create_user_id, update_user_id, disable) VALUES (8, 7, 2, '数据看板', '/sys/dataDashboard', 'el-icon-data-line', false, '2022-03-08 15:41:29', null, null, null, false);