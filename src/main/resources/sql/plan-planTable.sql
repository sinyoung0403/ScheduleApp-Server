use plan;

-- plan TABLE SQL
create table plan
(
    id       bigint auto_increment comment '할일 식별자'
        primary key,
    authorId bigint    null comment '작성자 식별자',
    task     text      null comment '할일',
    pwd      char(20)  null comment '비밀번호',
    created  timestamp null comment '작성일',
    updated  timestamp null comment '수정일',
    constraint authorId_FK
        foreign key (authorId) references author (id)
            on update cascade on delete cascade
);

-- DATA INSERT SQL
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (5, 3, '2 작성자가 작성했습니다.', 'pwd', '2025-03-24 17:18:40', '2025-03-24 17:18:42');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (8, 4, '수정이지롱', 'pwd', '2025-03-24 17:28:41', '2025-03-24 18:34:44');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (9, 4, '작성자가 작성했습니다.', 'pwd', '2025-03-24 17:28:41', '2025-03-24 17:28:44');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (10, 4, '작성자가 작성했습니다.', 'pwd', '2025-03-24 17:28:42', '2025-03-24 17:28:44');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (11, 4, '작성자가 작성했습니다.', 'pwd', '2025-03-24 17:24:44', '2025-03-24 17:24:44');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (12, 4, '수정이지롱', 'pwd', '2025-03-25 15:42:23', '2025-03-25 17:22:40');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (13, 2, '작성했습니다.', 'pwd', '2025-03-25 15:42:25', '2025-03-25 15:42:27');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (14, 3, '작성했습니다.', 'pwd', '2025-03-25 15:42:28', '2025-03-25 15:42:29');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (15, 4, '작성했습니다.', 'pwd', '2025-03-25 15:42:29', '2025-03-25 15:42:30');
INSERT INTO plan.plan (id, authorId, task, pwd, created, updated) VALUES (16, 1, '작성했습니다.', 'pwd', '2025-03-25 15:42:31', '2025-03-25 15:42:31');