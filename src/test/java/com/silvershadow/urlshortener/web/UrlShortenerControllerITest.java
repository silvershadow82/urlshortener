package com.silvershadow.urlshortener.web;

import com.silvershadow.urlshortener.web.dto.URLRequestDTO;
import com.silvershadow.urlshortener.web.dto.URLResponseDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlShortenerControllerITest {

    @LocalServerPort
    private int port;

    private URL baseURL;

    @Resource
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        baseURL = new URL(String.format("http://localhost:%d", port));
    }

    @Test
    public void shortenURL() throws Exception {

        ResponseEntity<URLResponseDTO> entity = getShortURL("http://sarabethsrestaurants.com/press/");

        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(entity.getBody().getShortURL(), is(baseURL + "/go/oCba4"));
    }

    @Test
    public void goToURL() throws Exception {

        ResponseEntity<URLResponseDTO> shortURLEntity = getShortURL("http://www.google.com/maps/1414525,25236826");

        ResponseEntity<?> responseEntity = restTemplate.getForEntity(shortURLEntity.getBody().getShortURL(), String.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(responseEntity.getHeaders().getLocation().toString(), is(shortURLEntity.getBody().getOriginalURL()));
    }

    private ResponseEntity<URLResponseDTO> getShortURL(String url) throws URISyntaxException {
        String submitURL = baseURL.toString() + "/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<URLRequestDTO> requestEntity =
                new HttpEntity<>(new URLRequestDTO(url), headers);

        return restTemplate.postForEntity(new URI(submitURL),
                requestEntity,
                URLResponseDTO.class);
    }


}