package raisetech.StudentManagement.controller;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    when(service.searchStudentList(any())).thenReturn(List.of(new StudentDetail()));

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList(any());
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = "33";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }


  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
    //リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    //本来であれば返りは登録されたデータが入るが、モック化すると意味がないためレスポンスは作らない。
    mockMvc.perform(
            post("/registerStudent").contentType(String.valueOf(MediaType.APPLICATION_JSON)).content(
                """
                    
                    {
                       "student": {
                         "fullName": "Ami Takayama",
                         "furigana": "Ami Takayama",
                         "nickname": "Amimaru",
                         "email": "Ami_takayama@example.com",
                         "region": "Miyagi Sendai",
                         "age": 23,
                         "gender": "zyosei",
                         "remark": "",
                         "isDeleted": false
                       },
                       "studentCourseList": [
                         {
                           "courseName": "AWS",
                           "startDate": "2026-03-20",
                           "endDate": "2027-03-20",
                           "status": "jukou_shuryou"
                         }
                       ]
                     }
                    """
            ))
        .andExpect(status().isOk());
    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できて空で返ってくること() throws Exception {

    mockMvc.perform(put("/updateStudent")
            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
            .content(
                """
                    {
                       "student": {
                         "id": 33,
                         "fullName": "Ami Takayama",
                         "furigana": "Ami Takayama",
                         "nickname": "Amimaru",
                         "email": "Ami_takayama@example.com",
                         "region": "Miyagi Sendai",
                         "age": 23,
                         "gender": "zyosei",
                         "remark": "",
                         "isDeleted": false
                       },
                       "studentCourseList": [
                         {
                           "id": 11,
                           "studentId": 33,
                           "courseName": "AWS",
                           "startDate": "2026-03-20",
                           "endDate": "2027-03-20",
                           "status": "jukou_shuryou"
                         }
                       ]
                     }
                    """
            ))
        .andExpect(status().isOk());
    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {

    Student student = new Student();
    student.setId(Integer.valueOf("33"));
    student.setFullName("Ami Takayama");
    student.setFurigana("Ami Takayama");
    student.setNickname("Amimaru");
    student.setEmail("Ami_takayama@example.com");
    student.setAge(23);
    student.setRegion("Miyagi Sendai");
    student.setGender("zyosei");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生のコース情報で終了日が過去の日付で入力してきた時に入力チェックに掛かること() {

    StudentCourse studentcourse = new StudentCourse();
    studentcourse.setId(33);
    studentcourse.setStudentId(33);
    studentcourse.setCourseName("AWS");
    studentcourse.setStartDate(LocalDate.now());
    studentcourse.setEndDate(LocalDate.now().minusDays(1));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentcourse);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("過去の日付を入力しないでください");
  }
}
