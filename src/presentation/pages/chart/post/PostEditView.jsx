import React, {useEffect, useState} from "react";
import "../../../styles/post-style.css"
import {useLocation, useNavigate, useParams} from "react-router-dom";

export default function PostEditView() {

    const location = useLocation()
    const { name, symbol, icon, price, change, author, date, _title, _content, postId } = location.state || {}

    const navigate = useNavigate();

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        alert(`제목: ${title}\n내용: ${content}`);
    };

    useEffect(() => {
        setTitle(_title)
        setContent(_content)
    }, [])

    return (
        <div className="write-container">

            <div className="nav">
                <button className="btn-on" onClick={() => {
                    navigate("/")
                }}>Coin
                </button>
                <button className="btn" onClick={() => {
                    navigate("/")
                }}>News
                </button>
            </div>

            <div className="user-info-section">
                <img src={icon} alt="User" className="user-icon"/>
                <div>
                    <p>{name}</p>
                    <p style={{color: "gray", marginTop: -15}}>{symbol}</p>
                </div>
            </div>

            <form onSubmit={handleSubmit} className="write-form">
                <label className="form-label">TITLE</label>
                <input
                    type="text"
                    className="title-input"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                />

                <label className="form-label">CONTENT</label>
                <textarea
                    className="content-input"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                ></textarea>

                <div className="button-group">
                    <button type="button" className="cancel-btn" onClick={() => {
                        navigate(`/post/${postId}`, {state: {name:name, symbol:symbol, icon:icon, price:price, change:change, author:author, date:date}})
                    }}>취소
                    </button>
                    <button type="submit" className="submit-btn">수정</button>
                </div>
            </form>
        </div>
    );
}


