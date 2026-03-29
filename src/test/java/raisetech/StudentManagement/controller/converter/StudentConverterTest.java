package raisetech.StudentManagement.controller.converter;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;


class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {

    sut = new StudentConverter();
  }

  @Test
  void 受講生のリストと受講生コース情報を渡して受講生リストが作成できること() {

    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(33);
    studentCourse.setStudentId(33);
    studentCourse.setCourseName("AWS");
    studentCourse.setStartDate(LocalDate.now());
    studentCourse.setEndDate(LocalDate.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);
    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡したときに紐づかない受講生コース情報は除外されること() {

    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(33);
    studentCourse.setStudentId(999);
    studentCourse.setCourseName("AWS");
    studentCourse.setStartDate(LocalDate.now());
    studentCourse.setEndDate(LocalDate.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual =
        sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEmpty();
  }

  private static @NonNull Student createStudent() {
    Student student = new Student();
    student.setId(Integer.valueOf("33"));
    student.setFullName("Ami Takayama");
    student.setFurigana("Ami Takayama");
    student.setNickname("Amimaru");
    student.setEmail("Ami_takayama@example.com");
    student.setRegion("Miyagi Sendai");
    student.setAge(23);
    student.setGender("zyosei");
    student.setRemark("");
    student.setIsDeleted(false);
    return student;
  }
}