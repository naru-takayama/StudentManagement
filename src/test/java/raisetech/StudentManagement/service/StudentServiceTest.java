package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repositry.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;
  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索＿リポジトリとコンバーターの処理が適切に呼び出せていること(){
    //sutという名前にしているのはテスト対象という意味であえてServiseではなくsutにしている
    //事前準備
    List<Student> studentList =new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository,times(1)).search();
    verify(repository,times(1)).searchStudentCourseList();
    verify(converter,times(1)).convertStudentDetails(studentList,studentCourseList);
  }

  @Test
  void 受講生詳細の検索studentIdと紐づく受講生コース情報を適切に呼び出していること(){

    String studentId = "26";
    Integer id = Integer.valueOf(studentId);

    Student student = new Student();
    student.setId(id);

    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.searchStudent(studentId)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(studentCourseList);

    StudentDetail actual = sut.searchStudent(studentId);

    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentCourse(id);

    assertEquals(id, actual.getStudent().getId());
  }

  @Test
  void 受講生詳細の新規登録＿リポジトリの処理が適切に呼び出せていること(){

    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();

    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourseList);

    StudentDetail actual = sut.registerStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);

    assertEquals(student, actual.getStudent());
  }

  @Test
  void 受講生詳細の更新＿指定したIdの更新処理が呼び出されていること(){

    Student student = new Student();
    student.setId(26);

    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }
}