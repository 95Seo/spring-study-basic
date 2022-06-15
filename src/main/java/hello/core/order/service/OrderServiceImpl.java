package hello.core.order.service;

import hello.core.discount.DiscountPolicy;
import hello.core.member.domain.Member;
import hello.core.member.domain.MemberRepository;
import hello.core.order.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 의존 주입 방법
// 1. 생성자 주입 (객체 생성과 동시에 주입 됨으로 넷중 가장 먼저 의존성이 주입된다.)
// 2. 수정자 주입 (Setter 주입)
// 3. 필드 주입 (권장하지 않음) -> 외부에서 변경이 불가능해서 테스트 하기가 힘들다.
// 4. 일반 메서드 주입 (수정자 주입과 비슷)
@Component
public class OrderServiceImpl implements OrderService {

    // 3. 필드 주입
//    @Autowired
    private MemberRepository memberRepository;
//    @Autowired
    private DiscountPolicy discountPolicy;

    // 1. 생성자 주입
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    public OrderServiceImpl() {
    }

    // 2. 수정자 주입
    // required = false 필수가 아닌 의존 주입
//    @Autowired(required = false)
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    // 4. 일반 메서드 주입
    // 한번에 여러 필드를 주입 받을 수 있다.
    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
