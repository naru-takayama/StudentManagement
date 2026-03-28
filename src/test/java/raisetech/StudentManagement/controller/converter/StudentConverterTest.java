package raisetech.StudentManagement.controller.converter;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;


class StudentConverterTest {

  private StudentConverter converter = new StudentConverter();


  @Test
  void 受講生情報と受講生コース情報が正しく紐づくこと() {

    // 複数コースありの人と一つのコースのみの人にIDをつける
    Student student1 = new Student();
    student1.setId(1);

    Student student2 = new Student();
    student2.setId(2);

    List<Student> studentList = List.of(student1, student2);

    // ID=1にした人はJava Basic、Java Basicの２つのコースがあるとする
    StudentCourse course1 = new StudentCourse();
    course1.setStudentId(1);
    course1.setCourseName("Java Basic");

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId(1);
    course2.setCourseName("Java Basic");

    // ID=２にした人はWeb Basicの１つのコースがあるとする
    StudentCourse course3 = new StudentCourse();
    course3.setStudentId(2);
    course3.setCourseName("Web Basic");

   //それぞれの受講コースデータ
   // course1, course2はid=1で受講しているコース
   // course3,はid=2で受講しているコース
    List<StudentCourse> studentCourseList =
        List.of(course1, course2, course3);

    List<StudentDetail> result =
        converter.convertStudentDetails(studentList, studentCourseList);

    assertEquals(2, result.size());

    // student_id = 1 の人を result から探している
    StudentDetail studentDetail1 = result.stream()
        .filter(sd -> sd.getStudent().getId() == 1)
        .findFirst()
        .orElseThrow();

    assertEquals(2, studentDetail1.getStudentCourseList().size());

    // student_id = ２の人を result から探している
    StudentDetail studentDetail2 = result.stream()
        .filter(sd -> sd.getStudent().getId() == 2)
        .findFirst()
        .orElseThrow();

    assertEquals(1, studentDetail2.getStudentCourseList().size());
  }

  @Test
  void 一人の受講生に複数コースが紐づくこと() {
    // 「複数コース」に特化したテスト
    Student student = new Student();
    student.setId(1);

    List<Student> studentList = List.of(student);

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId(1);
    course1.setCourseName("Java Basic");

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId(1);
    course2.setCourseName("Spring Boot");

    List<StudentCourse> studentCourseList =
        List.of(course1, course2);


    List<StudentDetail> result =
        converter.convertStudentDetails(studentList, studentCourseList);


    assertEquals(1, result.size());

    StudentDetail studentDetail = result.stream()
        .filter(sd -> sd.getStudent().getId().equals(1))
        .findFirst()
        .orElseThrow();

    assertEquals(2, studentDetail.getStudentCourseList().size());
  }

  @Test
  void 紐づくコースがない場合は空リストになること() {

    Student student = new Student();
    student.setId(1);

    List<Student> studentList = List.of(student);

    StudentCourse course = new StudentCourse();
    course.setStudentId(99); // 存在しないID

    List<StudentCourse> studentCourseList = List.of(course);

    List<StudentDetail> result =
        converter.convertStudentDetails(studentList, studentCourseList);

    assertEquals(1, result.size());
    assertTrue(result.get(0).getStudentCourseList().isEmpty());
  }

  @Test
  void 受講生が空の場合は空リストが返ること() {

    List<StudentDetail> result =
        converter.convertStudentDetails(new ArrayList<>(), new ArrayList<>());

    assertTrue(result.isEmpty());
  }
}