package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "受講生詳細")
@Getter
@Setter
public class Student {
  @Min(value = 1, message = "1以上入力してください。")
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
