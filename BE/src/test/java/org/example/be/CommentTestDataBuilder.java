package org.example.be;
import org.example.be.entity.Comment;
import org.example.be.entity.Member;
import org.example.be.entity.Post;
import org.example.be.dto.response.CommentResponse;

public class CommentTestDataBuilder {
    public static Comment createDefaultComment() {
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setComment("Nice post!");
        comment.setStatus("ACTIVE");
        comment.setRating(5);
        Member member = new Member();
        member.setMemberId(1);
        member.setUsername("testuser");
        comment.setMember(member);
        Post post = new Post();
        post.setPostsId(1);
        post.setTitle("Test Post");
        comment.setPost(post);
        return comment;
    }
    public static Comment createCommentWithId(int id) {
        Comment comment = createDefaultComment();
        comment.setCommentId(id);
        return comment;
    }
    public static CommentResponse createDefaultCommentResponse() {
        CommentResponse response = new CommentResponse();
        response.setCommentId(1);
        response.setComment("Nice post!");
        response.setStatus("ACTIVE");
        response.setRating(5);
        return response;
    }
}
