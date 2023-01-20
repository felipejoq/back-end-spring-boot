package com.uncodigo.clientes.app.models.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uncodigo.clientes.app.models.entity.Client;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service("PhotoService")
public class UploadPhotoImpl implements IUploadPhotoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Set Cloudinary credentials
    Dotenv dotenv = Dotenv.load();
    private Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));

    @Override
    public String upload(MultipartFile file, Client client) throws Exception {

        this.cloudinary.config.secure = true;

        System.out.println("Cloudinary: ***********" + this.cloudinary.config.cloudName);

        if (client.getImgUrl() != null) {

            this.deleteImgFromCloudinary(client);

        }

        Map<String, Boolean> params = new HashMap<>();
        params.put("use_filename", true);
        params.put("unique_filename", true);
        params.put("overwrite", false);

        Map uploadResult = this.cloudinary.uploader().upload(file.getBytes(), params);
        String publicId = (String) uploadResult.get("public_id");

        Map<String, Boolean> params1 = new HashMap<>();
        params1.put("quality_analysis", true);

        return (String) this.cloudinary.api().resource(publicId, params1).get("secure_url");
    }

    @Override
    public Boolean deleteImgFromCloudinary(Client client) {

        Boolean result = false;

        // Delete image if exits.
        String[] arrFromUrl = client.getImgUrl().split("/");
        String publicId = arrFromUrl[arrFromUrl.length - 1].split("\\.")[0];

        try {
            Map resp = this.cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "image"));
            if(resp.get("result").equals("ok")) result = true;
            logger.info(resp.toString() + result);
        } catch (IOException e) {
            logger.error("OCURRIÓ UN ERROR: " + e.getMessage());
        }

        return result;
    }

}
