package cielo.cielo.mvc.service;

import cielo.cielo.domain.Address;
import cielo.cielo.domain.Member;
import cielo.cielo.mvc.dto.MemberSaveDTO;
import cielo.cielo.mvc.dto.MemberUpdateDTO;
import cielo.cielo.mvc.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Long join(MemberSaveDTO memberSaveDTO) {
    Member member = memberSaveDTO.toEntity();
    validateDuplicateMember(member.getName());
    return memberRepository.save(member);
  }

  @Transactional
  public void update(MemberUpdateDTO memberUpdateDTO, Long id){
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다."));
    member.update(memberUpdateDTO.getName(), new Address(memberUpdateDTO.getName(),
        memberUpdateDTO.getStreet(), memberUpdateDTO.getZipcode()));
  }

  private void validateDuplicateMember(String name) {
    memberRepository.findByName(name).ifPresent(a -> {
      throw new IllegalArgumentException(a+"이름은 중복된 이름입니다.");
    });
  }

  public List<Member> findMembers(){
    return memberRepository.findAll();
  }

  public Member findOne(Long id){
    return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다!"));
  }

}
