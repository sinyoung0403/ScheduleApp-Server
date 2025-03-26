/* Essential SQL*/
use schedule;

/* Table SQL */
CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '식별자',
    task TEXT COMMENT '할일',
    author CHAR(10) COMMENT '작성자명',
    pwd CHAR(20) COMMENT '비밀번호',
    created DATETIME COMMENT '작성일',
    updated DATETIME COMMENT '수정일'
);

/* DATA INSERT SQL */
INSERT INTO schedule.schedule (id, task, author, pwd, created, updated) VALUES (1, '제목A', 'A', 'pwd', '2025-03-20 20:08:56', '2025-03-20 20:08:56');
INSERT INTO schedule.schedule (id, task, author, pwd, created, updated) VALUES (2, '제목A', 'A', 'pwd', '2025-03-20 20:09:48', '2025-03-20 20:09:48');
INSERT INTO schedule.schedule (id, task, author, pwd, created, updated) VALUES (3, '제목B', 'B', 'pwd', '2025-03-20 20:11:33', '2025-03-21 21:00:08');
INSERT INTO schedule.schedule (id, task, author, pwd, created, updated) VALUES (4, '제목B', 'B', 'pwd', '2025-03-21 12:29:47', '2025-03-21 17:15:51');
INSERT INTO schedule.schedule (id, task, author, pwd, created, updated) VALUES (6, '제목C', 'C', 'pwd', '2025-03-21 17:04:57', '2025-03-21 17:04:57');
INSERT INTO schedule.schedule (id, task, author, pwd, created, updated) VALUES (8, '제목C', 'C', 'pwd', '2025-03-24 09:55:10', '2025-03-24 09:56:01');
