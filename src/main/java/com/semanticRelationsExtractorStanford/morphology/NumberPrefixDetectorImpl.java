package com.semanticRelationsExtractorStanford.morphology;

import com.semanticRelationsExtractorStanford.cache.PrefixesCache;

/**
 * Created by Oliver on 7/18/2017.
 */
public class NumberPrefixDetectorImpl implements NumberPrefixDetector {

    public boolean detect(String token) {
        for (Integer constantPrefixLength : PrefixesCache.prefixesLengthsCacheSet) {
            if (token.length() >= constantPrefixLength && modelContainsPrefix(token, constantPrefixLength)) {
                return true;
            }
        }
        return false;
    }

    private boolean modelContainsPrefix(String token, int endIndex) {
        return PrefixesCache.prefixesLengthsCacheSet.contains(token.substring(0, endIndex));
    }

}