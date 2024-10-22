package com.nubble.backend.comment.service;

import com.nubble.backend.comment.mapper.CommentInfoMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentInfoMapper commentInfoMapper;

    @Transactional(readOnly = true)
    public List<CommentInfo.CommentDto> findAllByPostId(long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(commentInfoMapper::toCommentDto)
                .toList();
    }
}
