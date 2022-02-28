package com.jovan.activityplanner.model.filemanager;

import com.google.gson.*;
import com.jovan.activityplanner.model.RootActivity;
import com.jovan.activityplanner.util.LoggerSingleton;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LocalFileSystem implements FileSystemInterface<RootActivity> {

    private Gson getGsonSerializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        JsonSerializer<RootActivity> serializer = new JsonSerializer<RootActivity>() {
            @Override
            public JsonElement serialize(RootActivity src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject jsonRootActivity = new JsonObject();

                jsonRootActivity.addProperty("id", src.getId());
                jsonRootActivity.addProperty("startTime", src.getStartTime().toString());
                jsonRootActivity.addProperty("endTime", src.getEndTime().toString());
                jsonRootActivity.addProperty("title", src.getTitle());
                jsonRootActivity.addProperty("description", src.getDescription());

                return jsonRootActivity;
            }
        };

        builder.registerTypeAdapter(RootActivity.class, serializer);
        return builder.create();
    }

    private Gson getGsonDeserializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        ArrayList<RootActivity> rootActivityArrayList = new ArrayList<>();

        JsonDeserializer<ArrayList<RootActivity>> deserializer = new JsonDeserializer<ArrayList<RootActivity>>() {
            @Override
            public ArrayList<RootActivity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                ArrayList<RootActivity> list = new ArrayList<>();
                JsonArray array = json.getAsJsonArray();
                for(JsonElement element : array) {
                    JsonObject object = element.getAsJsonObject();
                    LocalDateTime startTime = LocalDateTime.parse(object.get("startTime").getAsString());
                    LocalDateTime endTime = LocalDateTime.parse(object.get("endTime").getAsString());

                    RootActivity loadedActivity = new RootActivity(object.get("id").getAsString(), startTime, endTime, object.get("title").getAsString(), object.get("description").getAsString());
                    list.add(loadedActivity);
                }

                return list;
            }
        };
        gsonBuilder.registerTypeAdapter(rootActivityArrayList.getClass(), deserializer);

        return gsonBuilder.create();
    }

    @Override
    public ArrayList<RootActivity> read(String path) {
        try {
            Reader reader = Files.newBufferedReader(Path.of(path));
            Gson gson = getGsonDeserializer();
            ArrayList<RootActivity> result = gson.fromJson(reader, ArrayList.class);
            reader.close();

            return result;
        } catch (IOException e) {
            LoggerSingleton.getInstance().severe("Error loading file contents: " + e);
        }

        return new ArrayList<>();
    }

    @Override
    public void write(String path, List<RootActivity> contents) {
        Gson serializer = getGsonSerializer();
        try {
            FileWriter writer = new FileWriter(path);
            serializer.toJson(contents, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LoggerSingleton.getInstance().severe("Error saving contents to file: " + e);
        }
    }
}
