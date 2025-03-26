|                                          📅 ScheduleApp-Server 📅                                          |  
|:------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/3f0705da-87a1-4590-9731-7c6942571f74" width="200"> |


---

## 🧑‍💻 개발자 소개

|  |                                   팀원                                   |
|:-------------:|:----------------------------------------------------------------------:|
|프로필| ![image](https://avatars.githubusercontent.com/u/94594402?v=4&size=64) |
|이름|                                  박신영                                   |
|GitHub|                              sinyoung0403                              |
|기술블로그|                 [블로그](https://sintory-04.tistory.com/)                 |

---

## ⚒ 프로젝트

### 1. 프로젝트 이름

- **" ScheduleApp-Server "**

### 2. 프로젝트 소개

- 이 프로젝트는 일정과 작성자를 관리할 수 있는 백엔드 API 서버입니다.
- Spring Boot와 MySQL을 사용하여 일정 및 작성자의 CRUD 기능을 제공합니다.
- git brunch 중 "essentail" 은 필수과제, "challenge" 는 도전과제를 뜻합니다.

---

## 🚀 프로젝트 실행 방법

### 1. Git clone

```bash
git clone https://github.com/sinyoung0403/ScheduleApp-Server.git
cd ScheduleApp-Server
```

### 2. My SQL 설정 ( DB 실행 )

- MySQL 이 설치되어 있어야 합니다.
- `src/main/resources/sql` 에 있는 sql 파일들을 실행시켜주세요.
- 실행 순서 1. `schedule.sql` -> 2. `plan-authortable.sql` -> 3. `schedule.sql` 

### 3. 환경변수 설정

- `src/main/resources/application.properties` 에 있는 properties 파일을 본인의 DB 정보에 맞게 수정해주세요.
- `spring.datasource.username=root` root 를 본인의 username 으로 수정해주세요.
- `spring.datasource.password=1111` 1111 를 본인의 비밀번호로 수정해주세요.

### 4. Run ScheduleAppServerApplication

- `src/main/java/com/example/scheduleappserver/ScheduleAppServerApplication.java` 를 실행해주세요.
- 상단의 Run 버튼을 클릭하거나, Shift+F10 을 눌러주세요.


### 5. PostMan Test

- `src/main/resources/schedule.postman_collection.json` 를 통해서 Postman json 을 import 를 해주면 됩니다. 

---

## 📌 API 명세

### 1. 일정 API

![일정API](https://github.com/user-attachments/assets/5dc72a18-c564-42e9-8c3d-2deec09b70e4)

### 2. 작성자 API

![작성자 API](https://github.com/user-attachments/assets/8a559907-9d56-48f8-8f1d-c4bb249e02a0)


---

## 🔗 ERD


### 1. 필수 과제

![ERD Table](https://github.com/user-attachments/assets/83159944-9a94-4aed-b6f3-e7433a79e386)

### 2. 도전 과제

![Plan ERD](https://github.com/user-attachments/assets/0bb884cd-dfaa-4679-8ec1-76f644af7e2f)

---

## 🗄️ 데이터베이스 스키마 (SQL Query)

### 1. [필수] schedule Table

```mysql
CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '식별자',
    task TEXT COMMENT '할일',
    author CHAR(10) COMMENT '작성자명',
    pwd CHAR(20) COMMENT '비밀번호',
    created DATETIME COMMENT '작성일',
    updated DATETIME COMMENT '수정일'
);
```
### 2. [도전] Plan Table

```mysql
create table plan
(
    id       bigint auto_increment comment '할일 식별자'
        primary key,
    authorId bigint    comment '작성자 식별자',
    task     text      comment '할일',
    pwd      char(20)  comment '비밀번호',
    created  timestamp comment '작성일',
    updated  timestamp comment '수정일',
    constraint authorId_FK
        foreign key (authorId) references author (id)
            on update cascade on delete cascade
);
```

### 3. [도전] Author Table

```mysql
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
```


---

## 📜 PostMan Test

### 1. Author

#### 1) 작성자 추가

![image](https://github.com/user-attachments/assets/1b4e571b-cedf-444c-af1c-f488072001e6)

#### 2) 작성자 전부 조회

![image](https://github.com/user-attachments/assets/7263c306-533c-48db-8550-342c4659c2f9)

#### 3) 작성자 단건 조회

![image](https://github.com/user-attachments/assets/17d9e056-b2f0-466b-8bb5-c842612e268a)

#### 4) 작성자 수정

![image](https://github.com/user-attachments/assets/3ca5a079-88bd-43e9-9565-3887e1d2ccef)

#### 5) 작성자 삭제

![image](https://github.com/user-attachments/assets/ae0b92ff-2b59-4368-87d0-94eb0edd9715)

### 2. Plan

#### 1) 할일 추가

![image](https://github.com/user-attachments/assets/2faaba39-f1d4-49b8-8a2e-297a85648918)

#### 2) 할일 전부 조회 (작성자 이름과 수정일을 기준으로)

![image](https://github.com/user-attachments/assets/06232449-24cb-4824-bc81-5856ef0cf59a)

#### 3) 할일 단건 조회 (할일의 식별자)

![image](https://github.com/user-attachments/assets/5b62d3a4-b9d8-4f16-9a49-6492d00f98ba)

#### 4) 할일 단건 조회 (작성자의 식별자)

![image](https://github.com/user-attachments/assets/5c036eb4-bb2d-4c47-bdfc-84b3dae0049a)

#### 5) 할일 수정

![image](https://github.com/user-attachments/assets/1c0624a8-88a4-4e20-8413-6c5075956547)

#### 6) 할일 삭제

![image](https://github.com/user-attachments/assets/ea293b06-9eac-472e-bd78-fc17365ccb29)

### 3. Error

#### 1) MissingServletRequestParameterException

![파라미터가 없을 경우](https://github.com/user-attachments/assets/63f9e49f-6049-47d3-bb92-e1f9e08e4854)

#### 2) InvalidPasswordException

![비밀번호가 잘못되었을 경우](https://github.com/user-attachments/assets/952b3c7c-dec6-4a48-b220-fd7719826961)

#### 3) DataNotFoundException // 데이터 조회 불가

![데이터가 없을 경우](https://github.com/user-attachments/assets/ad276278-0eae-482a-b2ae-29c16b605120)

#### 4) ConstraintViolationException

![validated 에러](https://github.com/user-attachments/assets/05e8bddd-1ac0-47b9-a99f-496532140e47)

#### 5) MethodArgumentNotValidException

![valid 에러](https://github.com/user-attachments/assets/8a658987-d463-47ff-97af-43b2677d8374)

#### 6) InvalidInputException

![InvalidInputException](https://github.com/user-attachments/assets/05e790d6-c969-40e7-83f4-e2ab4b734b2d)
