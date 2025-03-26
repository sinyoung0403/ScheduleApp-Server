use schedule;

-- author TABLE SQL
create table author
(
    id      bigint auto_increment comment '작성자 식별자'
        primary key,
    name    char(10)  null comment '작성자명',
    email   char(100) null comment '이메일',
    created datetime  null comment '작성일',
    updated datetime  null comment '수정일',
    constraint check_name
        check (true)
);

-- DATA INSERT SQL
INSERT INTO plan.author (id, name, email, created, updated) VALUES (1, 'A', 'aa@aa.com', '2025-03-25 15:41:44', '2025-03-25 15:41:46');
INSERT INTO plan.author (id, name, email, created, updated) VALUES (2, 'B', 'bb@bb.com', '2025-03-25 15:41:41', '2025-03-25 15:41:45');
INSERT INTO plan.author (id, name, email, created, updated) VALUES (3, 'C', 'cc@cc.com', '2025-03-24 16:10:22', '2025-03-24 17:29:50');
INSERT INTO plan.author (id, name, email, created, updated) VALUES (4, 'D', 'dd@ddd.com', '2025-03-24 16:11:22', '2025-03-24 16:11:22');
INSERT INTO plan.author (id, name, email, created, updated) VALUES (5, 'E', 'ee@ee.com', '2025-03-25 20:11:18', '2025-03-25 22:28:57');
INSERT INTO plan.author (id, name, email, created, updated) VALUES (6, 'B', 'BBB@BB.com', '2025-03-25 20:17:30', '2025-03-25 20:17:30');
INSERT INTO plan.author (id, name, email, created, updated) VALUES (7, 'B', 'BBB@BB.com', '2025-03-25 20:17:39', '2025-03-25 20:17:39');
INSERT INTO plan.author (id, name, email, created, updated) VALUES (8, 'G', 'ggg@GG.com', '2025-03-25 22:31:38', '2025-03-25 22:31:38');