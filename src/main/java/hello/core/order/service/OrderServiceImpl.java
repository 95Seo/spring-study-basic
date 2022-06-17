package hello.core.order.service;

import hello.core.discount.DiscountPolicy;
import hello.core.member.domain.Member;
import hello.core.member.domain.MemberRepository;
import hello.core.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

// @Autowired 는 type 으로 조회 한다.
// 조회 대상 빈이 2개 이상일 때 해결 방법
// 1. @Autowired 필드 명 매칭 - 빈 이름 매칭
// 2. @Quilifier -> @Quilifier 끼리 매칭 - 빈 이름 매칭
// -> Quilifier 이름을 찾지 못하면 동일한 이름을 가진 빈을 추가로 찾는다.
// -> 단, Quilifier는 Quilifier를 찾는 용도로만 쓰는게 명확하다.
// -> @Quilifier("이름") 이렇게 내부에 문자열로 검색하는 것이기 때문에 컴파일 에러가 안난다.
// -> 이를 해결하기 위해 에노테이션을 직접 작성해서 사용 할수도 있다.
// 3. @Primary 사용
// 우선순위를 정하는 방법이다. 여러 빈이 매칭되어 있으면 @Primary가 우선권을 가진다.

// 의존 주입 방법
// 1. 생성자 주입 (객체 생성과 동시에 주입 됨으로 넷중 가장 먼저 의존성이 주입된다.)
// 2. 수정자 주입 (Setter 주입)
// 3. 필드 주입 (권장하지 않음) -> 외부에서 변경이 불가능해서 테스트 하기가 힘들다.
// 4. 일반 메서드 주입 (수정자 주입과 비슷)
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    // 3. 필드 주입
//    @Autowired
    private final MemberRepository memberRepository;
//    @Autowired
    private final DiscountPolicy discountPolicy;

    // 1. 생성자 주입
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, /*@Qualifier("mainDiscountPolicy")*/ DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    public OrderServiceImpl() {
//    }

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
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

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
