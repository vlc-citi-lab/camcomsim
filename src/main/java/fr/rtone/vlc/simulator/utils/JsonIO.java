/**
 * Copyright 2017 Alexis DUQUE.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.rtone.vlc.simulator.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonIO {

    public static String toJsonString(Object o, boolean pretty, boolean onlyExpose) {
        final GsonBuilder builder = new GsonBuilder();
        if (onlyExpose) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }
        if (pretty) {
            builder.setPrettyPrinting();
        }
        final Gson gson = builder.create();
        String paramsString = gson.toJson(o);
        return paramsString;
    }

    public static boolean toJsonFile(Object o, String filename, String path, boolean onlyExpose) {
        LocalDateTime date = LocalDateTime.now();
        String humanDate = date.format(DateTimeFormatter.ofPattern("ddMMyy-HHmmssSSS"));
        String fixedPath = path;
        if (path == null || path.isEmpty()) {
            fixedPath = "./";
        }
        fixedPath = fixedPath + "/" + filename + "_" + humanDate + ".json";
        String content = toJsonString(o, false, onlyExpose);
        try {
            Files.write(Paths.get(fixedPath), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
