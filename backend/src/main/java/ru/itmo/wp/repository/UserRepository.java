package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.wp.domain.MyUser;

import java.util.List;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE my_user SET password_sha=?2 WHERE id=?1", nativeQuery = true)
    void updatePasswordSha(long id, String passwordSha);

    @Query(value = "SELECT * FROM my_user WHERE login=?1 AND password_sha=?2", nativeQuery = true)
    MyUser findByLoginAndPassword(String login, String passwordSha);

    List<MyUser> findAllByOrderByIdDesc();

    List<MyUser> findAllByIdIn(List<Long> ids);

    MyUser findByLogin(String login);
}
