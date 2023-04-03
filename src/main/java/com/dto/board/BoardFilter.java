package com.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BoardFilter {
  private WorkPeriod[] workPeriods;
  private WorkDays[] workDays;
  private WorkHours[] workHours;

  public enum WorkPeriod {
    ONE_DAY("1일"),
    ONE_WEEK("1주일 이내"),
    ONE_WEEK_TO_ONE_MONTH("1주일~1개월"),
    ONE_TO_THREE_MONTHS("1개월~3개월"),
    THREE_TO_SIX_MONTHS("3개월~6개월"),
    SIX_MONTHS_TO_ONE_YEAR("6개월~1년"),
    OVER_ONE_YEAR("1년 이상");

    private final String label;

    WorkPeriod(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }

  public enum WorkDays {
    WEEKDAYS("평일(월,화,수,목,금)"),
    WEEKENDS("주말(토,일)"),
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일"),
    NEGOTIABLE("요일 협의");

    private final String label;

    WorkDays(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }

  public enum WorkHours {
    MORNING("오전"),
    MORNING_TO_AFTERNOON("오전~오후"),
    ALL_DAY("종일"),
    AFTERNOON("오후"),
    AFTERNOON_TO_EVENING("오후~저녁"),
    NEGOTIABLE("시간 협의"),
    EVENING("저녁"),
    EVENING_TO_DAWN("저녁~새벽"),
    DAWN("새벽"),
    DAWN_TO_MORNING("새벽~오전");

    private final String label;

    WorkHours(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }
}