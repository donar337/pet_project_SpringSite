package ru.itmo.wp.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.MyUser;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final String HASH_SALT = "8960c201fb3136ef";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(String login, String name, String password) {
        MyUser user = new MyUser();
        user.setAdmin(false);
        user.setLogin(login);
        user.setName(name);
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), DigestUtils.sha256Hex(password + HASH_SALT + login));
    }

    public MyUser findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, DigestUtils.sha256Hex(password + HASH_SALT + login));
    }

    public MyUser findByLogin(String login) {
            return userRepository.findByLogin(login);
    }

    public MyUser findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<MyUser> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public List<MyUser> findAllById(List<Long> ids) {
        return  userRepository.findAllByIdIn(ids);
    }

    public void writePost(Post post) {
        post.getUser().addPost(post);
        userRepository.save(post.getUser());
    }
}
