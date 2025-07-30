package io.github.sseregit.realtimelab.longpolling;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockAsyncContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import jakarta.servlet.AsyncListener;

@WebMvcTest(LongPollingController.class)
class LongPollingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void LongPolling_노_메시지_타임아웃() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/longpolling")
                .param("userId", "1"))
            .andExpect(request().asyncStarted())
            .andReturn();

        simulateTimeout(mvcResult);

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(content().string("no message"));
    }

    /**
     * MockMvc는 실제 서블릿과 달리 타임아웃 이벤트를 자동으로 실행하지 않는다.
     * 타임아웃 테스트를 위해 Timeout을 수동으로 호출시켜야 테스트가 가능하다.
     * @param mvcResult
     * @throws IOException
     */
    void simulateTimeout(MvcResult mvcResult) throws IOException {
        MockAsyncContext ctx = (MockAsyncContext)mvcResult.getRequest().getAsyncContext();

        for (AsyncListener listener : ctx.getListeners()) {
            listener.onTimeout(null);
        }
    }

}