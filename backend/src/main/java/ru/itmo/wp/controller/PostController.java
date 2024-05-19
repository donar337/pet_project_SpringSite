package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.MyUser;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/1")
public class PostController {
    private final PostService postService;

    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("post")
    public Post findPost(@RequestParam Long postId) {
        Post post = postService.findPostById(postId);
        return post;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("posts/write")
    public void writePost(@RequestBody @Valid Post post,
                          BindingResult bindingResult) {
        if  (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        userService.writePost(post);
    }

    @GetMapping("posts/by/user")
    public List<Post> findPostsByUser(@RequestParam Long userId) {
        return postService.findAllByUserId(userId);
    }

//    @PutMapping("posts/change/score")
//    public void changeScore(@RequestBody int up,
//                            @RequestBody Long postId) {
//        int i = 0;
//    }
}
