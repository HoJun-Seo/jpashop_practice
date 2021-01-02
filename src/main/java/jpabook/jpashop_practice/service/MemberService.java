package jpabook.jpashop_practice.service;

import jpabook.jpashop_practice.domain.Member;
import jpabook.jpashop_practice.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // @Repository 어노테이션과 같이 내부에 @Componenet 어노테이션이 선언되어 있다.(컴포넌트 스캔의 대상 - 자동으로 스프링 빈에 등록된다.)
@Transactional(readOnly = true) // 기본적으로는 읽기 전용의 트랜잭션을 생성한다.
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// 회원 가입
	@Transactional // 따로 트랜잭션을 생성한 경우 위의 읽기 전용 옵션보다 우선하여 기능이 수행된다.(readOnly = false)
	public Long join(Member member){
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	// 이름이 중복되는 회원이 있는지 검증하는 메소드
	private void validateDuplicateMember(Member member) {
		// 검증결과 문제가 있을 경우 Exception 을 터트린다.
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()){
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	// 회원 전체 조회

	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	// 회원 단일 조회

	public Member findOne(Long memberId){
		return memberRepository.findOne(memberId);
	}
}
