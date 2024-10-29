package com.nubble.backend.post.comment.service;

import com.nubble.backend.comment.shared.CommentInfo;
import com.nubble.backend.comment.shared.CommentMapper;
import com.nubble.backend.comment.shared.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<CommentInfo.CommentDto> findAllByPostId(long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(commentMapper::toCommentDto)
                .toList();
    }
}
