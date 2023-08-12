package dev.ehyeon.SpringAndFirebaseAuthentication.config;

import dev.ehyeon.SpringAndFirebaseAuthentication.member.Member;
import dev.ehyeon.SpringAndFirebaseAuthentication.member.MemberRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StringTokenizer st = new StringTokenizer(username);
        String uid = st.nextToken();
        String email = st.nextToken();

        if (!memberRepository.existsMemberByUid(uid)) {
            memberRepository.save(new Member(uid, email));
        }

        return new CustomUserDetails(uid);
    }

    private static class CustomUserDetails implements UserDetails {

        List<GrantedAuthority> authorities;
        String username;

        CustomUserDetails(String username) {
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("MEMBER")); // TODO refactor role
            this.username = username;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
