package com.spotify.spotify;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class SpotifyService {
    public Object getDataWithToken(String access){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + access);
        ResponseEntity<Object> response = restTemplate.exchange("https://api.spotify.com/v1/recommendations/available-genre-seeds",
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                Object.class);
        Map<Object, Object> ss = (Map<Object, Object>) response.getBody();
        List<String> a = (List<String>) ss.get("genres");
        System.out.println(a.size());
        return ss;
    }
    public Map< String, Object > getUserPlaylists(String access){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + access);
        ResponseEntity<String> response = restTemplate.exchange("https://api.spotify.com/v1/me/playlists",
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                String.class);
       // Map<Object, Object> ss = (Map<Object, Object>) response.getBody();
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map < String, Object > map = springParser.parseMap(String.valueOf(response.getBody()));
        String mapArray[] = new String[map.size()];
        System.out.println("Items found: " + mapArray.length);
        int i = 0;
        for (Map.Entry < String, Object > entry: map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
            i++;
        }
        //System.out.println(ss);
        return map;
    }
//    public Map< String, Object > getUserArtists(String access){
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization","Bearer " + access);
//        ResponseEntity<String> response = restTemplate.exchange("https://api.spotify.com/v1/me/following",
//                HttpMethod.GET,
//                new HttpEntity<>("parameters", headers),
//                String.class);
//        // Map<Object, Object> ss = (Map<Object, Object>) response.getBody();
//        JsonParser springParser = JsonParserFactory.getJsonParser();
//        Map < String, Object > map = springParser.parseMap(String.valueOf(response.getBody()));
//        String mapArray[] = new String[map.size()];
//        System.out.println("Items found: " + mapArray.length);
//        int i = 0;
//        for (Map.Entry < String, Object > entry: map.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue());
//            i++;
//        }
//        //System.out.println(ss);
//        return map;
//    }
}
