package com.spotify.spotify;

import ch.qos.logback.core.BasicStatusManager;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpotifyService {
    public static HashMap<String, Object> playlistData = new HashMap<>();
    public static ArrayList<String> playlistIds = new ArrayList<String>();
    public static HashMap<String, List<String>> artistIds = new HashMap<>();
    public static JsonParser springParser = JsonParserFactory.getJsonParser();
    public static RestTemplate restTemplate = new RestTemplate();
    public static HttpHeaders headers = new HttpHeaders();
    public Map<Object, Object> getDataWithToken(String access){
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
        System.out.println("GETTING PLAYLISTS");
        headers.set("Authorization","Bearer " + access);
        ResponseEntity<String> response = restTemplate.exchange("https://api.spotify.com/v1/me/playlists",
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                String.class);
       // Map<Object, Object> ss = (Map<Object, Object>) response.getBody();
        Map < String, Object > map = springParser.parseMap(String.valueOf(response.getBody()));
        playlistData = (HashMap<String, Object>) map;
        getArtists(access);
        return map;
    }
    public void getArtists(String access){
        int i = 0;
        ArrayList<Object> playlists = new ArrayList<>();
        headers.set("Authorization","Bearer " + access);
        playlists = (ArrayList<Object>) playlistData.get("items");

        for (Object playlist : playlists){
            HashMap<String, String> ab = (HashMap <String, String>)playlist;
            String uri = ab.get("uri");
            uri=uri.substring(17);
            String getUrl = "https://api.spotify.com/v1/playlists/"+uri+"/tracks?limit=50";
            ResponseEntity<String> response = restTemplate.exchange(getUrl,
                    HttpMethod.GET,
                    new HttpEntity<>("parameters", headers),
                    String.class);
            Map < String, Object > map = springParser.parseMap(String.valueOf(response.getBody()));
            ArrayList<Object> tracks = (ArrayList<Object>) map.get("items");
            ArrayList<String> tempArtist = new ArrayList();
            for(Object item:tracks){
                ArrayList<Object> artists = (ArrayList<Object>)(((HashMap<String, Object>)((HashMap<String, Object>) item).get("track")).get("artists"));
                for(Object element: artists){
                    tempArtist.add(((HashMap<String, String>) element).get("name"));
                }
                artistIds.put(uri, tempArtist);
            }
            playlistIds.add(uri);
        }
    }

//    public Map<String, Object> getRecommendations(String access, String playlistId){
//    }
}
