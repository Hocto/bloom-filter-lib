package com.cube.cyber.bloomfilter.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordLoadServiceImpl implements LoadService {

    private List<String> words = new ArrayList<>();

    @Override
    public void load(String fileName) throws IOException {
        InputStream resourceInputStream = new FileInputStream(fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceInputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        }
    }

    public List<String> getWords(){
        return words;
    }
}
