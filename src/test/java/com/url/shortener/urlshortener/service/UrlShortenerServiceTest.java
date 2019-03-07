package com.url.shortener.urlshortener.service;

import com.url.shortener.urlshortener.dto.ShortUrl;
import com.url.shortener.urlshortener.dto.ShortUrlStatistics;
import com.url.shortener.urlshortener.exception.BadRequestException;
import com.url.shortener.urlshortener.exception.NotFoundException;
import com.url.shortener.urlshortener.model.UrlShortener;
import com.url.shortener.urlshortener.repository.UrlShortenerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;

import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlShortenerServiceTest {

    private static final String EMPTY_URL = "";
    private static final String VALID_URL = "http://facebook.com";
    private static final String TINY_URL_FACEBOOK = "bM";
    private static final String INVALID_URL = "facebook.com";
    private static final String ADDRESS = "http://localhost:8080";

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

        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setId(id);
        urlShortener.setOriginalUrl(longUrl);

        when(urlShortenerRepository.findById(id)).thenReturn(Optional.of(urlShortener));
        when(urlShortenerRepository.save(any(UrlShortener.class))).thenReturn(null);

        ShortUrl result = urlShortenerService.getUrlShortener(shortUrl);

        Assert.assertEquals(expect, result);
        verify(urlShortenerRepository, times(1)).save(any());

    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnNotFoundWhenGetUrlShortener() {
        Long id = 100L;
        String shortUrl = this.TINY_URL_FACEBOOK;
        String longUrl = this.VALID_URL;

        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setId(id);
        urlShortener.setOriginalUrl(longUrl);

        ShortUrl result = urlShortenerService.getUrlShortener(shortUrl);
    }

    @Test
    public void shouldReturnTheShortUrlWhenCreateUrlShortener() {
        String longUrl = this.VALID_URL;
        ShortUrl shortUrl = new ShortUrl(longUrl);
        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setId(100L);
        urlShortener.setOriginalUrl(longUrl);
        ShortUrl expect = new ShortUrl(String.format("%s/%s", this.ADDRESS, this.TINY_URL_FACEBOOK));

        when(urlShortenerRepository.save(any(UrlShortener.class))).thenReturn(urlShortener);

        ShortUrl result = urlShortenerService.createUrlShortener(shortUrl, ADDRESS);

        Assert.assertEquals(result, expect);
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnTheBadRequestWhenCreateUrlShortenerReceiveidAEmptyUrl() {
        String longUrl = this.EMPTY_URL;
        ShortUrl shortUrl = new ShortUrl(longUrl);
        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setId(100L);
        urlShortener.setOriginalUrl(longUrl);

        ShortUrl result = urlShortenerService.createUrlShortener(shortUrl, ADDRESS);
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnTheBadRequestWhenCreateUrlShortenerReceiveidAInvalidUrl() {
        String longUrl = this.INVALID_URL;
        ShortUrl shortUrl = new ShortUrl(longUrl);
        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setId(100L);
        urlShortener.setOriginalUrl(longUrl);

        ShortUrl result = urlShortenerService.createUrlShortener(shortUrl, ADDRESS);
    }

    @Test
    public void shouldReturnShortUrlStatisticsWhengetStatistics() {
        LocalDateTime lastHit = LocalDateTime.of(2015, 10, 1, 1, 1);
        LocalDateTime created = LocalDateTime.of(2010, 10, 1, 1, 1);
        int hits = 10;
        long id = 100L;

        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setLastHitAt(lastHit);
        urlShortener.setHits(hits);
        urlShortener.setId(id);
        urlShortener.setCreateadAt(created);
        urlShortener.setOriginalUrl(this.VALID_URL);

        ShortUrlStatistics expected = new ShortUrlStatistics(this.VALID_URL, created, lastHit, hits);

        when(urlShortenerRepository.findById(id)).thenReturn(Optional.of(urlShortener));

        ShortUrlStatistics result = urlShortenerService.getStatistics(this.TINY_URL_FACEBOOK);

        Assert.assertEquals(expected, result);
    }
}
