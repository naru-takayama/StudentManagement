package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repositry.StudentRepository;
import raisetech.StudentManagement.domain.StudentDetail;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {
  private final StudentRepository repository;
  private StudentConverter converter;
  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {

    this.repository = repository;
    this.converter = converter;
  }
  /**
   * 受講生一覧検索です。
   *      * 全件検索を行うので、条件指定は行いません。
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    // 1. Student を DB に挿入
    repository.insertStudent(studentDetail.getStudent());

    // 2. 挿入後の ID を取得
    Integer studentId = studentDetail.getStudent().getId();
    // 3. 各コースに studentId をセットして挿入
    for (StudentsCourses course : studentDetail.getStudentsCourses()) {
      course.setStudentId(studentId);
      course.setStartDate(LocalDate.now());
      course.setEndDate(LocalDate.from(LocalDateTime.now().plusYears(1)));
    repository.insertStudentsCourses(course);
    }
    return studentDetail;
  }
  // 学生更新
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {

    //students テーブル更新
    repository.updateStudent(studentDetail.getStudent());

    //students_courses テーブル更新
    for (StudentsCourses course : studentDetail.getStudentsCourses()) {
      //studentIdを再セット
      course.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentsCourse(course);
    }
  }
  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String id) {
    // 学生を取得
    Student student = repository.searchStudentById(id);
    //コースを取得
    List<StudentsCourses> courses =
        repository.searchStudentsCoursesByStudentId(Integer.valueOf(id));
    //StudentDetailにまとめる
    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentsCourses(courses);
    return detail;
  }
}
