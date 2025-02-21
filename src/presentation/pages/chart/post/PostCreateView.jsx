import React, { useState } from "react";
import "../../../styles/post-style.css"
import {useLocation, useNavigate, useParams} from "react-router-dom";

export default function PostCreateView() {

    const location = useLocation()
    const { name, symbol, icon, price, change } = location.state || {}

    const navigate = useNavigate();

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        alert(`제목: ${title}\n내용: ${content}`);
    };

    return (
        <div className="write-container">

            <div className="post-nav">
                <button className="post-btn-on" onClick={() => {
                    navigate("/")
                }}>Coin
                </button>
                <button className="post-btn" onClick={() => {
                    navigate("/")
                }}>News
                </button>
            </div>

            <div className="user-info-section">
                <img src={icon} alt="User" className="user-icon"/>
                <div>
                    <p className="user-name">{name}</p>
                    <p className="user-symbol">{symbol}</p>
                </div>
            </div>

            <form onSubmit={handleSubmit} className="write-form">
                <div style={{display: "flex"}}>
                    <label className="form-label">TITLE</label>
                    <input
                        type="text"
                        className="title-input"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                    />
                </div>

                <label className="form-label">CONTENT</label>
                <textarea
                    className="content-input"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                ></textarea>

                <div className="button-group">
                    <button type="button" className="cancel-btn" onClick={() => {
                        navigate(`/coin/${symbol}`, {
                            state: {
                                name: name,
                                icon: icon,
                                price: price,
                                change: change,
                                prior: "board"
                            }
                        })
                    }}>취소
                    </button>
                    <button type="submit" className="submit-btn">등록</button>
                </div>
            </form>
        </div>
    );
}


