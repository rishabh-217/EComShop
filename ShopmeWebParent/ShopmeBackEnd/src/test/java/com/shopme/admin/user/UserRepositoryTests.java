package com.shopme.admin.user;

import com.shopme.ShopmeCommon.entity.Role;
import com.shopme.ShopmeCommon.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateNewUserWithOneRole(){
        Role roleAdmin = testEntityManager.find(Role.class, 1);
        User testUser = new User("test@test.com", "test@123", "test", "user");
        testUser.addRole(roleAdmin);

        User savedUser = userRepository.save(testUser);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles(){
        User testUser = new User("test2@test.com", "test@123", "test2", "user2");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        testUser.addRole(roleEditor);
        testUser.addRole(roleAssistant);

        User savedUser = userRepository.save(testUser);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }

    @Test
    public void testGetUserById(){
        User user = userRepository.findById(1).get();
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User testUser = userRepository.findById(1).get();
        testUser.setEnabled(true);
        testUser.setEmail("testuser@gmail.com");
        userRepository.save(testUser);
    }

    @Test
    public void testUpdateUserRoles(){
        User testUser = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesPerson = new Role(2);

        testUser.getRoles().remove(roleEditor);
        testUser.addRole(roleSalesPerson);

        userRepository.save(testUser);
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        userRepository.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "rktest3@gmail.com";
        User user = userRepository.getUserByEmail(email);

        assertThat(user).isNotNull();

    }


}
