package raisetech.StudentManagement.repositry;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
  //登録用
  @Select("SELECT * FROM students WHERE is_deleted = false")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  //更新用
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudentById(String id);

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchStudentsCoursesByStudentId(Integer studentId);

  //学生登録用
  @Insert("INSERT INTO students(full_name, furigana, nickname, email, region, age, gender, remark, is_deleted) "
      + "VALUES(#{fullName}, #{furigana}, #{nickname}, #{email}, #{region},#{age}, #{gender}, #{remark}, "
      + "COALESCE(#{isDeleted}, false))")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id, course_name, start_date, end_date)"
      + " VALUES(#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudentsCourses(StudentsCourses studentsCourses);

  //学生更新用
  @Update("UPDATE students SET full_name = #{fullName}, furigana = #{furigana}, nickname = #{nickname}, "
      + "email = #{email}, region = #{region}, age = #{age}, gender = #{gender}, "
      + "remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name = #{courseName}, start_date = #{startDate}, "
      + "end_date = #{endDate} WHERE id = #{id}")
  void updateStudentsCourse(StudentsCourses course);
}
