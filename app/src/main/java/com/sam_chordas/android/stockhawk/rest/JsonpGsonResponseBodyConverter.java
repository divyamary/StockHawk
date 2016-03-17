package com.sam_chordas.android.stockhawk.rest;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * From http://stackoverflow.com/questions/34421851/retrofit-how-to-parse-this-response
 */
public class JsonpGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    JsonpGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    private static String readerToString(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int charsRead = -1;
        char[] chars = new char[100];
        do {
            charsRead = reader.read(chars, 0, chars.length);
            //if we have valid chars, append them to end of string.
            if (charsRead > 0)
                builder.append(chars, 0, charsRead);
        } while (charsRead > 0);
        return builder.toString();
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        Reader reader = value.charStream();
        try {
            String jsonp = readerToString(reader);
            String json = JsonpParser.jsonpToJson(jsonp);
            return gson.fromJson(json, type);
        } finally {
            if (reader == null) return null;
            try {
                reader.close();
            } catch (IOException ignored) {
            }
        }
    }
}