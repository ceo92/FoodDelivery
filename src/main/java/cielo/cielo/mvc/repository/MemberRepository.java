package cielo.cielo.mvc.repository;

import cielo.cielo.domain.Member;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  private final EntityManager em;

  public Long save(Member member) {
    em.persist(member);
    return member.getId();
  }

  public Optional<Member> findById(Long id){
    return Optional.ofNullable(em.find(Member.class, id));
  }

  public List<Member> findAll(){
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }

  public Optional<Member> findByName(String name){
    return em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList().stream().findAny();
  }
}
