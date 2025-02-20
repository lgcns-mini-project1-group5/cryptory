import React, {useEffect, useState} from "react";
import CoinCard from "./CoinCard.jsx";
import axios from "axios";

export default function CoinView() {

    const [coinData, setCoinData] = useState([]);

    useEffect(() => {
        // axios
        //     .get(`http://${rest_api_host}:${rest_api_port}/api/v2/board/${boardIdx}`, {headers: {"Authorization": `Bearer ${token}`}})
        //     .then(res => {
        //
        //     })
        //     .catch(err => {
        //
        //     });
        setCoinData([
            { icon: "https://cryptologos.cc/logos/bitcoin-btc-logo.png", name: "비트코인", symbol: "BTC", price: 144204615, change: 9.77},
            { icon: "https://cryptologos.cc/logos/ethereum-eth-logo.png", name: "이더리움", symbol: "ETH", price: 6057077, change: -21.0},
            { icon: "https://cryptologos.cc/logos/tether-usdt-logo.png", name: "테더", symbol: "USDT", price: 1508, change: 0.1},
            { icon: "https://cryptologos.cc/logos/ripple-xrp-logo.png", name: "리플", symbol: "XRP", price: 3413, change: -7.76},
            { icon: "https://cryptologos.cc/logos/dogecoin-doge-logo.png", name: "도지코인", symbol: "DOGE", price: 378, change: 2.1},
            { icon: "https://cryptologos.cc/logos/binance-coin-bnb-logo.png", name: "바이낸스", symbol: "BNB", price: 35781, change: 1.5},
            { icon: "https://cryptologos.cc/logos/usd-coin-usdc-logo.png", name: "USDC", symbol: "USDC", price: 3778, change: 0.2},
        ]);
    }, []);

    return (<div className="coin-list">
        {coinData.map((coin, index) => (
            <CoinCard key={index} {...coin} />
        ))}
    </div>)
}