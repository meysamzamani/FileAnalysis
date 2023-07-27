package com.meysamzamani.file_analysis.services;

import com.meysamzamani.file_analysis.exceptions.StorageFileNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Paths;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FileSystemStorageServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IStorageService storageService;

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "FILE ANALYSIS Project".getBytes());
        mvc.perform(multipart("/api/v1/file").file(multipartFile))
                .andExpect(status().isCreated());

        then(storageService).should().store(multipartFile);
    }

    @Test
    public void should400WhenEmptyFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("test_empty", "",
                MediaType.APPLICATION_OCTET_STREAM_VALUE, new byte[0]);
        mvc.perform(multipart("/api/v1/file").file(multipartFile))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should404WhenMissingFile() throws Exception {
        given(storageService.loadAsResource("test.txt"))
                .willThrow(StorageFileNotFoundException.class);
        mvc.perform(get("/files/test.txt")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldListAllFiles() throws Exception {
        given(storageService.loadAll())
                .willReturn(List.of(Paths.get("test.txt"), Paths.get("test1.txt")));
        mvc.perform(get("/api/v1/file/")
                .header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name", Matchers.contains("test.txt", "test1.txt")));
    }
}