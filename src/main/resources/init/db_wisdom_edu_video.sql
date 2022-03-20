create table edu_video
(
    id                  bigint(19) auto_increment
        primary key,
    course_id           bigint(19)     null,
    title               varchar(128)   null,
    video_source        varchar(256)   null,
    video_original_name varchar(128)   null,
    sort                smallint       null,
    praise_count        bigint(19)     null,
    play_count          bigint         null,
    duration            decimal(10, 2) null,
    status              tinyint        null,
    size                bigint         null,
    deleted             tinyint(1)     null,
    create_user_id      bigint(19)     null,
    update_user_id      bigint(19)     null,
    create_date         datetime       not null,
    update_date         datetime       null
)
    charset = utf8;

INSERT INTO db_wisdom.edu_video (id, course_id, title, video_source, video_original_name, sort, praise_count, play_count, duration, status, size, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (1, 23, '第一节 Java基础', 'https://wisdom-oss.oss-cn-beijing.aliyuncs.com/2022/03/12/4caa03f5be974f91a5463f56258987de1647067966093329.mp4', '1647067966093329.mp4', 3, null, null, 21.20, null, 3914202, 0, 1, 1, '2022-03-12 08:02:54', '2022-03-12 08:52:37');
INSERT INTO db_wisdom.edu_video (id, course_id, title, video_source, video_original_name, sort, praise_count, play_count, duration, status, size, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (2, 23, '第二节 Java泛型', 'https://wisdom-oss.oss-cn-beijing.aliyuncs.com/2022/03/12/47cffd6418ed4b0e89ed9cae8b0d586b1647067966093329.mp4', '1647067966093329.mp4', 2, null, null, 21.20, null, 3914202, 0, 1, 1, '2022-03-12 08:53:52', '2022-03-12 08:57:29');
INSERT INTO db_wisdom.edu_video (id, course_id, title, video_source, video_original_name, sort, praise_count, play_count, duration, status, size, deleted, create_user_id, update_user_id, create_date, update_date) VALUES (3, 23, '第三节 Java 网络通信', 'https://wisdom-oss.oss-cn-beijing.aliyuncs.com/2022/03/12/c375cf69aba64900a8d45f629b714f7f1647067966093329.mp4', '1647067966093329.mp4', 4, null, null, 21.20, null, 3914202, 0, 1, 1, '2022-03-12 08:58:13', '2022-03-12 09:06:46');