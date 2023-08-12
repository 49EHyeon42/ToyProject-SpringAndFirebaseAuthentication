package dev.ehyeon.SpringAndFirebaseAuthentication.member;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse findMemberByUid(String uid) {
        return new MemberResponse(findMember(uid).getNickname());
    }

    @Transactional
    public void updateMemberByUid(String uid, UpdateMemberRequest request) {
        findMember(uid).updateMember(request);
    }

    private Member findMember(String uid) {
        return memberRepository.findById(uid)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
