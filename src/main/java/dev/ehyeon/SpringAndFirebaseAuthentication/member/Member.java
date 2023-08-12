package dev.ehyeon.SpringAndFirebaseAuthentication.member;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Unique
    private String uid;
    @Unique
    private String email;
    private String nickname;

    public Member(String uid, String email) {
        this.uid = uid;
        this.email = email;
        nickname = MemberUtils.getRandomUuid();
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public void updateMember(UpdateMemberRequest request) {
        this.nickname = request.getNickname();
    }
}
