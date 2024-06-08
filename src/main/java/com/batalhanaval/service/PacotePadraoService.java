package com.batalhanaval.service;

import com.batalhanaval.dtos.PacoteInputModel;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Component
@Profile("pacote-padrao")
public class PacotePadraoService implements SmartInitializingSingleton {

    private final PacoteService pacoteService;

    public PacotePadraoService(PacoteService pacoteService) {
        this.pacoteService = pacoteService;
    }


    @Override
    public void afterSingletonsInstantiated() {
        Resource src = new ClassPathResource("imgFundo.png");
        try {
            File file = src.getFile();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }





        try {
            this.pacoteService.postPacote(input, false, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
