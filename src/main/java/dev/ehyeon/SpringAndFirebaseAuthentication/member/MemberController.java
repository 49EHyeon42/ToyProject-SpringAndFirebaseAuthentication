package dev.ehyeon.SpringAndFirebaseAuthentication.member;

import dev.ehyeon.SpringAndFirebaseAuthentication.error.ErrorCode;
import dev.ehyeon.SpringAndFirebaseAuthentication.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public MemberResponse findMember() {
        return memberService.findMemberByUid(getUid());
    }

    @PutMapping("/member")
    public ResponseEntity<Void> updateMember(@RequestBody UpdateMemberRequest request) {
        memberService.updateMemberByUid(getUid(), request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getUid() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> emptyResultDataAccessExceptionHandler(
            EmptyResultDataAccessException exception) {
        return ResponseEntity
                .status(ErrorCode.NOT_FOUND_MEMBER.getStatus())
                .body(new ErrorResponse(
                        ErrorCode.NOT_FOUND_MEMBER.getStatus(),
                        ErrorCode.NOT_FOUND_MEMBER.getMessage()));
    }
}
