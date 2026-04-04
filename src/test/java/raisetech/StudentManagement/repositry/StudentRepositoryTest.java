package raisetech.StudentManagement.repositry;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(8);

  }
  @Test
  void 受講生のID検索ができること(){
    Student actual = sut.searchStudent("33");
    assertThat(actual.getId()).isEqualTo(33);

  }

  @Test
  void 受講生コースの全件検索が行えること(){
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(8);

  }

  @Test
  void 受講生IDに紐づく受講生コース情報が検索できること() {

    List<StudentCourse> actual = sut.searchStudentCourseList();

    // studentId=11のものだけ抽出
    List<StudentCourse> filtered = actual.stream()
        .filter(c -> c.getStudentId() == 33)
        .toList();

    assertThat(filtered).isNotEmpty();
  }

  @Test
  void 受講生の登録が行えること(){
    Student student = new Student();
    student.setFullName("Ami Takayama");
    student.setFurigana("Ami Takayama");
    student.setNickname("Amimaru");
    student.setEmail("Ami_takayama@example.com");
    student.setRegion("Miyagi Sendai");
    student.setAge(23);
    student.setGender("zyosei");
    student.setRemark("");
    student.setIsDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual).hasSize(9);
  }

  @Test
  void 受講生コースの登録が行えること() {
    StudentCourse course = new StudentCourse();
    course.setStudentId(33);
    course.setCourseName("AWS");
    course.setStartDate(LocalDate.now());
    course.setEndDate(LocalDate.now().plusYears(1));

    sut.registerStudentCourse(course);

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual).isNotEmpty();

    assertThat(actual.stream()
        .map(StudentCourse::getCourseName))
        .contains("AWS");
  }

  @Test
  void 受講生の更新ができること() {

    // 既存データ取得
    Student student = sut.searchStudent("33");

    // 更新内容
    student.setFullName("更新太郎");
    student.setNickname("update_nick");
    student.setEmail("update@example.com");
    student.setRegion("Tokyo");
    student.setAge(99);
    student.setGender("other");
    student.setRemark("更新テスト");

    // 更新実行
    sut.updateStudent(student);

    // 再取得
    Student updated = sut.searchStudent("33");

    // 検証
    assertThat(updated.getFullName()).isEqualTo("更新太郎");
    assertThat(updated.getNickname()).isEqualTo("update_nick");
    assertThat(updated.getEmail()).isEqualTo("update@example.com");
    assertThat(updated.getRegion()).isEqualTo("Tokyo");
    assertThat(updated.getAge()).isEqualTo(99);
    assertThat(updated.getGender()).isEqualTo("other");
    assertThat(updated.getRemark()).isEqualTo("更新テスト");
  }
  @Test
  void 受講生コースの更新ができること() {

    // 既存コース取得
    List<StudentCourse> courses = sut.searchStudentCourse(33);
    StudentCourse course = courses.get(0);

    // 更新内容
    course.setCourseName("AWS");
    course.setStartDate(LocalDate.of(2026, 3, 20));
    course.setEndDate(LocalDate.of(2027, 3, 20));

    // 更新実行
    sut.updateStudentCourse(course);

    // 再取得
    List<StudentCourse> updatedCourses = sut.searchStudentCourse(33);
    StudentCourse updated = updatedCourses.get(0);

    // 検証
    assertThat(updated.getCourseName()).isEqualTo("AWS");
    assertThat(updated.getStartDate()).isEqualTo(LocalDate.of(2026, 3, 20));
    assertThat(updated.getEndDate()).isEqualTo(LocalDate.of(2027, 3, 20));
  }
}

//todo:Git Hubに反映できているかテスト