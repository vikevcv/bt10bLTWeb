package vn.iotstar.bt10_2.services;

import org.springframework.stereotype.Service;
import vn.iotstar.bt10_2.entity.User;
import vn.iotstar.bt10_2.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}