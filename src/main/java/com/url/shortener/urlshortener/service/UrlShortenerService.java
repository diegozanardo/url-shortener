package com.url.shortener.urlshortener.service;

import com.url.shortener.urlshortener.dto.ShortUrl;
import com.url.shortener.urlshortener.enumeration.Errors;
import com.url.shortener.urlshortener.exception.BadRequestException;
import com.url.shortener.urlshortener.model.UrlShortener;
import com.url.shortener.urlshortener.repository.UrlShortenerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
@Slf4j
public class UrlShortenerService {

    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    public ShortUrl getUrlShortener(String key) {
        int id = GeneratorUrlService.decode(key);

        UrlShortener urlShortener = urlShortenerRepository.findById(Long.valueOf(id)).orElse(new UrlShortener());

        return new ShortUrl(urlShortener.getOriginalUrl());
    }

    public ShortUrl createUrlShortener(ShortUrl shortUrl) {
        if (shortUrl == null || shortUrl.getUrl().isEmpty()) {
            throw new BadRequestException(Errors.EMPTY);
        }

        try {
            new URL(shortUrl.getUrl());
        } catch (MalformedURLException e) {
            throw new BadRequestException(Errors.INVALID_URL);
        }

        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setOriginalUrl(shortUrl.getUrl());

        urlShortenerRepository.save(urlShortener);

        String tinyUrl = GeneratorUrlService.encode((int) (long) urlShortener.getId());

        log.info(tinyUrl);

        return new ShortUrl(tinyUrl);
    }
}



