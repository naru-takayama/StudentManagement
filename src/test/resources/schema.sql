-- students データ挿入（修正済）
--- =========================
-- students（生徒テーブル）
-- =========================
CREATE TABLE students (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          full_name VARCHAR(100),
                          furigana VARCHAR(100),
                          nickname VARCHAR(100),
                          email VARCHAR(100),
                          region VARCHAR(100),
                          age INT,
                          gender VARCHAR(10),
                          remark VARCHAR(255),
                          is_deleted BOOLEAN
);

-- =========================
-- students_courses（コーステーブル）
-- =========================
CREATE TABLE students_courses (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  student_id INT,
                                  course_name VARCHAR(100),
                                  start_date DATE,
                                  end_date DATE,
                                  status VARCHAR(20)
);