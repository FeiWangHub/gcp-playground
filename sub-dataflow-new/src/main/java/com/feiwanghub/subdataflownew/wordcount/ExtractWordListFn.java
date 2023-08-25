package com.feiwanghub.subdataflownew.wordcount;

import org.apache.beam.sdk.transforms.SimpleFunction;

import java.util.Arrays;
import java.util.List;

public class ExtractWordListFn extends SimpleFunction<String, List<String>> {

    /**
     * [^ ] means "any character that is not a space"
     * \p{L} means "any character that is a letter"
     * + means "one or more of the preceding"
     * So the pattern as a whole means "one or more characters that are not spaces,
     * followed by one or more characters that are letters".
     */
    public static final String TOKENIZER_PATTERN = "[^\\p{L}]+";

    @Override
    public List<String> apply(String line) {
        return Arrays.asList(line.trim().split(TOKENIZER_PATTERN));
    }

}
