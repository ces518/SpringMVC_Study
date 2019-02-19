package me.june.demowebmvc;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class FileController {

    /**
     * MultipartFile
     * - 파일업로드시 사용하는 메서드 아규먼트
     * - MultipartResolver 빈이 등록되어 있을시 사용할 수 있다. (부트는 자동적으로 등록.)
     * - POST mulitpart/form-data 요청에 들어있는 파일을 참조할수있다.
     * - List<MultipartFile> 형태로 여러개의 파일을 참조할 수 있다.
     *
     *  * 파일업로드 관련 스프링부트 설정 클래스
     *  - MultipartAutoConfiguration
     *  - MultipartProperties
     *
     * @param model
     * @return
     */
    @GetMapping("/files")
    public String files(ModelMap model) {
        return "files/form";
    }


    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/files")
    public String uploadFiles(
            @RequestParam MultipartFile file
            , RedirectAttributes redirectAttributes
    ) throws IOException {
        System.out.println(file.getOriginalFilename());

        try {
            File files = new File("/Users/june/IdeaProjects/demo-web-mvc/src/main/webapp/WEB-INF/upload/"+file.getOriginalFilename());
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("message",file.getOriginalFilename() + "is uploaded");
        return "redirect:/files";
    }

    /**
     * Content-disposition 헤더
     * MIME Content-disposition 헤더는 본문 부분에 대한 표시 정보를 제공합니다. 이 헤더를 첨부 파일에 추가하여 첨부 파일의 본문 부분을 표시할지(inline) 복사할 파일 이름으로 표시할지(attachment) 여부를 지정하는 경우도 있습니다. Content-disposition 헤더의 형식은 다음과 같습니다.
     *
     * Content-disposition: disposition_type; parameter1=value;parameter2=value...
     *
     * disposition_type은 일반적으로 inline(본문 부분 표시) 또는 attachment(저장할 파일로 표시)입니다. Attachment에는 일반적으로 저장된 파일에 대한 이름을 제안하는 값이 있는 filename 매개 변수가 있습니다.
     *
     *
     *  파일 리소스 읽어오는방법
     *  - ResourceLoader 사용..
     *
     *  파일 다운로드 응답헤더 설정
     *  - Content-Disposition : 사용자가 다운받을때 사용할 파일명
     *  - Content-type : 미디어타입
     *  - Content-Length : 파일의 크기
     *
     *  org.apache.tika : 파일의 미디어타입을 알아내는 lib
     *
     *  * ResponseEntity
     *  - 응답상태코드
     *  - 응답헤더
     *  - 응답본문
     *  을 보낼수있으며
     *  ResponseEntity 자체가 응답본문이므로 ResponseBody가 필요없다.
     *
     * @param filename
     * @return
     * @throws IOException
     */
    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> filesDownload(@PathVariable String filename) throws IOException {
        Resource resource = resourceLoader.getResource("/WEB-INF/upload/" + filename);

        File file = resource.getFile();

        /* 해당 미디어 타입을 알아내는 org.apache.tika*/
        Tika tika = new Tika();
        String mediaType = tika.detect(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", "\""+filename+"\"" ))
                .header(HttpHeaders.CONTENT_TYPE,mediaType)
                .header(HttpHeaders.CONTENT_LENGTH,file.length() + "")
                .body(resource);
    }
}
