package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "受講生詳細")
@Getter
@Setter
public class Student {
  private Integer id;
  @NotBlank(message = "名前は必須です")
  private String fullName;
  private String furigana;
  private String nickname;
  @Email(message = "メールアドレスの形式が正しくありません")
  private String email;
  private String region;
  @NotNull(message = "年齢は必須です")
  private Integer age;
  private String gender;
  private String remark;
  private Boolean isDeleted;
}