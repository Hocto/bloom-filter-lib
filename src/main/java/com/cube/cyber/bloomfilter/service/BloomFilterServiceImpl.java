package com.cube.cyber.bloomfilter.service;

import org.roaringbitmap.RoaringBitmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BloomFilterServiceImpl implements BloomFilterService{

    private static Logger logger = LoggerFactory.getLogger(BloomFilterServiceImpl.class);

    private HashService hashService;
    private RoaringBitmap bitmap;

    public BloomFilterServiceImpl() {
        this.hashService = new WordHashServiceImpl();
        this.bitmap = RoaringBitmap.bitmapOf();
    }

    @Override
    public void add(String s) {
        logger.info("BloomFilterServiceImpl - Adding new key with hashcode and hash with complexity 8: {}", s);
        bitmap.add(hashService.hash(s, 8));
        bitmap.add(s.hashCode());
    }

    @Override
    public boolean contains(String s) {
        logger.info("BloomFilterServiceImpl - Checking for containing key: {}", s);
        return bitmap.contains(s.hashCode()) && bitmap.contains(hashService.hash(s, 8));
    }

    public RoaringBitmap getBitmap() {
        return bitmap;
    }
}
