package raisetech.StudentManagement.controller;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import raisetech.StudentManagement.request.StudentSearchCondition;

/**
 * 受講生の検索や登録、更新を行うControllerです。
 */
@Validated
@RestController
public class StudentController {
  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {

    this.service = service;
  }

  /**
   * 受講生詳細一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   * @return  受講生詳細一覧（全件）
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() { //throws TestException {
    return service.searchStudentList();
  }
   // throw new TestException("エラーが発生しました。");

  /**
   * 受講生詳細検索です。
   * ID　に紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   *
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Size(min = 1, max = 3) String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います
   *
   * @param studentDetail 受講生詳細
   * @return  実行結果
     */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@Valid @RequestBody StudentDetail studentDetail) {
    StudentDetail reseponseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(reseponseStudentDetail);
  }
  /**
   * 受講生詳細の更新を行います。
   * キャンセルフラグの更新もここで行います（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return  実行結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok ("更新処理が成功しました。");
  }

  @GetMapping("/students")
  public List<Student> searchStudents(StudentSearchCondition condition) {
    return service.searchStudent(String);
  }
}