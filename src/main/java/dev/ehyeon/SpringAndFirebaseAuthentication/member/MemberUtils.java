package dev.ehyeon.SpringAndFirebaseAuthentication.member;

import java.util.UUID;

public class MemberUtils {

    private MemberUtils() throws InstantiationException {
        throw new InstantiationException();
    }

    public static String getRandomUuid() {
        return UUID.randomUUID().toString();
    }
}
