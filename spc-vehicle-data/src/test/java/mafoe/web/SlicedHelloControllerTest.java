package mafoe.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MVC Test that doesn't use @{@link SpringBootTest} to pull in the entire application context, but only loads a slice
 * of that, in this case the Web MVC slice.
 * <br/>
 * Virtually the same test as {@link HelloControllerTest}, except using @WebMvcTest to narrow down what we need for the
 * test.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class SlicedHelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("'Democracy Dies in Darkness', says the Washington Post")));
    }
}