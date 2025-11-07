package org.example.be.dataBuilder;

import org.example.be.dto.request.MemberRegisterRequest;
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

    public static MemberRegisterRequest createDefaultRegisterRequest() {
        MemberRegisterRequest request = new MemberRegisterRequest();
        request.setUsername("testuser");
        request.setAddress("123 Test Street");
        request.setEmail("testuser@example.com");
        request.setPhone("0123456789");
        request.setPassword("password123");
        return request;
    }
}