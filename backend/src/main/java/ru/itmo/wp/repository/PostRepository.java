package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.wp.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);
    List<Post> findAllByOrderByCreationTimeDesc();

    @Query(value = "SELECT * FROM post WHERE user_id=?1 ORDER BY creation_time DESC", nativeQuery = true)
    List<Post> findAllByUserId(Long userId);
}
