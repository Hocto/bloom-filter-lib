package com.cube.cyber.bloomfilter.test.service;

import com.cube.cyber.bloomfilter.service.BloomFilterServiceImpl;
import com.cube.cyber.bloomfilter.service.WordLoadServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BloomFilterServiceTest.class})
@ComponentScan(basePackages = {"com.cube.cyber.bloomfilter.*"})
@ActiveProfiles("test")
public class BloomFilterServiceTest {

    @Autowired
    private WordLoadServiceImpl wordLoadService;

    @Autowired
    private BloomFilterServiceImpl bloomFilterService;

    @Before
    public void setup() throws IOException {
        wordLoadService.load(this.getClass().getClassLoader().getResource("words_test.txt").getPath());
        List<String> words = wordLoadService.getWords();
        words.forEach(bloomFilterService::add);
    }

    @Test
    public void test_assertTrueIfExistsInDictionary() {
        assertThat(bloomFilterService.contains("words")).isTrue();
        assertThat(bloomFilterService.contains("cybercube")).isTrue();
        assertThat(bloomFilterService.contains("orkun")).isTrue();
    }

    @Test
    public void test_assertFalseIfNotExistsInDictionary() {
        assertThat(bloomFilterService.contains("notInDict")).isFalse();
        assertThat(bloomFilterService.contains("notExist")).isFalse();
        assertThat(bloomFilterService.contains("123")).isFalse();
    }
}
