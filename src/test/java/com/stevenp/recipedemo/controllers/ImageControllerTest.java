package com.stevenp.recipedemo.controllers;

import com.stevenp.recipedemo.services.ImageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest {

    @Mock
    private ImageService imageService;

    private ImageController imageController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }
    //TODO
//    @Test
//    public void handleImagePost() throws Exception {
//        MockMultipartFile mockMultipartFile =
//                new MockMultipartFile("file", "testing.txt", "text/plain", "recipedemo".getBytes());
//
//        mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
//                .andExpect(status().isFound())
//                .andExpect(header().string("Location", "/"));
//
//    }
}