package com.sam_chordas.android.stockhawk;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sam_chordas.android.stockhawk.model.Results;

import java.lang.reflect.Type;

/**
 * Created by divyamary on 10-03-2016.
 */
public class ResultsDeserializer implements JsonDeserializer<Results> {


    @Override
    public Results deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return new Results();
    }
}
