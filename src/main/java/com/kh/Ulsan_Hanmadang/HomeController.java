package com.kh.Ulsan_Hanmadang;

import com.kh.Ulsan_Hanmadang.domain.member.Member;
import com.kh.Ulsan_Hanmadang.domain.member.svc.MemberSVC;
import com.kh.Ulsan_Hanmadang.web.form.member.LoginForm;
import com.kh.Ulsan_Hanmadang.web.session.LoginMember;
import com.kh.Ulsan_Hanmadang.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

  final MemberSVC memberSVC;

  @GetMapping
  private String home() {

    return "member/beforeLogin";
  }

  //로그인 화면
  @GetMapping("/login")
  public String loginForm(@ModelAttribute("form") LoginForm loginForm){

    return "member/login";
  }


  //로그인 처리
  @PostMapping("/login")
  public String login(@Valid @ModelAttribute("form") LoginForm loginForm,
                      BindingResult bindingResult,
                      HttpServletRequest request,
                      @RequestParam(value = "requestURI",required = false,defaultValue = "/") String requestURI){

    //기본 검증
    if (bindingResult.hasErrors()) {
      //log.info("bindingResult={}",bindingResult);
      return "member/login";
    }

    //회원유무
    Optional<Member> member = memberSVC.login(loginForm.getEmail(), loginForm.getPassword());
    //log.info("member={}", member);
    if(member.isEmpty()){
      bindingResult.reject("LoginForm.login","회원정보가 없습니다.");
      return "member/login";
    }

    //회원인경우
    Member findedMember = member.get();

    //세션에 회원정보 저장
    LoginMember loginMember = new LoginMember(findedMember.getMember_id(), findedMember.getEmail(),findedMember.getPassword(),
        findedMember.getName(),findedMember.getNickname(), findedMember.getPhone(), findedMember.getBirthday(),
        findedMember.getSms_service(), findedMember.getEmail_service(),findedMember.getGubun(), findedMember.getCdate(),findedMember.getUdate());

    //request.getSession(true) : 세션정보가 있으면 가져오고 없으면 세션을 많듬
    HttpSession session = request.getSession(true);
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    if(requestURI.equals("/")){
      //log.info("홈 화면으로");
      return "member/afterLogin";
    }
    else if(requestURI.equals("/members//info")){
      //log.info("홈 화면으로");
      return "redirect:members/"+loginMember.getEmail()+"/info";
    }
    //log.info("로그인 info : {}",requestURI);
    return "redirect:"+requestURI;
  }


  // 로그인 후 화면
  @GetMapping("/afterLogin")
  public String after() {
    return "member/afterLogin";
  }


  //로그아웃
  @GetMapping("/logout")
  public String logout(HttpServletRequest request){
    //request.getSession(false) : 세션정보가 있으면 가져오고 없으면 세션을 만들지 않음
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/"; //초기화면 이동
  }
}