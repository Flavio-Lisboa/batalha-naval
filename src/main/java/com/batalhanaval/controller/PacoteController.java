package com.batalhanaval.controller;

import com.batalhanaval.dtos.PacoteInputModel;
import com.batalhanaval.dtos.PacoteModel;
import com.batalhanaval.dtos.PacoteResponseModel;
import com.batalhanaval.dtos.PacoteUpdateModel;
import com.batalhanaval.service.PacoteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("pacotes")
public class PacoteController {

    private final PacoteService pacoteService;

    public PacoteController(PacoteService pacoteService) {
        this.pacoteService = pacoteService;
    }

    @PostMapping
    @CrossOrigin
    public PacoteResponseModel postPacote(
            @RequestParam("imageFundo") MultipartFile imageFundo,
            @RequestParam("imageAvatar") MultipartFile imageAvatar,
            @RequestParam("barco4File") MultipartFile barco4File,
            @RequestParam("barco3File") MultipartFile barco3File,
            @RequestParam("barco2File") MultipartFile barco2File,
            @RequestParam("barco1File") MultipartFile barco1File,
            @RequestParam("barcosFile") MultipartFile barcosFile,
            @RequestParam() BigDecimal valor,
            @RequestParam() String tipoPagamento,
            @RequestParam() String nomeTema,
            @RequestParam() String nomeAvatar,
            @RequestParam() String nomeEmbarcacoes
            ) throws IOException {

        PacoteInputModel inputModel = PacoteInputModel.builder()
                .imageFundo(imageFundo)
                .barco4File(barco4File)
                .barco3File(barco3File)
                .barco2File(barco2File)
                .barco1File(barco1File)
                .barcosFile(barcosFile)
                .imageAvatar(imageAvatar)
                .valor(valor)
                .nomeTema(nomeTema)
                .nomeAvatar(nomeAvatar)
                .nomeEmbarcacoes(nomeEmbarcacoes)
                .tipoPagamento(tipoPagamento)
                .build();

        return this.pacoteService.postPacote(inputModel, false, null);
    }

    @PutMapping("{temaId}")
    @CrossOrigin
    public PacoteResponseModel putPacote(
            @PathVariable Long temaId,
            @RequestParam("imageFundo") MultipartFile imageFundo,
            @RequestParam("imageAvatar") MultipartFile imageAvatar,
            @RequestParam("barco4File") MultipartFile barco4File,
            @RequestParam("barco3File") MultipartFile barco3File,
            @RequestParam("barco2File") MultipartFile barco2File,
            @RequestParam("barco1File") MultipartFile barco1File,
            @RequestParam("barcosFile") MultipartFile barcosFile,
            @RequestParam() BigDecimal valor,
            @RequestParam() String tipoPagamento,
            @RequestParam() String nomeTema,
            @RequestParam() String nomeAvatar,
            @RequestParam() String nomeEmbarcacoes
    ) throws IOException {

        PacoteInputModel inputModel = PacoteInputModel.builder()
                .imageFundo(imageFundo)
                .barco4File(barco4File)
                .barco3File(barco3File)
                .barco2File(barco2File)
                .barco1File(barco1File)
                .barcosFile(barcosFile)
                .imageAvatar(imageAvatar)
                .valor(valor)
                .nomeTema(nomeTema)
                .nomeAvatar(nomeAvatar)
                .nomeEmbarcacoes(nomeEmbarcacoes)
                .tipoPagamento(tipoPagamento)
                .build();

        return this.pacoteService.update(temaId, inputModel);
    }

    @GetMapping
    @CrossOrigin
    public List<PacoteModel> getAll() {
        return this.pacoteService.getAll();
    }

    @GetMapping("{temaId}")
    @CrossOrigin
    public PacoteUpdateModel getPacote(@PathVariable Long temaId) {
        return this.pacoteService.getPacote(temaId);
    }

    @DeleteMapping("{temaId}")
    @CrossOrigin
    public void delete(@PathVariable Long temaId) {
        this.pacoteService.delete(temaId);
    }
}
