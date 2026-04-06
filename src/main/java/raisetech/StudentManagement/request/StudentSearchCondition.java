package raisetech.StudentManagement.request;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "受講生検索条件")
@Getter
@Setter
public class StudentSearchCondition {

  private String fullName;
  private String furigana;
  private String nickname;
  private String email;
  private String region;
  private String gender;

  private Integer minAge;
  private Integer maxAge;

  /**
   * 検索条件が何も指定されていないか判定
   */
  public boolean isEmpty() {
    return (fullName == null || fullName.isBlank()) &&
        (furigana == null || furigana.isBlank()) &&
        (nickname == null || nickname.isBlank()) &&
        (email == null || email.isBlank()) &&
        (region == null || region.isBlank()) &&
        (gender == null || gender.isBlank()) &&
        minAge == null &&
        maxAge == null;
  }
}
