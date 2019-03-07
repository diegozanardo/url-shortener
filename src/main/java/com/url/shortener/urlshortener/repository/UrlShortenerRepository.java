package com.url.shortener.urlshortener.repository;

import com.url.shortener.urlshortener.model.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {
}
