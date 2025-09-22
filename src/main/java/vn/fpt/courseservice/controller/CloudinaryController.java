package vn.fpt.courseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.fpt.courseservice.service.CloudinaryService;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CloudinaryController {

    private final CloudinaryService  cloudinaryService;

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam MultipartFile file) throws IOException {
        return cloudinaryService.uploadFile(file);
    }

}
