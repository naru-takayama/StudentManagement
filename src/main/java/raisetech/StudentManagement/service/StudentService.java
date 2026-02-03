package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repositry.StudentRepository;

//Service
//ドメインロジック、業務処理　受け取ったデータを処理できるように加工するもの
//細かい処理をしたいときに使う
@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //検索処理
    return repository.search();
  }
  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCourses();
  }
}
