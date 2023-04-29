import java.time.DayOfWeek;
import java.time.LocalTime;

public class DiscountCondition {
    private DiscountConditionType type;

    // 순번 조건
    private int sequence;

    // 기간 조건
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    // 캡슐화 위반 : type이 필드로 포함된다는 사실을 인터페이스를 통해 노출
    public DiscountConditionType getType() {
        return type;
    }

    // 위의 데이터로 할 수 있는 것 : 할인 조건 만족 여부
    // 캡슐화 위반 : DayOfWeek, LocalTime이 필드로 포함된다는 사실을 인터페이스를 통해 노출
    public boolean isDiscountable(DayOfWeek dayOfWeek, LocalTime time) {
        if (type != DiscountConditionType.PERIOD) {
            throw new IllegalArgumentException();
        }

        return this.dayOfWeek.equals(dayOfWeek) &&
                this.startTime.compareTo(time) <= 0 &&
                this.endTime.compareTo(time) >= 0;
    }

    // 캡슐화 위반 : sequence가 필드로 포함된다는 사실을 인터페이스를 통해 노출
    public boolean isDiscountable(int sequence) {
        if (type != DiscountConditionType.SEQUENCE) {
            throw new IllegalArgumentException();
        }

        return this.sequence == sequence;
    }

    // 필드의 게터 세터는 원래 할인 조건 만족 여부를 외부에서 계산하기 위해 사용됐는데
    // 이 오퍼레이션을 내부에 포함했으므로 게터 세터 삭제하여 인터페이스 줄이기

}