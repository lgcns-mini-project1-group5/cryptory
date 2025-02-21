import React from "react";
import "../../../styles/main-style.css"
import {useNavigate} from "react-router-dom";

export default function CoinCard({icon, name, symbol, price, change}) {
    const navigate = useNavigate();

    return (
        <div className="coin-card" onClick={() => {navigate(`/coin/${symbol}`, {state: {name:name, icon:icon, price:price, change:change}})}}>
            <img src={icon} alt={name} className="coin-card-icon" />
            <div className="coin-card-info">
                <p className="coin-card-name">{name}</p>
                <p className="coin-card-symbol">{symbol}</p>
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