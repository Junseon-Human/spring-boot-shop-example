package com.keduit.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFIleName, byte[] fileData) throws Exception {
        
        // Universally Unique IdentiFier : 범용 고유 식별자, 중복되지 않는 유일한 값을 구성할 때 사용
        UUID uuid = UUID.randomUUID();
        String extention = originalFIleName.substring(originalFIleName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extention;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

}
