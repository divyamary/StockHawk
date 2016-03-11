package com.sam_chordas.android.stockhawk;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sam_chordas.android.stockhawk.model.StockResponse;

import java.lang.reflect.Type;

/**
 * Created by divyamary on 10-03-2016.
 */
public class StockDeserializer implements JsonDeserializer<StockResponse> {


    @Override
    public StockResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement content = json.getAsJsonObject().get("meta");

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, StockResponse.class);

    }
}
