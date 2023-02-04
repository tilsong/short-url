package com.toy.shorturl.Module;

import java.util.List;

public class Base62Converter {
    private static final List<String> base62 = List.of("0","1","2","3","4","5","6","7","8","9",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");


    public static int base62ToInt(String encodedUrl) {
        int decoded = 0;

        for (int i = 0; i < encodedUrl.length() ; i++) {
            String decodedString = Character.toString(encodedUrl.charAt(i));
            int base62Index = base62.indexOf(decodedString);

            int squareNum = (int) Math.pow(62, encodedUrl.length()-i-1);

            decoded += base62Index * squareNum;
        }

        return decoded;
    }

    public static String intTobase62(int id) {
        StringBuilder encoded = new StringBuilder();

        while ((id/62) > 0 || encoded.toString().equals("")) {
            encoded.insert(0, base62.get(id % 62));
            id /= 62;
        }

        if (id > 0) {
            encoded.insert(0, base62.get(id));
        }

        return encoded.toString();
    }
}
