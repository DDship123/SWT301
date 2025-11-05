package org.example.be;

import org.example.be.entity.Member;

public class MemberTestDataBuilder {
    public static Member createDefaultMember() {
        Member member = new Member();
        member.setMemberId(1);
        member.setEmail("testuser@example.com");
        member.setUsername("testuser");
        member.setUsername("Test User");
        member.setPhone("0123456789");
        member.setCity("Ho Chi Minh City");
        member.setCity("District 1");
        member.setAddress("123 Test Street");
        member.setAvatarUrl("https://example.com/avatar.jpg");
        member.setStatus("ACTIVE");
        member.setRole("USER");
        return member;
    }

    public static Member createMemberWithId(Integer id) {
        Member member = createDefaultMember();
        member.setMemberId(id);
        member.setEmail("user" + id + "@example.com");
        member.setUsername("user" + id);
        return member;
    }

    public static Member createMemberInCity(String city) {
        Member member = createDefaultMember();
        member.setCity(city);
        return member;
    }
}
