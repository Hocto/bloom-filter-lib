package com.cube.cyber.bloomfilter.service;

import org.springframework.stereotype.Service;

@Service
public interface HashService {
    int hash(String s, int complexity);
}
