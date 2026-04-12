package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  private Integer id;
  private Integer studentId;

   @NotBlank
   private String courseName;
   private LocalDate startDate;
  @FutureOrPresent(message = "過去の日付を入力しないでください")
   private LocalDate endDate;
   private String status;
  }

