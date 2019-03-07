package com.url.shortener.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UrlShortener")
public class UrlShortener {

    public UrlShortener(String originalUrl){
        this.originalUrl = originalUrl;
        this.createadAt = LocalDateTime.now();
        this.hits = 0;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String originalUrl;

    private LocalDateTime createadAt;

    private LocalDateTime lastHitAt;

    private int hits;
}
