package vn.fpt.courseservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        Map upload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return upload.get("url").toString();
    }
}
