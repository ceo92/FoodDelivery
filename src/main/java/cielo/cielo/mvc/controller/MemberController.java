package cielo.cielo.mvc.controller;

import cielo.cielo.domain.Member;
import cielo.cielo.mvc.dto.MemberSaveDTO;
import cielo.cielo.mvc.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/save")
  public String saveMember(Model model) {
    log.info("asdasdsafa");
    model.addAttribute("member", new Member()); // 저장 시 값 유지용
    return "member/saveForm";
  }
  @PostMapping("/save")
  public String saveMember(@ModelAttribute("member") MemberSaveDTO memberSaveDto){
    log.info(memberSaveDto.getCity());
    log.info(memberSaveDto.getName());
    log.info(memberSaveDto.getStreet());
    log.info(memberSaveDto.getZipcode());
    Long saveId = memberService.join(memberSaveDto);
    return "redirect:/";
  }

  public String findMembers(Model model){
    List<Member> members = memberService.findMembers();
    model.addAttribute("members", members);
    return "member/members";
  }

}
