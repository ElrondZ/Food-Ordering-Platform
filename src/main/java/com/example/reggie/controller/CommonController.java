package com.example.reggie.controller;


import com.example.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // param "file" needs to be the same of the content-Disposition "name=file"
        // it can be found in the develop tool /Network/headers/Form Data

        // for now, file in here is temp, needs to transfer to a specific location to access later on.

        // original file name
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // use UUID to generate random file name to avoid file replacing due to the same file name
        String randomFilename = UUID.randomUUID().toString()+suffix;

        // if save directory not exists, create one
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            file.transferTo(new File(basePath+randomFilename)); // Auto File Save Path, it configured in the application.yml
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info(file.toString());
        return R.success(randomFilename); // for display in the frontend store dish info >> backend/page/demo/upload,html >> response.data
    }


    // for the first time visit, download data for future use.
    // 页面回显
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            // input flow(if): use if to get file name and download it
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));

            // output flow(of): use of to display file back to the frontend
            ServletOutputStream outputStream = response.getOutputStream();

            // set response file type
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush(); // refresh
            }


            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
