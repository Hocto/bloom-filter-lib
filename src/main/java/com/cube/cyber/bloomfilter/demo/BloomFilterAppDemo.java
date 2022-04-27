package com.cube.cyber.bloomfilter.demo;

import com.cube.cyber.bloomfilter.service.BloomFilterServiceImpl;
import com.cube.cyber.bloomfilter.service.WordLoadServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BloomFilterAppDemo implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(BloomFilterAppDemo.class);

    @Value("${dictionary.file.path}")
    private String filePath;

    @Override
    public void run(String... args) throws Exception {

        WordLoadServiceImpl wordLoadService = new WordLoadServiceImpl();
        wordLoadService.load(filePath);
        List<String> words = wordLoadService.getWords();
        logger.info("Word List Size: "+ words.size());

        BloomFilterServiceImpl bloomFilterService = new BloomFilterServiceImpl();
        words.forEach(bloomFilterService::add);

        if(bloomFilterService.contains("Achaia")){
            logger.info("Maybe!!");
        }
        else{
            logger.info("Not present!!");
        }
    }
}
