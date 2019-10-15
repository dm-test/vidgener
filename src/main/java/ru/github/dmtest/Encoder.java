package ru.github.dmtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class Encoder {
    private static final Logger LOG = LoggerFactory.getLogger(Encoder.class);
    private static final List<Character> ABC = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    private String inputText;
    private String encodeKey;

    public Encoder(String inputText, String encodeKey) {
        // непустые
        this.inputText = inputText;
        this.encodeKey = encodeKey;
    }

    public static void main(String[] args) {
        LOG.info("Введите текст, который необходимо закодировать:");
        String inputText = getTextFromKeyboard();
        LOG.info("Введите ключевое слово:");
        String encodeKey = getTextFromKeyboard();
        Encoder encoder = new Encoder(inputText, encodeKey);
        String encodeText = encoder.encode();
        LOG.info("Закодированная строка: {}", encodeText);
    }

    private static String getTextFromKeyboard() {
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String text = br.readLine().toUpperCase();
            LOG.info("Введена строка: {}", text);
            return text;
        } catch (IOException e) {
            LOG.info("Не удалось считать введенную строку!");
            throw new IOError(e);
        }
    }

    private String encode() {
        char[] inputChars = inputText.toCharArray();
        char[] keyChars = encodeKey.toCharArray();
        StringBuilder sb = new StringBuilder();
        int keyIndex = 0;
        for (char inputSymbol : inputChars) {
            if (keyIndex == keyChars.length) {
                keyIndex = 0;
            }
            char keySymbol = keyChars[keyIndex];
            char encodeSymbol = getEncodeSymbol(keySymbol, inputSymbol);
            sb.append(encodeSymbol);
            keyIndex++;
        }
        return sb.toString();
    }

    private char getEncodeSymbol(char keySymbol, char inputSymbol) {
        int shift = ABC.indexOf(inputSymbol);
        int keySymbolIndex = ABC.indexOf(keySymbol);
        int sum = keySymbolIndex + shift;
        int encodeSymbolIndex;
        if (sum < ABC.size()) {
            encodeSymbolIndex = sum;
        } else {
            encodeSymbolIndex = sum % ABC.size();
        }
        return ABC.get(encodeSymbolIndex);
    }

}