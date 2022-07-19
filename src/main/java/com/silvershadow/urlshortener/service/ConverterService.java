package com.silvershadow.urlshortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConverterService {

    private static final int HEX_RADIX = 16;

    @Value("${converter.max-hex-length}")
    private int maxHexLength;

    private final List<String> elements = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
                "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4",
                "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                "Y", "Z");

    private final Map<String, String> storageMap = new HashMap<>();

    /**
     * Take a string and find a shortened version accoring to built-in alphabet
     * @param value value to convert
     * @return shortened value
     */
    public String shortenString(String value)  {
        if(null == value || value.isEmpty())
            return "";

        String hexString = DigestUtils.md5DigestAsHex(value.getBytes(Charset.defaultCharset()));
        String hexKey = hexString.substring(0, maxHexLength);

        storageMap.putIfAbsent(hexKey, value);

        long newBase = elements.size();

        return convert(convertHexString(hexKey), newBase);
    }

    /**
     * Retrieve original string based on it's shortened value
     * @param shortValue short value
     * @return original string
     */
    public String getOriginalString(String shortValue) {
        int base = elements.size();
        int len = shortValue.length();
        int i = 0;
        long value = 0;

        for (char c : shortValue.toCharArray()) {
            value += elements.indexOf(String.valueOf(c)) * Math.pow(base, len - i - 1);
            i++;
        }

        String hexKey = Long.toHexString(value);

        return storageMap.getOrDefault(hexKey, "");
    }

    private long convertHexString(String hexString) {
        return Long.valueOf(hexString, HEX_RADIX);
    }

    private String convert(long number, long base) {
        if (0 == number)
            return "0";

        StringBuilder sb = new StringBuilder();
        long remainder;

        while (number > 0) {
            remainder = number % base;
            sb.append(elements.get((int)remainder));
            number = number / base;
        }

        return sb.reverse().toString();
    }
}
