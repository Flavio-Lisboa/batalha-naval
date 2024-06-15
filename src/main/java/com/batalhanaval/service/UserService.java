package com.batalhanaval.service;

import com.batalhanaval.dtos.LoginModel;
import com.batalhanaval.dtos.LoginResponse;
import com.batalhanaval.dtos.PacoteModel;
import com.batalhanaval.entity.Avatar;
import com.batalhanaval.entity.Embarcacao;
import com.batalhanaval.entity.User;
import com.batalhanaval.repository.*;
import com.batalhanaval.util.ImageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TemaRepository temaRepository;
    private final AvatarRepository avatarRepository;
    private final EmbarcacaoRepository embarcacaoRepository;
    private final UserTemaRepository userTemaRepository;

    public UserService(UserRepository userRepository, TemaRepository temaRepository, AvatarRepository avatarRepository, EmbarcacaoRepository embarcacaoRepository, UserTemaRepository userTemaRepository) {
        this.userRepository = userRepository;
        this.temaRepository = temaRepository;
        this.avatarRepository = avatarRepository;
        this.embarcacaoRepository = embarcacaoRepository;
        this.userTemaRepository = userTemaRepository;
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
        user.setDiamante(updateData.getDiamante());
        user.setMoeda(updateData.getMoeda());
        user.setVolumeMusica(updateData.getVolumeMusica());
        user.setVolumeSom(updateData.getVolumeSom());
        user.setVitorias(updateData.getVitorias());
        user.setDerrotas(updateData.getDerrotas());
        user.setIdAvatar(updateData.getIdAvatar());
        user.setIdEmbarcacao(updateData.getIdEmbarcacao());
        user.setIdTema(updateData.getIdTema());

        this.userRepository.save(user);

        return user;
    }

    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    public LoginResponse login(LoginModel model) {

        User user = this.userRepository.findByEmail(model.getLogin());

        if (user == null) return new LoginResponse("Usuário não encontrado. Por favor, verifique suas credenciais ou cadastre-se.", false, 0L, "");

        if (!Objects.equals(user.getSenha(), model.getSenha())) return new LoginResponse("Senha incorreta.", false, 0L, "");

        LoginResponse response = new LoginResponse();
        response.setMensagem("Logado com sucesso!");
        response.setSucessoLogin(true);
        response.setUsuarioId(user.getId());
        response.setNomeUsuario(user.getNome());

        return response;
    }

    public List<PacoteModel> getAllPacotes(Long userId) {
        List<PacoteModel> pacoteModels = new ArrayList<>();

        var response = this.userTemaRepository.findAllTemasByUserId(userId);

        response.forEach(t -> {
            Avatar avatar = this.avatarRepository.findByTema_Id(t.getId());
            Embarcacao embarcacao = this.embarcacaoRepository.findByTema_Id(t.getId());

            byte[] imageData = ImageUtil.decompressImage(t.getImgFundo());
            String base64Fundo = "data:image/jpg;base64,"+ Base64.getEncoder().encodeToString(imageData);

            imageData = ImageUtil.decompressImage(avatar.getImgAvatar());
            String base64Avatar= "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(imageData);

            imageData = ImageUtil.decompressImage(embarcacao.getBarcosFile());
            String base64Barcos = "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(imageData);

            imageData = ImageUtil.decompressImage(embarcacao.getImgBarco1());
            String base64Barco1 = "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(imageData);

            imageData = ImageUtil.decompressImage(embarcacao.getImgBarco2());
            String base64Barco2 = "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(imageData);

            imageData = ImageUtil.decompressImage(embarcacao.getImgBarco3());
            String base64Barco3 = "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(imageData);

            imageData = ImageUtil.decompressImage(embarcacao.getImgBarco4());
            String base64Barco4 = "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(imageData);

            pacoteModels.add(new PacoteModel(t.getId(), t.getNome(), t.getTipoPagamento(), t.getValor(), avatar.getNome(), embarcacao.getNome(), base64Fundo, base64Avatar, base64Barcos, base64Barco1, base64Barco2, base64Barco3, base64Barco4));
        });

        return pacoteModels;
    }
}
