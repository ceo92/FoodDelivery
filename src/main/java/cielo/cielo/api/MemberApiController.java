package cielo.cielo.api;

import cielo.cielo.domain.Member;
import cielo.cielo.mvc.dto.MemberSaveDTO;
import cielo.cielo.mvc.dto.MemberUpdateDTO;
import cielo.cielo.mvc.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "Member", description = "회원 관리")
public class MemberApiController {

  private final MemberService memberService;

  @Operation(summary = "회원 전체 조회")
  @GetMapping
  public Response findMembers(){
    List<Member> members = memberService.findMembers();
    List<SelectMemberResponse> list = members.stream().map(member ->
        new SelectMemberResponse(member.getId(), member.getName(), member.getAddress().getCity(),
            member.getAddress().getStreet(),
            member.getAddress().getZipcode())).toList();

    return new Response(list);
  }

/*  @Operation(summary = "회원 조회")
  @GetMapping("{id}")
  public CreateMemberResponse findMember(@PathVariable Long id){
    Member member = memberService.findOne(id);
    return new CreateMemberResponse(id, member.getName(), member.getAddress().getCity(), member.getAddress().getStreet(), member.getAddress().getZipcode());
  }*/

  @Operation(summary = "회원 등록")
  @PostMapping("v1")
  public CreateMemberResponse saveMemberV1(@Validated @RequestBody MemberSaveDTO memberSaveDTO ){
    Long joinId = memberService.join(memberSaveDTO);
    return new CreateMemberResponse(joinId, memberSaveDTO.getName(), memberSaveDTO.getCity(),
        memberSaveDTO.getStreet(), memberSaveDTO.getZipcode());
  }

  @Operation(summary = "회원 수정")
  @PutMapping("{id}")
  public UpdateMemberResponse updateMemberV1(@Validated @RequestBody MemberUpdateDTO memberUpdateDTO, BindingResult bindingResult, @PathVariable("id") Long id){
    memberService.update(memberUpdateDTO, id);
    return new UpdateMemberResponse(id, memberUpdateDTO.getName(), memberUpdateDTO.getCity(),
        memberUpdateDTO.getStreet(), memberUpdateDTO.getZipcode());
  }


  // 여기서의 결론: 요청, 응답 둘 다 DTO로, 여기서만 쓰면 내부클래스로, 요청은 @RequestBody, 응답은 @ResponseBody, 리스트같은 경우는 그냥 DTO 리스트로 던지면 JSON 형식이 아니므로 반드시 Result같은 제네릭 클래스로 감싸서 던지기



  // 이런 DTO 같은거는 안에서만 쓰니 그냥 내부 클래스로 정의하자!
  @Data
  @AllArgsConstructor
  static class CreateMemberResponse {

    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
  }

  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse {

    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
  }

  @Data
  @AllArgsConstructor
  static class SelectMemberResponse{
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
  }

  @Data
  @AllArgsConstructor
  static class Response<T>{

    T data;
  }
}
