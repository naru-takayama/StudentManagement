package raisetech.StudentManagement.repositry;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ。
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  @Insert("INSERT INTO students(id,full_name, furigana, nickname, email, region, age, gender, remark, is_deleted)VALUES(#{id},#{fullName}, #{furigana}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses(id,student_id, course_name, start_date, end_date) VALUES(#{id},#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudentsCourses(StudentsCourses studentsCourses);



}
