package raisetech.StudentManagement.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新を行うControllerです。
 * todo:講座通りに修正済み
 */
@RestController
public class StudentController {
  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   * @return  受講生一覧（全件）
   * todo:講座通りに修正済み
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索です。
   * ID　に紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   * todo:講座通りに編集済み
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id) {
    return service.searchStudent(id);
  }

  /**
   * 新規登録処理
   * todo:講座通りに編集済み
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail reseponseStudentDetail =service.registerStudent(studentDetail);
    return ResponseEntity.ok (reseponseStudentDetail);
  }
  /**
   * 更新処理
   * todo:講座通りに編集済み
   */
  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok ("更新処理が成功しました。");
  }
}