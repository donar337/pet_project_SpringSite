package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class CommentController {
    private final PostService postService;

    private final CommentService commentService;

    public CommentController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("comment/by/post")
    public List<Comment> commentsByUser(@RequestParam Long postId) {
        return commentService.findAllByPostId(postId);
    }

    @PostMapping("comment/write")
    public void writeComment(@RequestBody @Valid CommentCredentials commentCredentials,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        postService.writeComment(commentCredentials.getText(), commentCredentials.getPostId(), commentCredentials.getUserId());
    }
}
