package com.silvershadow.urlshortener.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class URLResponseDTO {
    private String originalURL;
    private String shortURL;
}
