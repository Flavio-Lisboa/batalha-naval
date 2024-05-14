package com.batalhanaval.service;

import com.batalhanaval.dtos.PacoteInputModel;
import com.batalhanaval.dtos.PacoteModel;
import com.batalhanaval.dtos.PacoteResponseModel;
import com.batalhanaval.dtos.PacoteUpdateModel;
import com.batalhanaval.entity.Avatar;
import com.batalhanaval.entity.Embarcacao;
import com.batalhanaval.entity.Tema;
import com.batalhanaval.repository.AvatarRepository;
import com.batalhanaval.repository.EmbarcacaoRepository;
import com.batalhanaval.repository.TemaRepository;
import com.batalhanaval.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PacoteService {

    private final TemaRepository temaRepository;
    private final AvatarRepository avatarRepository;
    private final EmbarcacaoRepository embarcacaoRepository;

    public PacoteService(TemaRepository temaRepository, AvatarRepository avatarRepository, EmbarcacaoRepository embarcacaoRepository) {
        this.temaRepository = temaRepository;
        this.avatarRepository = avatarRepository;
        this.embarcacaoRepository = embarcacaoRepository;
    }

    @Transactional
    public PacoteResponseModel postPacote(PacoteInputModel model, boolean edit,  Long temaId) throws IOException {

        if (this.temaRepository.existsTemaByNome(model.getNomeTema())) {
            if (!edit) {
                return new PacoteResponseModel(false, "Já existe um tema cadastrado com esse nome");
            } else {
                if(!this.temaRepository.existsTemaByNomeAndId(model.getNomeTema(), temaId)) {
                    return new PacoteResponseModel(false, "Já existe um tema cadastrado com esse nome");
                }
            }
        };
        if (this.avatarRepository.existsAvatarByNome(model.getNomeAvatar())) {
            if (!edit) {
                return new PacoteResponseModel(false, "Já existe um avatar cadastrado com esse nome");
            } else {
                if(!this.avatarRepository.existsAvatarByNomeAndTema_Id(model.getNomeAvatar(), temaId)) {
                    return new PacoteResponseModel(false, "Já existe um avatar cadastrado com esse nome");
                }
            }
        };
        if (this.embarcacaoRepository.existsEmbarcacaoByNome(model.getNomeEmbarcacoes())) {
            if (!edit) {
                return new PacoteResponseModel(false, "Já existem embarcações cadastradas com esse nome");
            } else {
                if(!this.embarcacaoRepository.existsEmbarcacaoByNomeAndTema_Id(model.getNomeEmbarcacoes(), temaId)) {
                    return new PacoteResponseModel(false, "Já existem embarcações cadastradas com esse nome");
                }
            }
        };

        Tema tema = Tema.builder()
                .nome(model.getNomeTema())
                .tipoPagamento(model.getTipoPagamento())
                .valor(model.getValor())
                .imgFundo(ImageUtil.compressImage(model.getImageFundo().getBytes()))
                .build();

        if (edit) tema.setId(temaId);

        tema = this.temaRepository.save(tema);

        if (!edit) {
            Avatar avatar = Avatar.builder()
                    .nome(model.getNomeAvatar())
                    .imgAvatar(ImageUtil.compressImage(model.getImageAvatar().getBytes()))
                    .tema(tema)
                    .build();

            Embarcacao embarcacao = Embarcacao.builder()
                    .nome(model.getNomeEmbarcacoes())
                    .imgBarco1(ImageUtil.compressImage(model.getBarco1File().getBytes()))
                    .imgBarco2(ImageUtil.compressImage(model.getBarco2File().getBytes()))
                    .imgBarco3(ImageUtil.compressImage(model.getBarco3File().getBytes()))
                    .imgBarco4(ImageUtil.compressImage(model.getBarco4File().getBytes()))
                    .barcosFile(ImageUtil.compressImage(model.getBarcosFile().getBytes()))
                    .tema(tema)
                    .build();

            this.avatarRepository.save(avatar);
            this.embarcacaoRepository.save(embarcacao);
        } else {
            Avatar avatar = this.avatarRepository.findByTema_Id(tema.getId());

            avatar.setNome(model.getNomeAvatar());
            avatar.setTema(tema);
            avatar.setImgAvatar(ImageUtil.compressImage(model.getImageAvatar().getBytes()));

            Embarcacao embarcacao = this.embarcacaoRepository.findByTema_Id(tema.getId());

            embarcacao.setNome(model.getNomeEmbarcacoes());
            embarcacao.setImgBarco1(ImageUtil.compressImage(model.getBarco1File().getBytes()));
            embarcacao.setImgBarco2(ImageUtil.compressImage(model.getBarco2File().getBytes()));
            embarcacao.setImgBarco3(ImageUtil.compressImage(model.getBarco3File().getBytes()));
            embarcacao.setImgBarco4(ImageUtil.compressImage(model.getBarco4File().getBytes()));
            embarcacao.setBarcosFile(ImageUtil.compressImage(model.getBarcosFile().getBytes()));
            embarcacao.setTema(tema);

            this.avatarRepository.save(avatar);
            this.embarcacaoRepository.save(embarcacao);
        }

        return new PacoteResponseModel(true, edit ? "Sucesso ao editar pacote!" : "Sucesso ao criar pacote!");
    }

    public PacoteResponseModel update(Long temaId, PacoteInputModel model) throws IOException {
        return this.postPacote(model, true, temaId);
    }

    public List<PacoteModel> getAll() {
        List<PacoteModel> pacoteModels = new ArrayList<>();

        var response = this.temaRepository.findAll();

        response.forEach(t -> {
            Avatar avatar = this.avatarRepository.findByTema_Id(t.getId());
            Embarcacao embarcacao = this.embarcacaoRepository.findByTema_Id(t.getId());

            byte[] imageData = ImageUtil.decompressImage(t.getImgFundo());
            String base64Fundo = Base64.getEncoder().encodeToString(imageData);

            imageData = ImageUtil.decompressImage(avatar.getImgAvatar());
            String base64Avatar= Base64.getEncoder().encodeToString(imageData);

            imageData = ImageUtil.decompressImage(embarcacao.getBarcosFile());
            String base64Barcos = Base64.getEncoder().encodeToString(imageData);

            pacoteModels.add(new PacoteModel(t.getId(), t.getNome(), t.getTipoPagamento(), t.getValor(), base64Fundo, base64Avatar, base64Barcos));
        });

        return pacoteModels;
    }

    public PacoteUpdateModel getPacote(Long temaId) {
        Tema tema = this.temaRepository.findById(temaId).orElse(null);
        Avatar avatar = this.avatarRepository.findByTema_Id(temaId);
        Embarcacao embarcacao = this.embarcacaoRepository.findByTema_Id(temaId);

        byte[] imageData = ImageUtil.decompressImage(tema.getImgFundo());
        String base64Fundo = Base64.getEncoder().encodeToString(imageData);

        imageData = ImageUtil.decompressImage(avatar.getImgAvatar());
        String base64Avatar = Base64.getEncoder().encodeToString(imageData);

        imageData = ImageUtil.decompressImage(embarcacao.getImgBarco1());
        String base64Barco1 = Base64.getEncoder().encodeToString(imageData);

        imageData = ImageUtil.decompressImage(embarcacao.getImgBarco2());
        String base64Barco2 = Base64.getEncoder().encodeToString(imageData);

        imageData = ImageUtil.decompressImage(embarcacao.getImgBarco3());
        String base64Barco3 = Base64.getEncoder().encodeToString(imageData);

        imageData = ImageUtil.decompressImage(embarcacao.getImgBarco4());
        String base64Barco4 = Base64.getEncoder().encodeToString(imageData);

        imageData = ImageUtil.decompressImage(embarcacao.getBarcosFile());
        String base64Barcos = Base64.getEncoder().encodeToString(imageData);

        return new PacoteUpdateModel(
                tema.getId(),
                tema.getTipoPagamento(),
                tema.getValor(),
                tema.getNome(),
                avatar.getNome(),
                embarcacao.getNome(),
                base64Fundo,
                base64Avatar,
                base64Barco4,
                base64Barco3,
                base64Barco2,
                base64Barco1,
                base64Barcos);
    }

    @Transactional
    public void delete(Long temaId) {
        Tema tema = this.temaRepository.findById(temaId).orElse(null);

        Embarcacao embarcacao = this.embarcacaoRepository.findByTema_Id(temaId);
        embarcacao.setTema(tema);
        this.embarcacaoRepository.delete(embarcacao);

        Avatar avatar = this.avatarRepository.findByTema_Id(temaId);
        avatar.setTema(tema);
        this.avatarRepository.delete(avatar);

        this.temaRepository.delete(tema);
    }
}
