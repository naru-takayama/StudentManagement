package raisetech.StudentManagement.repositry;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること(){
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(8);
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
    assertThat(actual.size()).isEqualTo(8);//←ここの数字いろいろ変えてもエラーがなぜでないのか今度聞く
    //todo:動画内のTest完了課題からスタート
  }
}