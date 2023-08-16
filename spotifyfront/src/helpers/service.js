const client_id="3054cb711c3c4fc48cff3458cdaddea2"
const redirectUri="http://localhost:3000/dashboard"
const authUri="https://accounts.spotify.com/authorize"
const scopes=[`user-read-email, user-read-private, user-follow-read, user-read-playback-state, user-read-currently-playing, 
playlist-read-private, playlist-read-collaborative, user-read-playback-position, user-top-read, user-read-recently-played, 
user-library-read`];
export const loginUrl = `${authUri}?client_id=${client_id}&redirect_uri=${redirectUri}&scope=${scopes.join("%20")}&response_type=token&show_dialog=true`
    