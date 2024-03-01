package com.epam.victor;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Map;

public class CustomMapSerializer implements JsonSerializer<Map> {

    @Override
    public JsonElement serialize(Map map, Type type, JsonSerializationContext jsonSerializationContext) {
        if (map.size() == 0)
            return null;
        return jsonSerializationContext.serialize(map);
    }


}
