package com.baile.grupodebaile.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baile.grupodebaile.models.ImageUser;
import com.baile.grupodebaile.models.User;
import com.baile.grupodebaile.repositories.ImageUserRepository;
import com.baile.grupodebaile.repositories.UserRepository;

@Service
public class UserService {
    
    private UserRepository repository;
    @Autowired
    private ImageUserRepository imageUserRepository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User store(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return repository.save(user);
    }

    public void saveImageUser(ImageUser imageUserToAdd, Long id){
        User userAddImage = repository.findById(id).orElseThrow();

        imageUserRepository.save(imageUserToAdd);
        userAddImage.setImageUser(imageUserToAdd);

        repository.save(userAddImage);
    }
    
    public List<User> listAll() {
        return repository.findAll();
    }

    public User listOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User listOneByName(String userName) {
        return repository.findByUsername(userName).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}