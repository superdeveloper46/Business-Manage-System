package app.com.ChinChen.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class FileUploadController {
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    public String filePath = "/ChinChen/html/uploadFile/";
    // public String path = "D:/uploadFile/";

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile uploadFile, HttpServletRequest req, String url) {
        // 上傳的檔案將儲存在專案執行目錄下的 uploadFile 資料夾，
        // String realPath =
        // req.getSession().getServletContext().getRealPath("/uploadFile/");
        String realPath = filePath + url;

        // 並且在 uploadFile 資料夾中通過日期對上傳的檔案歸類儲存
        // 比如：/uploadFile/2019/06/06/32091e5f-c9e9-4506-9567-43e724f1fe37.png
        // String format = sdf.format(new Date());
        // File folder = new File(realPath + format);
        File folder = new File(realPath);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        // 對上傳的檔案重新命名，避免檔案重名
        String oldName = uploadFile.getOriginalFilename();
        String newName = "";
        if (oldName != null) {
            newName = UUID.randomUUID().toString()
                    + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        }
        System.out.println("上傳路徑：" + realPath + newName);
        try {
            // 檔案儲存
            uploadFile.transferTo(new File(folder, newName));

            // 返回上傳檔案的存取路徑
            // String filePath = req.getScheme() + "://" + req.getServerName()
            // + ":" + req.getServerPort() + "/uploadFile/" + format + oldName;
            String json = "{\"name\":\"" + newName + "\"}";
            return ResponseEntity
                    .ok()
                    .body(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return "上傳失敗!";
        return ResponseEntity
                .ok()
                .body("Error");
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile(String path) throws IOException {
        String filename = filePath + path;
        File file = new File(filename);
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (Exception ex) {
            HashMap<String, String> body = new HashMap<String, String>();
            body.put("msg", ex.getMessage());
            return ResponseEntity.unprocessableEntity().body(body);
        }
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }
}
