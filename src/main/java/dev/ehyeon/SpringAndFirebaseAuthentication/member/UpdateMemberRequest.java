package dev.ehyeon.SpringAndFirebaseAuthentication.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateMemberRequest {

    private String nickname;
}
