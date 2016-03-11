package com.sam_chordas.android.stockhawk;

import com.google.gson.Gson;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * From http://stackoverflow.com/questions/34421851/retrofit-how-to-parse-this-response
 */
public final class JsonpGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    private JsonpGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static JsonpGsonConverterFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static JsonpGsonConverterFactory create(Gson gson) {
        return new JsonpGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new JsonpGsonResponseBodyConverter<>(gson, type);
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new GsonRequestBodyConverter<>(gson, type);
    }
}
