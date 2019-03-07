package com.url.shortener.urlshortener.service;

import org.junit.Assert;
import org.junit.Test;

public class GeneratorUrlServiceTest {

    @Test
    public void shouldEncodeTheIntWhenCallEncode(){
        int id = 1000;
        String expect = "qi";

        String result = GeneratorUrlService.encode(id);

        Assert.assertEquals(expect, result);
    }

    @Test
    public void shouldDecodeTheStringWhenCallDecode(){
        int expect = 1000;
        String key = "qi";

        int result = GeneratorUrlService.decode(key);

        Assert.assertEquals(expect, result);
    }

    @Test
    public void shouldReturnZeroWhenCallDecodeWithEmptyString(){
        int expect = 0;
        String key = "";

        int result = GeneratorUrlService.decode(key);

        Assert.assertEquals(expect, result);
    }

    @Test
    public void shouldReturnEmptyWhenCallEncodeWithZero(){
        int id = 0;
        String expect = "";

        String result = GeneratorUrlService.encode(id);

        Assert.assertEquals(expect, result);
    }
}
