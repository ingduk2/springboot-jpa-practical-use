package com.jpabook.jpashop.Service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional //JPA 모든 것들은 트랜잭션 안에 있어야 한다. javax 보다 spring것 사용
@Transactional(readOnly = true)
//lombok
//@AllArgsConstructor //모든필드 생성자 생성
@RequiredArgsConstructor //final 있는것만 생성자 생성
public class MemberService {

//    @Autowired //field Injection 단점 - 테스트할때나 변경할때 힘듬.
    private final MemberRepository memberRepository; // final 넣는걸 추천. 확인가능

//    @Autowired //setter intection 단점 - 런타임에 누군가가 바꿀수가 있다.
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    @Autowired //constructor Injection 중간에 바꿀 수 없다. 생성자 하나있는경우는 생략 가능
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //was 여러개 뜨고, 동시에 insert하면 걸린다. 그래서 name을 unique로 쓰던가하는 방법 사용.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
//    @Transactional(readOnly = true) //조회의 경우 성능 최적화 가능.
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

//    @Transactional(readOnly = true) //조회의 경우 성능 최적화 가능.
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        //dirty checking
        Member member = memberRepository.findOne(id);
        member.setName(name);
        //종료되면 aop transactional 끝나면 flush , commit
    }
}
