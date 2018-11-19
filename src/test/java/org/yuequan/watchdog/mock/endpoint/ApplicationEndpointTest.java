package org.yuequan.watchdog.mock.endpoint;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ApplicationEndpointTest {

    @Autowired
    private MockMvc mock;

    private final ObjectMapper mapper = new ObjectMapper();
    @Test
    public void index() throws Exception {
        //无数据情况
        mock.perform(get("/applications")).andExpect(status().isOk());
        //有数据情况
        mock.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"test1\"}"))
                .andExpect(status().isOk());
        mock.perform(get("/applications")).andExpect(status().isOk());
    }

    @Test
    public void create()throws Exception {
        //最少参数
        mock.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"test1\"}"))
                .andExpect(status().isOk());
        //全参
        MvcResult result = mock.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"test1\", \"redirect_uri\": [\"test_redirect_uri\"], \"scope\": [\"SELECT\"]}"))
                .andExpect(status().isOk()).andReturn();
        Map<String,Object> responseJson = responseToMap(result);
        String clientId = String.valueOf(responseJson.get("client_id"));
        String clientSecret = String.valueOf(responseJson.get("raw_client_secret"));
        String scope = toList(responseJson.get("scope")).get(0);
        Assert.assertNotNull(clientId);
        Assert.assertNotNull(clientSecret);
        Assert.assertNotNull(scope);
        //认证
        authtication(clientId ,clientSecret, scope);
    }

    @Test
    public void update()throws Exception{
        //创建一条记录
        MvcResult result = mock.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"test1\"}"))
                .andExpect(status().isOk()).andReturn();
        Map<String,Object> responseJson = responseToMap(result);
        String clientId = String.valueOf(responseJson.get("client_id"));
        Assert.assertNotNull(clientId);
        //修改创建的记录
        String willUpdateParam = "{\"name\":\"test2\",\"redirect_uri\":[\"test_redirect_uri\"],\"scope\":[\"SELECT\"]}}";
        result = mock.perform(put("/applications/"+clientId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(willUpdateParam))
                .andExpect(status().isOk()).andReturn();
        responseJson = responseToMap(result);
        Assert.assertEquals("test2",responseJson.get("name"));
        Assert.assertEquals("test_redirect_uri", toList(responseJson.get("redirect_uri")).get(0));
        Assert.assertEquals("SELECT", toList(responseJson.get("scope")).get(0));
    }

    @Test
    public void destroy() throws Exception {
        MvcResult result = mock.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"test1\"}"))
                .andExpect(status().isOk()).andReturn();
        Map<String,Object> responseJson = responseToMap(result);
        String clientId = String.valueOf(responseJson.get("client_id"));
        Assert.assertNotNull(clientId);
        mock.perform(delete("/applications/"+clientId)).andExpect(status().isNoContent());
    }

    public void show() throws Exception {
        MvcResult result = mock.perform(post("/applications")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"test1\"}"))
                .andExpect(status().isOk()).andReturn();
        Map<String,Object> responseJson = responseToMap(result);
        String clientId = String.valueOf(responseJson.get("client_id"));
        Assert.assertNotNull(clientId);
        mock.perform(get("/applications/"+clientId)).andExpect(status().isOk());
    }

    private void authtication(String clientId, String clientSecret, String scope) throws Exception {
        String tokenAuthUrl = "/oauth/token?username=test1&password=123456&grant_type=password&scope="+scope+"&client_id="+clientId+"&client_secret="+clientSecret;
        mock.perform(post(tokenAuthUrl)).andExpect(status().isOk());
    }

    private Map<String, Object> responseToMap(MvcResult result) throws IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), Map.class);
    }

    public List<String> toList(Object object){
        return ((ArrayList<String>) object);
    }
}
