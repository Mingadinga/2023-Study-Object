/*
데이터 중심 설계가 변경에 취약한 이유
- 캡슐화 위반 : 이름과 타입을 통해 인터페이스에 구현을 드러냄. 접근자와 수정자에 과도하게 의존하는 설계 방식 - 추측에 의한 설계 전략
- 높은 결합도 : getFee가 제대로 캡슐화되지 않았으므로, Fee의 타입이 바뀌면 getFee애 의존하는 구현도 함께 변경됨
- 낮은 응집도 : ReservationAgency는 변경의 이유가 여러가지. 참고로 SRP의 책임은 변경의 이유를 말하며 객체의 행동을 나타내는 용어가 아니다.
 */


public class ReservationAgency { // 영화 예매 절차를 구현하는 프로시저 클래스
    public Reservation reserve(Screening screening, Customer customer,
                               int audienceCount) {
        Movie movie = screening.getMovie();

        // 할인 가능 여부 확인
        boolean discountable = false;
        for(DiscountCondition condition : movie.getDiscountConditions()) {
            if (condition.getType() == DiscountConditionType.PERIOD) {
                discountable = screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
                        condition.getStartTime().compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                        condition.getEndTime().compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
            } else {
                discountable = condition.getSequence() == screening.getSequence();
            }

            if (discountable) {
                break;
            }
        }

        // 할인 요금 계산
        Money fee;
        if (discountable) {
            Money discountAmount = Money.ZERO;
            switch(movie.getMovieType()) {
                case AMOUNT_DISCOUNT: // 고정 금액
                    discountAmount = movie.getDiscountAmount();
                    break;
                case PERCENT_DISCOUNT: // 비율 금액
                    discountAmount = movie.getFee().times(movie.getDiscountPercent());
                    break;
                case NONE_DISCOUNT: // 할인 안함
                    discountAmount = Money.ZERO;
                    break;
            }

            fee = movie.getFee().minus(discountAmount).times(audienceCount);
        } else {
            fee = movie.getFee().times(audienceCount);
        }

        return new Reservation(customer, screening, fee, audienceCount);
    }
}