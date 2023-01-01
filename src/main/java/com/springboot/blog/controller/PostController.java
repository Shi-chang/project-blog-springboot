package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    // From spring 4.3, @Autowired is not required if there is only one constructor in a class.
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Creates a new post.
     *
     * @param postDto the post dto from the request body
     * @return a response entity containing the post dto
     */
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity(postService.createPost(postDto), HttpStatus.CREATED);
    }

    /**
     * Gets all posts.
     *
     * @return all the posts
     */
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    /**
     * Gets a post by id.
     *
     * @param id the id of the post
     * @return a response entity that contains the post dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
        return new ResponseEntity(postService.getPostById(id), HttpStatus.OK);
    }

    /**
     * Updates a post.
     *
     * @param postDto the request post dto
     * @param id      the id of the post to be updated
     * @return the response entity that contains the updated post dto
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable long id) {
        return new ResponseEntity(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity("Post deleted successfully.", HttpStatus.OK);
    }
}
