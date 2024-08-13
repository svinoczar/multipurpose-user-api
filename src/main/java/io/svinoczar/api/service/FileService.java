package io.svinoczar.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    public String readFileFromResources(String filename) {
        Resource resource = new ClassPathResource(filename);
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error while reading a file: " + filename, e);
        }
    }

    public List<String[]> parseCSVtoList(String csv) {
        List<String[]> list = new ArrayList<>();
        Arrays.stream(csv.strip().split("\n")).toList().forEach(
                sublist -> {
                    list.add(sublist.split(","));
                }
        );
        list.remove(0);
        return list;
    }

    public Map<String, Object> parseCSVtoMap(String csv) {
        Map<String, Object> map = new HashMap<>();
        Arrays.stream(csv.strip().split("\n")).toList().forEach(
                element -> {
                    String[] string = element.split(",");
                    map.put(string[0], string[1]);
                }
        );
        map.remove("Key", "Value");
        return map;
    }
}