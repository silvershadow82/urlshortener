package com.silvershadow.urlshortener.service;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class ConverterServiceTest {

    @InjectMocks
    private ConverterService converterService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void convertToBase62() throws Exception {
        String shortValue = converterService.shortenString("http://sarabethsrestaurants.com/press/");

        assertThat(shortValue, is("oCba4"));
    }

    @Test
    public void convertPrettyLongUrlToBase62() throws Exception {
        String shortValue = converterService.shortenString("https://www.google.com/maps/dir/30-19+Northern+Boulevard,+Long+Island+City,+NY/40.811434,-73.3661209/@40.7641773,-73.7914397,11z/data=!3m1!4b1!4m8!4m7!1m5!1m1!1s0x89c25f2b16090af1:0x3991001b5d761c76!2m2!1d-73.93445!2d40.75158!1m0");

        assertThat(shortValue, is("gWRnl"));
    }

    @Test
    public void convertEmpty() throws Exception {
        String shortValue = converterService.shortenString("");

        assertThat(shortValue, is(""));
    }


    @Test
    public void retrieveOriginal() throws Exception {
        String shortValue = converterService.shortenString("http://sarabethsrestaurants.com/press/");
        String originalUrl = converterService.getOriginalString(shortValue);
        assertThat(originalUrl, is("http://sarabethsrestaurants.com/press/"));
    }


    @Test
    public void retrieveOriginalNonExistentKey() throws Exception {
        String originalUrl = converterService.getOriginalString("af95gFc");
        assertThat(originalUrl, is(""));
    }
}