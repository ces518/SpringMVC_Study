package me.june.demowebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void fileUpload() throws Exception {
        /**
         * Spring Mvc 에서 제공하는 MockMultipartFile class
         * 파일태그명 , 파일명 , 컨텐츠타입 , 해당파일의 본문 순서대로 넣어주면 mockMultipartFile이 생성된다.
         */

        MockMultipartFile files = new MockMultipartFile("file"
                                                        ,"file.txt"
                                                        ,"text/plan"
                                                        ,"Hello world".getBytes());
        /**
         * multipart()
         * - mulipart요청을 보낼수있다 (post)
         */
        this.mockMvc.perform(multipart("/files").file(files))
                        .andDo(print())
                        .andExpect(status().is3xxRedirection());
    }


}