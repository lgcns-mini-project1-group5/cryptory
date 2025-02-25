import React from "react";
import "../../../styles/prompt-style.css"

export default function ResponseCell({ content }) {
    return (<div className="prompt-content">
            <p className="prompt-content">{content}</p>
        </div>);
}