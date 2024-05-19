package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.MyUser;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserService userService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public List<Post> findAllByUserId(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    public void writeComment(String text, Long postId, Long userId) {
        Post post = postRepository.findPostById(postId);
        Comment comment = new Comment();
        comment.setText(text);
        comment.setPost(post);
        comment.setUser(userRepository.findById(userId).orElse(null));
        post.addComment(comment);
        post.incrementComentCount();
        postRepository.save(post);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
}
