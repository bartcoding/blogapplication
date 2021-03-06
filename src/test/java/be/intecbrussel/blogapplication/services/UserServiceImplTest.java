package be.intecbrussel.blogapplication.services;

import be.intecbrussel.blogapplication.model.User;
import be.intecbrussel.blogapplication.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        userService = new UserServiceImpl(userRepository, passwordEncoder);

    }

    @Test
    void findById() {

        User user = new User();
        user.setId(1L);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        User userReturned = userService.findById(1L);

        assertNotNull(userReturned, "user is not null");
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).findAll();

    }

    @Test
    void findByEmail() {

        User user = new User();
        user.setEmail("mock@gmail.com");

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        User userReturned = userService.findByEmail("mock@gmail.com");

        assertNotNull(userReturned, "user is not null");
        assertEquals(userReturned,user);

        verify(userRepository, times(1)).findByEmail("mock@gmail.com");
        verify(userRepository, never()).findAll();

    }

    @Test
    void save() {

        User user = new User();
        user.setId(1L);
        user.setEmail("mock@gmail.com");
        user.setPassword("newpassword");
        Optional<User> userOptional = Optional.of(user);

        //UserRegistrationDto registration = new UserRegistrationDto();

        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        when(userRepository.save(any())).thenReturn(userOptional);

        Optional<User> userReturned = Optional.of(userService.findById(1L));

        assertEquals(Long.valueOf(1L),user.getId());
        assertNotNull(userOptional, "user is not null");
        assertEquals(userOptional, userReturned);
        verify(userRepository, never()).findAll();

    }


    @Test
    void updateProfile() {

        User user = new User();
        user.setId(1L);
        user.setFirstName("jef");
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        User updatedUser = new User();
        updatedUser.setLastName("robby");
        updatedUser.setPassword("newpassword");

        Optional<User> userOptional1 = Optional.of(updatedUser);

        when(userRepository.findById(anyLong())).thenReturn(userOptional1);

        Optional<User> userReturned = Optional.of(userService.findById(1L));

        assertNotNull(userReturned, "user is not null");
        assertEquals(userOptional1, userReturned);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).findAll();

    }

    @Test
    void loadUserByUsername() {

        User user = new User();
        user.setEmail("mock@gmail.com");
        user.setFirstName("jeffrey");

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        User userReturned = userService.findByEmail("mock@gmail.com");

        assertNotNull(userReturned, "user is not null");
        verify(userRepository, times(1)).findByEmail("mock@gmail.com");
        verify(userRepository, never()).findAll();

    }

}