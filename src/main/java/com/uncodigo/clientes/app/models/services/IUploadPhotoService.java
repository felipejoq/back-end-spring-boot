package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.entity.Client;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadPhotoService {
    String upload(MultipartFile file, Client client) throws Exception;

    Boolean deleteImgFromCloudinary(Client client);
}
