package hello.core.discount;

import hello.core.member.domain.Grade;
import hello.core.member.domain.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;   // 1000원 할인인

   @Override
    public int discount(Member member, int price) {
       if (member.getGrade() == Grade.VIP) {
           return discountFixAmount;
       }
        return 0;
    }
}
