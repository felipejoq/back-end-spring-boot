package com.uncodigo.clientes.app.models.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uncodigo.clientes.app.models.entity.Client;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service("uploadPhotoService")
public class UploadPhotoImpl implements IUploadPhotoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String upload(MultipartFile file, Client client) throws Exception {

        // Set Cloudinary credentials
        Dotenv dotenv = Dotenv.load();
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        System.out.println("Cloudinary: ***********" + cloudinary.config.cloudName);

        if (client.getImgUrl() != null) {
            // Delete image if exits.
            String[] arrFromUrl = client.getImgUrl().split("/");
            String publicId = arrFromUrl[arrFromUrl.length - 1].split("\\.")[0];

            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "image"));

        }

        Map<String, Boolean> params = new HashMap<>();
        params.put("use_filename", true);
        params.put("unique_filename", true);
        params.put("overwrite", false);

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
        String publicId = (String) uploadResult.get("public_id");

        Map<String, Boolean> params1 = new HashMap<>();
        params1.put("quality_analysis", true);

        return (String) cloudinary.api().resource(publicId, params1).get("secure_url");
    }

}
