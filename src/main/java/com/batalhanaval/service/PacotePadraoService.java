package com.batalhanaval.service;

import com.batalhanaval.entity.Avatar;
import com.batalhanaval.entity.Embarcacao;
import com.batalhanaval.entity.Tema;
import com.batalhanaval.repository.AvatarRepository;
import com.batalhanaval.repository.EmbarcacaoRepository;
import com.batalhanaval.repository.TemaRepository;
import com.batalhanaval.util.ImageUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@Component
public class PacotePadraoService implements SmartInitializingSingleton {

    private final TemaRepository temaRepository;
    private final AvatarRepository avatarRepository;
    private final EmbarcacaoRepository embarcacaoRepository;

    public PacotePadraoService(TemaRepository temaRepository, AvatarRepository avatarRepository, EmbarcacaoRepository embarcacaoRepository) {
        this.temaRepository = temaRepository;
        this.avatarRepository = avatarRepository;
        this.embarcacaoRepository = embarcacaoRepository;
    }

    @Override
    public void afterSingletonsInstantiated() {

        if (this.temaRepository.existsTemaByNome("Padrão")) {
            return;
        }

        InputStream temaImg = getClass().getClassLoader().getResourceAsStream("pacotePadrao/bgMadeira.png");
        InputStream avatarImg = getClass().getClassLoader().getResourceAsStream("pacotePadrao/pirata2.png");
        InputStream embarcacoesImg = getClass().getClassLoader().getResourceAsStream("pacotePadrao/BarcosPadrao.png");
        InputStream barco1Img = getClass().getClassLoader().getResourceAsStream("pacotePadrao/barcoPadrao1.png");
        InputStream barco2Img = getClass().getClassLoader().getResourceAsStream("pacotePadrao/barcoPadrao2.png");
        InputStream barco3Img = getClass().getClassLoader().getResourceAsStream("pacotePadrao/barcoPadrao3.png");
        InputStream barco4Img = getClass().getClassLoader().getResourceAsStream("pacotePadrao/barcoPadrao4.png");

        try {
            byte[] bytesTema = temaImg.readAllBytes();
            byte[] bytesAvatar = avatarImg.readAllBytes();
            byte[] bytesEmbarcacoes = embarcacoesImg.readAllBytes();
            byte[] bytesBarco1 = barco1Img.readAllBytes();
            byte[] bytesBarco2 = barco2Img.readAllBytes();
            byte[] bytesBarco3 = barco3Img.readAllBytes();
            byte[] bytesBarco4 = barco4Img.readAllBytes();

            Tema tema = Tema.builder()
                    .nome("Padrão")
                    .tipoPagamento("Moeda")
                    .valor(BigDecimal.valueOf(0))
                    .imgFundo(ImageUtil.compressImage(bytesTema))
                    .build();
            tema = this.temaRepository.save(tema);

            Avatar avatar = Avatar.builder()
                    .nome("Aspirante")
                    .imgAvatar(ImageUtil.compressImage(bytesAvatar))
                    .tema(tema)
                    .build();

            Embarcacao embarcacao = Embarcacao.builder()
                    .nome("Barcos de Madeira")
                    .imgBarco1(ImageUtil.compressImage(bytesBarco1))
                    .imgBarco2(ImageUtil.compressImage(bytesBarco2))
                    .imgBarco3(ImageUtil.compressImage(bytesBarco3))
                    .imgBarco4(ImageUtil.compressImage(bytesBarco4))
                    .barcosFile(ImageUtil.compressImage(bytesEmbarcacoes))
                    .tema(tema)
                    .build();

            this.avatarRepository.save(avatar);
            this.embarcacaoRepository.save(embarcacao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
