package com.silvershadow.urlshortener.web;

import com.silvershadow.urlshortener.service.ConverterService;
import com.silvershadow.urlshortener.web.dto.URLRequestDTO;
import com.silvershadow.urlshortener.web.dto.URLResponseDTO;
import com.silvershadow.urlshortener.web.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
public class UrlShortenerController {

    @Resource
    private ConverterService converterService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public URLResponseDTO shortenURL(HttpServletRequest request, @RequestBody URLRequestDTO urlRequestDTO) throws MalformedURLException, URISyntaxException {
        // Compute unique value for the url and then convert it to base62
        String shortKey = converterService.shortenString(urlRequestDTO.getUrl());
        URL requestURL = new URL(request.getRequestURL().toString());

        URI shortURL = new URI(requestURL.getProtocol(), null, requestURL.getHost(), requestURL.getPort(), "/go/" + shortKey, null, null);

        return new URLResponseDTO(urlRequestDTO.getUrl(), shortURL.toString());
    }

    @RequestMapping(value = "/go/{alias}", method = RequestMethod.GET)
    public ModelAndView goToOriginalURL(@PathVariable("alias") String alias) throws IOException {
        String originalURL = converterService.getOriginalString(alias);

        if(originalURL.isEmpty())
            throw new NotFoundException();

        return new ModelAndView(new RedirectView(originalURL));
    }
}
