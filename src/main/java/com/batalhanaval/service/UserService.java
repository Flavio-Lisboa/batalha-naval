package com.batalhanaval.service;

import com.batalhanaval.entity.User;
import com.batalhanaval.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public User getUser(Long userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User updateUser(Long userId, User updateData) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) return null;

        user.setEmail(updateData.getEmail());
        user.setNome(updateData.getNome());
        user.setSenha(updateData.getSenha());
        user.setDataNascimento(updateData.getDataNascimento());
        user.setNivelAcesso(updateData.getNivelAcesso());

        this.userRepository.save(user);

        return user;
    }

    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }
}
