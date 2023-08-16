import styles from "./styles/Home.module.css";
import { loginUrl } from './helpers/service';
import {Routes, Route} from "react-router-dom";

function Home() {
  return (
    <div className={styles.wrapper}>
            <div className={styles.heading}>
                Anti-Spotify
            </div>
            <a href={loginUrl} className={styles.button}>
                Log in with Spotify
            </a>
        </div>
  );
}

export default Home;
