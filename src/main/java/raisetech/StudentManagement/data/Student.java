package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

  @Getter
  @Setter
  public class Student {
  private Integer id;
  private String fullName;//「　_　」がついていた名前は「　_　」の後に大文字にすること！
  private String furigana;
  private String nickname;
  private String email;
  private String region;
  private Integer age;
  private String  gender;
  private String remark;  //備考欄
  private Boolean isDeleted = false;  //削除フラグ
}
