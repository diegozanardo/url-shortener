package com.url.shortener.urlshortener.controller;

import com.url.shortener.urlshortener.dto.ShortUrl;
import com.url.shortener.urlshortener.enumeration.Errors;
import com.url.shortener.urlshortener.enumeration.Messages;
import com.url.shortener.urlshortener.service.UrlShortenerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @ApiOperation(value = "Redirect a short URL to original URL")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Messages.URL_CREATED),
            @ApiResponse(code = 404, message = Errors.NOT_FOUND) })
    @RequestMapping(value = "/shortener/{key}", method = RequestMethod.GET)
    public ResponseEntity<Void> redirect(@ApiParam(value = "Key", required = true)
                                         @PathVariable("key") String key) {
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, urlShortenerService.getUrlShortener(key).getUrl())
                .build();
    }

    @ApiOperation(value = "Create a new Short Url")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Messages.URL_CREATED),
            @ApiResponse(code = 400, message = Errors.INVALID_INPUT) })
    @RequestMapping(value = "/shortener",
                    produces = { "application/json" },
                    consumes = { "application/json" },
                    method = RequestMethod.POST)
    public ResponseEntity<ShortUrl> create(@ApiParam(value = "URL to be shortened.")
                                           @Validated @RequestBody ShortUrl shortUrl,
                                           HttpServletRequest request) {

        String address = request.getRequestURL().toString();
        ShortUrl createdShortUrl = urlShortenerService.createUrlShortener(shortUrl, address);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdShortUrl);
    }
}
