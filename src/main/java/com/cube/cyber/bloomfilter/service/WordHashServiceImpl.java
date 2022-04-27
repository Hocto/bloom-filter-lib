package com.cube.cyber.bloomfilter.service;

import org.springframework.stereotype.Service;

@Service
public class WordHashServiceImpl implements HashService{

    @Override
    public int hash(String s, int complexity) {
        int t = complexity;
        int length = s.length();
        int hash = 0;

        if(s.length()<=complexity){
            t = s.length()-1;
        }
        for(; t>=0 ; t--){
            hash += s.charAt(t) * (length<0 ? t : t*(--length));
        }
        if(s.length()>complexity){
            for(int u=complexity; u<s.length(); u++){
                hash += s.charAt(u);
            }
        }
        return hash;
    }
}
