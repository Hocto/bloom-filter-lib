package com.cube.cyber.bloomfilter.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("BloomFilterService")
public interface BloomFilterService {
    void add(String s);
    boolean contains(String s);
}
