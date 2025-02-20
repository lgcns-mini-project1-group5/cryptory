import React from "react";
import "../../../styles/main-style.css"
import {useNavigate} from "react-router-dom";

export default function CoinCard({icon, name, symbol, price, change}) {
    const navigate = useNavigate();

    return (
        <div className="coin-card" onClick={() => {navigate(`/coin/${symbol}`, {state: {name:name, icon:icon, price:price, change:change}})}}>
            <img src={icon} alt={name} className="coin-icon" />
            <div className="coin-info">
                <p className="coin-name">{name}</p>
                <p className="coin-symbol">{symbol}</p>
            </div>
            <div className="coin-price">
                <p className={`price ${change >= 0 ? "positive" : "negative"}`}>
                    {price.toLocaleString()}원
                </p>
                <p className={`change ${change >= 0 ? "positive" : "negative"}`}>
                    {change.toFixed(2)}%
                </p>
            </div>
        </div>
    );
}