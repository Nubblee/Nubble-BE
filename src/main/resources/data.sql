INSERT INTO users (username, password, nickname)
VALUES ('test', '1234', '성오수민지영'),
       ('ssuminii', '990430', '김수민'),
       ('jizerozz', '051127', '박지영'),
       ('sonseongoh', '960123', '손성오'),
       ('biddan606', '960606', '유원우');

INSERT INTO coding_problems (quiz_date, user_id, title, url)
VALUES ('2024-10-06', 2, 'Lv0. 문자열의 뒤의 n글자', 'https://school.programmers.co.kr/learn/courses/30/lessons/181910'),
       ('2024-10-05', 2, 'Lv1. 핸드폰 번호 가리기', 'https://school.programmers.co.kr/learn/courses/30/lessons/12948');

INSERT INTO categories (name, parent_category_id)
VALUES ('스터디', NULL),
       ('코딩 테스트', NULL);

INSERT INTO boards (name, category_id)
VALUES ('lv0', 1),
       ('lv1', 1),
       ('lv2', 1),
       ('lv3', 1);
