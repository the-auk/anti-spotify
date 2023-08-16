package com.spotify.spotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(path = "api")
public class SpotifyController {
    private final SpotifyService spotifyService;

    @Autowired
    public SpotifyController(SpotifyService spotifyService){
        this.spotifyService = spotifyService;
    }
    @GetMapping("/")
    public String hello(){
        return "Server Working";
    }
    @PostMapping("/access")
    public Map< String, Object > getAuthToken(@RequestBody Map<Object, Object> token){
        String access= (String) token.get("access_token");
       // return spotifyService.getDataWithToken(access);
        return spotifyService.getUserPlaylists(access);
    }

    @GetMapping("/dashboard")
    public Object getDashboardData(){
        return new Object();
    }
}
