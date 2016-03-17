package com.sam_chordas.android.stockhawk.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jamesanto on 12/22/15.
 */
public class JsonpParser {

    private static final Pattern JSONP = Pattern.compile("(?s)\\w+\\((.*)\\).*");

    public static String jsonpToJson(String jsonStr) {
        Matcher matcher = JSONP.matcher(jsonStr);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Unknown jsonp format");
        }
    }

    public static void main(String[] args) {
        String sampleJson = "finance_charts_json_callback({\n" +
                "    \"base\": \"cmc stations\",\n" +
                "    \"clouds\": {\n" +
                "        \"all\": 75\n" +
                "    },\n" +
                "    \"cod\": 200,\n" +
                "    \"coord\": {\n" +
                "        \"lat\": 51.51,\n" +
                "        \"lon\": -0.13\n" +
                "    },\n" +
                "    \"dt\": 1450807548,\n" +
                "    \"id\": 2643743,\n" +
                "    \"main\": {\n" +
                "        \"humidity\": 82,\n" +
                "        \"pressure\": 1011,\n" +
                "        \"temp\": 286.94,\n" +
                "        \"temp_max\": 287.59,\n" +
                "        \"temp_min\": 286.15\n" +
                "    },\n" +
                "    \"name\": \"London\",\n" +
                "    \"sys\": {\n" +
                "        \"country\": \"GB\",\n" +
                "        \"id\": 5091,\n" +
                "        \"message\": 0.0136,\n" +
                "        \"sunrise\": 1450771468,\n" +
                "        \"sunset\": 1450799652,\n" +
                "        \"type\": 1\n" +
                "    },\n" +
                "    \"weather\": [\n" +
                "        {\n" +
                "            \"description\": \"light rain\",\n" +
                "            \"icon\": \"10n\",\n" +
                "            \"id\": 500,\n" +
                "            \"main\": \"Rain\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"description\": \"light intensity drizzle rain\",\n" +
                "            \"icon\": \"09n\",\n" +
                "            \"id\": 310,\n" +
                "            \"main\": \"Drizzle\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"wind\": {\n" +
                "        \"deg\": 210,\n" +
                "        \"gust\": 14.9,\n" +
                "        \"speed\": 9.8\n" +
                "    }\n" +
                "});";


        String json = jsonpToJson(sampleJson);
        System.out.println(json);
    }
}
