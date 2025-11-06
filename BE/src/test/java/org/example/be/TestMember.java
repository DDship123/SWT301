package org.example.be;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.be.entity.Member;
import org.example.be.repository.PostRepository;
import org.example.be.service.PostService;
import org.example.be.repository.MemberRepository;
import org.example.be.service.MemberService;
import org.example.be.dto.request.MemberRegisterRequest;
import org.example.be.dto.response.ApiResponse;
import org.example.be.dto.request.LoginRequest;
import org.example.be.dto.response.MemberResponse;

@ExtendWith(MockitoExtension.class)
public class TestMember {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void registerMember() {
        // Arrange
        MemberRegisterRequest request = MemberTestDataBuilder.createDefaultRegisterRequest();
        Member savedMember = MemberTestDataBuilder.createDefaultMember();
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);
        // Act
        ApiResponse<Member> response = memberService.register(request);
        // Assert
        assertNotNull(response);
        assertEquals(savedMember.getEmail(), response.getPayload().getEmail());
        assertEquals(savedMember.getUsername(), response.getPayload().getUsername());
        assertEquals(savedMember.getPhone(), response.getPayload().getPhone());
        // Verify
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void registerMemberFailed() {
        // Arrange
        MemberRegisterRequest request = MemberTestDataBuilder.createDefaultRegisterRequest();
        Member existing = MemberTestDataBuilder.createDefaultMember();
        // Mock
        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existing));
        // Act
        ApiResponse<Member> response = memberService.register(request);
        // Assert
        assertNotNull(response);
        assertEquals("ERROR", response.getStatus());
        assertTrue(response.getError().containsKey("email"));
        assertEquals("Email này đã được sử dụng", response.getError().get("email"));
        // Verify
        verify(memberRepository, times(1)).findByEmail(request.getEmail());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    public void updateMember() {
        // Arrange
        Integer memberId = 1;
        Member originalMember = MemberTestDataBuilder.createDefaultMember();
        originalMember.setEmail("olduser@example.com");
        originalMember.setUsername("olduser");
        originalMember.setPhone("0912345678");
        Member updatedDetails = MemberTestDataBuilder.createMemberWithId(memberId);
        updatedDetails.setEmail("updateduser@example.com");
        updatedDetails.setUsername("updateduser");
        updatedDetails.setPhone("0912345678");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(originalMember));
        when(memberRepository.findByUsername(updatedDetails.getUsername())).thenReturn(Optional.empty());
        when(memberRepository.findByEmail(updatedDetails.getEmail())).thenReturn(Optional.empty());
        when(memberRepository.findByPhone(updatedDetails.getPhone())).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(updatedDetails);
        // Act
        ApiResponse<Member> response = memberService.updateMember(memberId, updatedDetails);
        // Assert
        assertNotNull(response);
        assertEquals(updatedDetails.getEmail(), response.getPayload().getEmail());
        assertEquals(updatedDetails.getUsername(), response.getPayload().getUsername());
        assertEquals(updatedDetails.getPhone(), response.getPayload().getPhone());
        // Verify
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void updateMemberFailed() {
        // Arrange
        Integer memberId = 1;
        Member updatedDetails = MemberTestDataBuilder.createDefaultMember();
        // Mock
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
        // Act
        ApiResponse<Member> response = memberService.updateMember(memberId, updatedDetails);
        // Assert
        assertNotNull(response);
        assertEquals("ERROR", response.getStatus());
        assertTrue(response.getError().containsKey("message"));
        assertEquals("Member not found", response.getError().get("message"));
        // Verify
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    public void loginMember() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        Member member = MemberTestDataBuilder.createDefaultMember();
        // Mock
        when(memberRepository.findByUsernameAndPassword("testuser", "password")).thenReturn(Optional.of(member));
        // Act
        Optional<Member> result = memberService.login(request);
        // Assert
        assertTrue(result.isPresent());
        assertEquals(member.getUsername(), result.get().getUsername());
        // Verify
        verify(memberRepository, times(1)).findByUsernameAndPassword("testuser", "password");
    }

    @Test
    public void loginMemberFailed() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("nouser");
        request.setPassword("badpass");
        // Mock
        when(memberRepository.findByUsernameAndPassword("nouser", "badpass")).thenReturn(Optional.empty());
        // Act
        Optional<Member> result = memberService.login(request);
        // Assert
        assertFalse(result.isPresent());
        // Verify
        verify(memberRepository, times(1)).findByUsernameAndPassword("nouser", "badpass");
    }

    @Test
    public void updateMemberStatus() {
        // Arrange
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setMemberId(1);
        memberResponse.setStatus("ACTIVE");
        Member member = MemberTestDataBuilder.createDefaultMember();
        member.setStatus("ACTIVE");
        // Mock
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        // Act
        ApiResponse<MemberResponse> response = memberService.updateMemberStatus(memberResponse);
        // Assert
        assertEquals("ACTIVE", response.getPayload().getStatus());
        // Verify
        verify(memberRepository, times(1)).findById(1);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void updateMemberStatusFailed() {
        // Arrange
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setMemberId(1);
        memberResponse.setStatus("INACTIVE");
        // Mock
        when(memberRepository.findById(1)).thenReturn(Optional.empty());
        // Act
        ApiResponse<MemberResponse> response = memberService.updateMemberStatus(memberResponse);
        // Assert
        assertNotNull(response);
        assertEquals("ERROR", response.getStatus());
        assertTrue(response.getError().containsKey("message"));
        assertEquals("Member not found", response.getError().get("message"));
        // Verify
        verify(memberRepository, times(1)).findById(1);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    public void getMembersByStatus() {
        // Arrange
        Member member = MemberTestDataBuilder.createDefaultMember();
        // Mock
        when(memberRepository.getMembersByStatus("ACTIVE")).thenReturn(List.of(member));
        // Act
        List<Member> result = memberService.getMembersByStatus("ACTIVE");
        // Assert
        assertEquals(1, result.size());
        // Verify
        verify(memberRepository, times(1)).getMembersByStatus("ACTIVE");
    }

    @Test
    public void getMembersByStatusFailed() {
        // Arrange
        String status = "ACTIVE";
        // Mock
        when(memberRepository.getMembersByStatus(status)).thenReturn(List.of());
        // Act
        List<Member> result = memberService.getMembersByStatus(status);
        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        // Verify
        verify(memberRepository, times(1)).getMembersByStatus(status);
    }
}
