package com.alex.travel.derby.data.service;

import com.alex.travel.derby.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }
    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public User update(User entity) {
        return userRepository.save(entity);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    public int count() {
        return (int) userRepository.count();
    }

}
