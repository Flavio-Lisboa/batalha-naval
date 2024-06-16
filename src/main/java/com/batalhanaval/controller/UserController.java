package com.batalhanaval.controller;

import com.batalhanaval.dtos.LoginModel;
import com.batalhanaval.dtos.LoginResponse;
import com.batalhanaval.dtos.PacoteModel;
import com.batalhanaval.dtos.UserModel;
import com.batalhanaval.entity.NivelAcesso;
import com.batalhanaval.entity.User;
import com.batalhanaval.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UserController {

    private final UserService userService;

    @Autowired
    private PacoteController pacoteController;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserModel userModel) throws Exception {
        User user = User.builder()
                .email(userModel.getEmail())
                .nome(userModel.getNome())
                .senha(userModel.getSenha())
                .dataNascimento(LocalDateTime.now())
                .nivelAcesso(NivelAcesso.USER)
                .diamante(10)
                .moeda(500)
                .volumeMusica(0.5f)
                .volumeSom(0.5f)
               // .srcAvatar("../../assets/images/img-home-page/pirata1.png")
                .idAvatar(1)
                .idTema(1)
                .idEmbarcacao(1)
                .vitorias(0)
                .derrotas(0)
                .build();


        user = this.userService.saveUser(user);


        this.pacoteController.comprarPacote(user.getId(), 1L); //falar pro Flavio dps ;-;

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("{userId}")
    @CrossOrigin
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = this.userService.getUser(userId);
        System.out.println(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> users = this.userService.getUsers();

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
    public ResponseEntity<User> alterarSenha(@RequestParam() String novaSenha, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setSenha(novaSenha);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping("{userId}/update-moeda")
    @CrossOrigin
    public ResponseEntity<Boolean> updateMoeda(@RequestParam() int valorMoeda, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
           // return ResponseEntity.notFound().build();
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }

        user.setMoeda(valorMoeda);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(true);//ResponseEntity.ok(user);
    }

    @PutMapping("{userId}/update-diamante")
    @CrossOrigin
    public ResponseEntity<Boolean> updateDiamante(@RequestParam() int valorDiamante, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
           // return ResponseEntity.notFound().build();
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }

        user.setDiamante(valorDiamante);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(true);//ResponseEntity.ok(user);
    }

    @PutMapping("{userId}/alterar-nome")
    @CrossOrigin
    public ResponseEntity<User> alterarNome(@RequestParam() String novoNome, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setNome(novoNome);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping("{userId}/alterar-volume")
    @CrossOrigin
    public ResponseEntity<User> alterarVolume(@RequestParam() float volumeSom, @RequestParam() float volumeMusica, @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setVolumeSom(volumeSom);
        user.setVolumeMusica(volumeMusica);
        user = this.userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping("{userId}/alterar-ids-pacotes")
    @CrossOrigin
    public ResponseEntity<User> updateUserTemaId(
            @RequestParam() int newTemaId,
            @RequestParam() int newAvatarId,
            @RequestParam() int newEmbarcacoesId,
            @PathVariable Long userId) {
        User user = this.userService.getUser(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if(newTemaId != 0){
            user.setIdTema(newTemaId);
        }
        if(newAvatarId != 0){
            user.setIdAvatar(newAvatarId);
        }
        if(newEmbarcacoesId != 0){
            user.setIdEmbarcacao(newEmbarcacoesId);
        }
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

    @GetMapping("{userId}/pacotes")
    @CrossOrigin
    public ResponseEntity<List<PacoteModel>> getPacotes(@PathVariable Long userId) {
        List<PacoteModel> pacoteModels = this.userService.getAllPacotes(userId);

        if (pacoteModels == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pacoteModels);
    }
}
