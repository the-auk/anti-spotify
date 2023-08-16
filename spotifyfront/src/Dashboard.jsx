import styles from "./styles/Dashboard.module.css";
import { Image, useEffect, useState } from "react";
import axios from "axios";

const Dashboard = () => {
  const [datafromurl, setDatafromurl] = useState(null);
  const [playlistOpen, setPlaylistOpen] = useState(false);
  const [artistlistOpen, setArtistlistOpen] = useState(false);
  const dropdownsvg = (
    <img className={styles.dropdown} src="./dropdown.svg"></img>
  );
  const [playlistData, setPlaylistData] = useState();
  const [renderPlaylist, setRenderPlaylist] = useState();

  useEffect(() => {
    let tempdata = window.location.hash
      .substring(1)
      .split("&")
      .reduce((initial, item) => {
        let parts = item.split("=");
        initial[parts[0]] = decodeURIComponent(parts[1]);

        return initial;
      }, {});
    setDatafromurl(tempdata);
  }, []);

  useEffect(() => {
    if (datafromurl != null) {
      handleSendToken();
    }
  }, [datafromurl]);

  const handleSendToken = async () => {
    console.log(datafromurl);
    try {
      const response = await axios.post(
        "http://10.0.0.161:8080/api/access",
        datafromurl
      );
      setPlaylistData(response.data);
    } catch (error) {
      console.error(error);
    }
  };
  useEffect(()=>{
    if(playlistData!=null){
        let tempList=[]
        playlistData.items.forEach(playlist => {
            let temp=<div className={styles.item}>
                <div style={{backgroundImage:`url(${playlist.images[0].url})`}} className={styles.itemImage}>
                </div>
                <div className={styles.itemTextWrapper}>
                    <div className={styles.itemHeading}>{playlist.name}</div>
                    <div className={styles.itemTracks}>Tracks: {playlist.tracks["total"]}</div>
                    <a target="_blank" href={playlist.external_urls["spotify"]} className={styles.spotifyLink}>Open in Spotify</a>
                </div>
            </div>
            tempList.push(temp)
        });
        setRenderPlaylist(tempList)
    }
  }, [playlistData])
  return (
    <div className={styles.wrapper}>
      <div className={styles.heading}>Anti-Spotify</div>
      <div className={styles.section}>
        <div className={styles.playlistWrapper}>
          <div
            onClick={() =>
              setPlaylistOpen((prev) => {
                return !prev;
              })
            }
            className={styles.playlistHeader}
          >
            <div className={styles.playlistHeading}>Your Playlists</div>
            <div className={styles.arrow}>{playlistOpen?"see less":"see more"}<span style={{transition: "all ease 0.5s", transform:playlistOpen?"rotate(180deg)":""}}>{dropdownsvg}</span></div>
          </div>
          <div
            className={styles.playlistContent}
            style={{ height: playlistOpen ? "auto" : "200px" }}
          >
            {playlistData?renderPlaylist:"Listen to some playlists loser"}
          </div>
        </div>
        <div className={styles.playlistWrapper}>
          <div
            onClick={() =>
              setArtistlistOpen((prev) => {
                return !prev;
              })
            }
            className={styles.playlistHeader}
          >
            <div className={styles.playlistHeading}>Your Top Artists</div>
            <div className={styles.arrow}>{artistlistOpen?"see less":"see more"}<span style={{transition: "all ease 0.5s", transform:artistlistOpen?"rotate(180deg)":""}}>{dropdownsvg}</span></div>
          </div>
          <div
            className={styles.playlistContent}
            style={{ maxHeight: artistlistOpen ? "auto" : "200px" }}
          >
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
