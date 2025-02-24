import {useState} from "react";
import "../../styles/main-style.css"
import CoinView from "./coin/CoinView.jsx";
import NewsView from "./news/NewsView.jsx";

export default function MainView() {

    const [type, setType] = useState("coin");

    return (<div className="content">
        {(type === 'coin') && <>
            <div className="main-nav">
                <button className="main-btn-on">Coin</button>
                <button className="main-btn" onClick={() => {setType("news")}}>News</button>
            </div>
            <CoinView/>
        </>}

        {(type === 'news') && <>
            <div className="main-nav">
                <button className="main-btn" onClick={() => {setType("coin")}}>Coin</button>
                <button className="main-btn-on">News</button>
            </div>
            <NewsView type={"main"}/>
        </>}
    </div>)
}