package com.epam.victor.exchanger.repository.json;

import com.epam.victor.exchanger.repository.exception.JsonFileNotCreatedException;
import com.epam.victor.exchanger.repository.exception.JsonFileReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonFileUtil {
    public static <T> List<T> findAllFromFolder(String path, ObjectMapper objectMapper, TypeReference<T> typeReference) {
        List<T> objectList;
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            objectList = paths
                    .filter(Files::isRegularFile)
                    .map(p -> {
                        try {
                            return objectMapper.readValue(p.toFile(), typeReference);
                        } catch (IOException e) {
                            throw new JsonFileReadException("Unable to find the file " + p.getFileName().toString());
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new JsonFileReadException("List of" + typeReference.getType().toString() +  " read exception", e);
        }
        return objectList;

    }

    public static <T> void writeObjectToFile(T object,
                                             String path,
                                             ObjectMapper objectMapper,
                                             TypeReference<T> typeReference){
        try {
            objectMapper.writerFor(typeReference).writeValue(new File(path), object);
        } catch (IOException e) {
            throw new JsonFileNotCreatedException("File " + path + " wasn't created", e);
        }
    }

    public static <T> Optional<T> fileByPathToObject (String path ,ObjectMapper objectMapper, TypeReference<T> typeReference){
        T object;
        try {
            object = objectMapper.readValue(new File(path), typeReference);
        } catch (FileNotFoundException e){
            object = null;
        } catch (IOException e) {
            throw new JsonFileNotCreatedException("Unable to read file " + path, e);
        }
        return Optional.ofNullable(object);
    }

    public static boolean isPathExist(String path){
        return Files.exists(Paths.get(path));
    }

    public static void removeFile(String path){
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            throw new JsonFileReadException("Unable to delete file " + path, e);
        }
    }
}
