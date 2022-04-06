package com.shopme.admin.user;

import com.shopme.ShopmeCommon.entity.Role;
import com.shopme.ShopmeCommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public void save(User user) {
        encodePassword(user);
        userRepository.save(user);
    }

    public boolean isEmailUnique(String email) {
        User userByEmail = userRepository.getUserByEmail(email);
        return userByEmail == null;
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

}
