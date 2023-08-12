package dev.ehyeon.SpringAndFirebaseAuthentication.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    boolean existsMemberByUid(String uid);
}
