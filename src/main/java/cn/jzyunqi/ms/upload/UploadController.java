package cn.jzyunqi.ms.upload;

import cn.jzyunqi.common.utils.IOUtilPlus;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UploadController {

    @PostMapping(value = "/upload")
    public void singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
//        Path path = Paths.get("/static/uploads/" + file.getOriginalFilename());
        System.out.print(
                FileUtils.getUserDirectoryPath()
        );
        FileUtils.writeByteArrayToFile( new File("/Users/feizheng/github/yq-springboot/src/main/resources/uploads/" + file.getOriginalFilename()), bytes, false);
//        IOUtilPlus.write(bytes,path);
    }
}
