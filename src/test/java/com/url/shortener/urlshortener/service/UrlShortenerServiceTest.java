package com.url.shortener.urlshortener.service;

import com.url.shortener.urlshortener.dto.ShortUrl;
import com.url.shortener.urlshortener.exception.BadRequestException;
import com.url.shortener.urlshortener.exception.NotFoundException;
import com.url.shortener.urlshortener.model.UrlShortener;
import com.url.shortener.urlshortener.repository.UrlShortenerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlShortenerServiceTest {

    private static final String EMPTY_URL = "";
    private static final String VALID_URL = "http://facebook.com";
    private static final String TINY_URL_FACEBOOK = "bM";
    private static final String INVALID_URL = "facebook.com";

    @Mock
    private UrlShortenerRepository urlShortenerRepository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @Test
    public void shouldReturnTheLongUrlWhenGetUrlShortener() {
        Long id = Long.valueOf(1000);
        String shortUrl = "qi";
        String longUrl = this.VALID_URL;

        ShortUrl expect = new ShortUrl(longUrl);

        UrlShortener urlShortener = new UrlShortener(id, longUrl);

        when(urlShortenerRepository.findById(id)).thenReturn(Optional.of(urlShortener));

        ShortUrl result = urlShortenerService.getUrlShortener(shortUrl);

        Assert.assertEquals(expect, result);
    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnNotFoundWhenGetUrlShortener() {
        Long id = 100L;
        String shortUrl = this.TINY_URL_FACEBOOK;
        String longUrl = this.VALID_URL;

        UrlShortener urlShortener = new UrlShortener(id, longUrl);

        ShortUrl result = urlShortenerService.getUrlShortener(shortUrl);
    }

    @Test
    public void shouldReturnTheShortUrlWhenCreateUrlShortener() {
        String longUrl = this.VALID_URL;
        ShortUrl shortUrl = new ShortUrl(longUrl);
        UrlShortener urlShortener = new UrlShortener(100L, longUrl);
        ShortUrl expect = new ShortUrl(this.TINY_URL_FACEBOOK);

        when(urlShortenerRepository.save(any(UrlShortener.class))).thenReturn(urlShortener);

        ShortUrl result = urlShortenerService.createUrlShortener(shortUrl);

        Assert.assertEquals(result, expect);
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnTheBadRequestWhenCreateUrlShortenerReceiveidAEmptyUrl() {
        String longUrl = this.EMPTY_URL;
        ShortUrl shortUrl = new ShortUrl(longUrl);
        UrlShortener urlShortener = new UrlShortener(100L, longUrl);

        ShortUrl result = urlShortenerService.createUrlShortener(shortUrl);
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnTheBadRequestWhenCreateUrlShortenerReceiveidAInvalidUrl() {
        String longUrl = this.INVALID_URL;
        ShortUrl shortUrl = new ShortUrl(longUrl);
        UrlShortener urlShortener = new UrlShortener(100L, longUrl);

        ShortUrl result = urlShortenerService.createUrlShortener(shortUrl);
    }
}
