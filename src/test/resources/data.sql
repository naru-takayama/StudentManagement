INSERT INTO students(id,full_name, furigana, nickname, email, region, age, gender, remark, is_deleted)
VALUES (1, 'Taro Yamada', 'Taro Yamada', 'TaroYama', 'taro.yamada@example.com', 'Tokyo Shinjuku',35, 'dansei', NULL, 0),
       (2, 'Hanako Sato', 'Hanako Sato', 'Hana', 'hanako.sato@example.com', 'Kanagawa Yokohama', 28,'zyosei', NULL, 0),
       (3, 'Ichiro Suzuki', 'Ichiro Suzuki', 'Ichi', 'ichiro.suzuki@example.com', 'Osaka Osaka', 32,'dansei', NULL, 0),
       (4, 'Misaki Takahashi', 'Misaki Takahashi', 'Misa', 'misaki.takahashi@example.com','Aichi Nagoya', 22, 'zyosei', NULL, 0),
       (5, 'Yu Ito', 'Yu Ito', 'Yu', 'yu.itoo@example.com', 'Fukuoka Fukuoka', 28, 'dansei', NULL,0),
       (21, 'narumi takayama', 'narumi takayama', 'naru', 'naru.takayama@gmail.com','Miyagi Seindai', 25, 'zyosei', NULL, 0),
       (32, 'test', 'test', 'test', 'test@test.com', 'test', 20, 'test', '', 0),
       (33, 'Ami Takayama', 'Ami Takayama', 'Amimaru', 'Ami_takayama@example.com', 'Miyagi Sendai',23, 'zyosei', '', 0);

INSERT INTO students_courses(id, student_id, course_name, start_date, end_date)
VALUES(1,1,'Java Basic','2025-04-01','2025-06-30'),
      (2,1,'Spring Boot','2025-07-01','2025-09-30'),
      (3,2,'Web Basic','2025-04-01','2025-06-30'),
      (4,3,'Java Advanced','2025-05-01','2025-07-31'),
      (5,4,'MySQL','2025-06-01','2025-08-31'),
      (9,21,'Java Standard','2026-02-22','2027-02-22'),
      (10,32,'AWS','2026-03-20','2027-03-20'),
      (11,33,'AWS','2026-03-20','2027-03-20');

ALTER TABLE students ALTER COLUMN id RESTART WITH 100;
ALTER TABLE students_courses ALTER COLUMN id RESTART WITH 100;