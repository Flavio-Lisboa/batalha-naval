package com.batalhanaval.controller;

import com.batalhanaval.dtos.LoginModel;
import com.batalhanaval.dtos.LoginResponse;
import com.batalhanaval.dtos.UserModel;
import com.batalhanaval.entity.NivelAcesso;
import com.batalhanaval.entity.User;
import com.batalhanaval.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserModel userModel) {
        User user = User.builder()
                .email(userModel.getEmail())
                .nome(userModel.getNome())
                .senha(userModel.getSenha())
                .dataNascimento(LocalDateTime.now())
                .nivelAcesso(NivelAcesso.USER)
                .build();

        user = this.userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("{userId}")
    @CrossOrigin
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = this.userService.getUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("{userId}")
    @CrossOrigin
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long userId) {
        user = this.userService.updateUser(userId, user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{userId}")
    @CrossOrigin
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }

    @PostMapping("login")
    @CrossOrigin
    public LoginResponse login(@RequestBody LoginModel loginModel) {
        return this.userService.login(loginModel);
    }

    @PutMapping("{userId}/alterar-senha")
    @CrossOrigin
    public ResponseEntity<User> alterarSenha(@RequestBody String novaSenha, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setSenha(novaSenha);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping("{userId}/alterar-nome")
    @CrossOrigin
    public ResponseEntity<User> alterarNome(@RequestBody String novoNome, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setNome(novoNome);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("{userId}/info")
    @CrossOrigin
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("{userId}/alterar-diamante")
    @CrossOrigin
    public ResponseEntity<User> alterarDiamante(@RequestBody String novoDiamante, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setDiamante(novoDiamante);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping("{userId}/alterar-moeda")
    @CrossOrigin
    public ResponseEntity<User> alterarMoeda(@RequestBody String novaMoeda, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setMoeda(novaMoeda);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(user);
    }
}
