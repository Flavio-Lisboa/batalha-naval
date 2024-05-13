package com.batalhanaval.service;

import com.batalhanaval.dtos.PacoteInputModel;
import com.batalhanaval.dtos.PacoteModel;
import com.batalhanaval.dtos.PacoteResponseModel;
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
    public PacoteResponseModel postPacote(PacoteInputModel model) throws IOException {

        if (this.temaRepository.existsTemaByNome(model.getNomeTema())) {
            return new PacoteResponseModel(false, "Já existe um tema cadastrado com esse nome");
        };
        if (this.avatarRepository.existsAvatarByNome(model.getNomeAvatar())) {
            return new PacoteResponseModel(false, "Já existe um avatar cadastrado com esse nome");
        };
        if (this.embarcacaoRepository.existsEmbarcacaoByNome(model.getNomeEmbarcacoes())) {
            return new PacoteResponseModel(false, "Já existem embarcações cadastradas com esse nome");
        };

        Tema tema = Tema.builder()
                .nome(model.getNomeTema())
                .tipoPagamento(model.getTipoPagamento())
                .valor(model.getValor())
                .imgFundo(ImageUtil.compressImage(model.getImageFundo().getBytes()))
                .build();

        tema = this.temaRepository.save(tema);

        if (tema.getId() != null) {
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
        }

        return new PacoteResponseModel(true, "Sucesso ao criar pacote!");
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
