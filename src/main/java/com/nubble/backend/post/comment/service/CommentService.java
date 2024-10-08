package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.post.comment.service.factory.CommentFactory;
import com.nubble.backend.post.comment.service.remover.CommentRemover;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentFactory commentFactory;
    private final CommentRepository commentRepository;
    private final CommentRemover commentRemover;

    @Transactional
    public long createComment(CommentCreateCommand command) {
        Post post = postRepository.findById(command.postId())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        Comment newComment = commentFactory.generateComment(post, command);
        return commentRepository.save(newComment)
                .getId();
    }

    @Transactional
    public void deleteComment(CommentDeleteCommand command) {
        Comment comment = commentRepository.findById(command.commentId())
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        commentRemover.remove(comment, command);
    }

    @Transactional(readOnly = true)
    public List<CommentInfo> findAllByPostId(long postId) {
        return null;
    }
}
