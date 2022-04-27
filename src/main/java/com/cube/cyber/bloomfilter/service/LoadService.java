package com.cube.cyber.bloomfilter.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface LoadService {
    void load(String fileName) throws IOException;
}
