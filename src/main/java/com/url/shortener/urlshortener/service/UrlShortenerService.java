package com.url.shortener.urlshortener.service;

import com.url.shortener.urlshortener.dto.ShortUrl;
import com.url.shortener.urlshortener.dto.ShortUrlStatistics;
import com.url.shortener.urlshortener.enumeration.Errors;
import com.url.shortener.urlshortener.exception.BadRequestException;
import com.url.shortener.urlshortener.exception.NotFoundException;
import com.url.shortener.urlshortener.model.UrlShortener;
import com.url.shortener.urlshortener.repository.UrlShortenerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UrlShortenerService {

    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    public ShortUrl getUrlShortener(String key) {

        UrlShortener urlShortener = getUrlShortenerDb(key);

        urlShortener.setHits(urlShortener.getHits() + 1);
        urlShortener.setLastHitAt(LocalDateTime.now());
        urlShortenerRepository.save(urlShortener);

        return new ShortUrl(urlShortener.getOriginalUrl());
    }

    public ShortUrl createUrlShortener(ShortUrl shortUrl, String address) {
        if (shortUrl == null || shortUrl.getUrl().isEmpty()) {
            throw new BadRequestException(Errors.EMPTY);
        }

        try {
            new URL(shortUrl.getUrl());
        } catch (MalformedURLException e) {
            throw new BadRequestException(Errors.INVALID_URL);
        }

        UrlShortener urlShortener = new UrlShortener(shortUrl.getUrl());
        urlShortener = urlShortenerRepository.save(urlShortener);

        String tinyUrl = GeneratorUrlService.encode((int) (long) urlShortener.getId());
        tinyUrl = String.format("%s/%s", address, tinyUrl);

        log.info("Create new url shortener {}", urlShortener);

        return new ShortUrl(tinyUrl);
    }

    private UrlShortener getUrlShortenerDb(String key) {
        int id = GeneratorUrlService.decode(key);

        return urlShortenerRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException(Errors.NOT_FOUND));
    }

    public ShortUrlStatistics getStatistics(String key) {
        UrlShortener urlShortener = getUrlShortenerDb(key);

        return new ShortUrlStatistics(urlShortener.getOriginalUrl(),
                urlShortener.getCreateadAt(), urlShortener.getLastHitAt(), urlShortener.getHits());

    }

}



