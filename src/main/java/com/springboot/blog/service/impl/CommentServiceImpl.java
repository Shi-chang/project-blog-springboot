package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    /**
     * Creates a comment.
     *
     * @param postId the post id that this comment belongs to
     * @param commentDto the comment dto
     * @return the comment dto
     */
    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    /**
     * Gets comments by post id.
     *
     * @param postId the post id that comments belong to
     * @return a list of comment dto
     */
    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Gets a comment by its id.
     *
     * @param postId the post id that this comment belongs to
     * @param commentId the id of the comment
     * @return the comment dto
     */
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException("Comment does not belong to the post.", HttpStatus.BAD_REQUEST);
        }

        return mapToDto(comment);
    }

    /**
     * A helper function that converts a comment entity to a comment dto.
     *
     * @param comment the comment to be converted
     * @return the comment dto
     */
    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());

        return commentDto;
    }

    /**
     * A helper function that converts a comment dto to a comment entity.
     *
     * @param commentDto the comment dto to be converted
     * @return the comment
     */
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());

        return comment;
    }
}
