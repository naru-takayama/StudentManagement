package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repositry.StudentRepository;
import raisetech.StudentManagement.domain.StudentDetail;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  // 学生一覧検索
  public List<Student> searchStudentList() {
    return repository.search();
  }

  // 学生のコース一覧検索
  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCourses();
  }

  // 学生登録
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    // 1. Student を DB に挿入
    repository.insertStudent(studentDetail.getStudent());

    // 2. 挿入後の ID を取得
    Integer studentId = studentDetail.getStudent().getId();
    if (studentId == null) {
      throw new IllegalStateException("Student ID was not generated after insert!");
    }

    // 3. 各コースに studentId をセットして挿入
    for (StudentsCourses course : studentDetail.getStudentsCourses()) {
      course.setStudentId(studentId);
      course.setStartDate(LocalDate.now());
      repository.insertStudentsCourses(course);
    }
  }
}
