import {useState} from "react";
import "../../styles/main-style.css"
import CoinView from "./coin/CoinView.jsx";
import NewsView from "./news/NewsView.jsx";

export default function MainView() {

    const [type, setType] = useState("coin");

    return (<div className="content">
        {(type === 'coin') && <>
            <div className="nav">
                <button className="btn-on">Coin</button>
                <button className="btn" onClick={() => {setType("news")}}>News</button>
            </div>
            <CoinView/>
        </>}

        {(type === 'news') && <>
            <div className="nav">
                <button className="btn" onClick={() => {setType("coin")}}>Coin</button>
                <button className="btn-on">News</button>
            </div>
            <NewsView type={"main"}/>
        </>}
    </div>)
}