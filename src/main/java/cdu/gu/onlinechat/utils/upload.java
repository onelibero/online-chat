package cdu.gu.onlinechat.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class upload {
    @Value("${avatar.path}")
    private String avatarPath;


    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        // 创建保存目录，如果不存在
        File directory = new File(avatarPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 生成唯一的文件名
        //获取文件名
        String originalFileName = file.getOriginalFilename();
        //获取后缀
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        //生成新名字
        String newFileName = UUID.randomUUID().toString() + extension;

        // 保存文件
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = Paths.get(avatarPath, newFileName);
            Files.copy(inputStream, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回图片的访问地址
        String imageUrl = "/images/" + newFileName; // 这里假设你的图片在 "/uploads" 目录下

        return imageUrl;
    }
}
