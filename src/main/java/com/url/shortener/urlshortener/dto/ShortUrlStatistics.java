package com.url.shortener.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlStatistics {
    private String originalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime lastHitAt;
    private int hits;
}
