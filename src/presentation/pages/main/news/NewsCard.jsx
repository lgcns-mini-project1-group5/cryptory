import React from "react";
import "../../../styles/main-style.css";

export default function NewsCard({ title, description, date, url }) {

    function pageSwitch() {
        window.open(url)
    }

    return (
        <div className="news-card" onClick={() => {pageSwitch()}}>
            <h2 className="news-title">{title}</h2>
            <p className="news-description">{description}</p>
            <p className="news-date">{date}</p>
        </div>
    );
}


