import React, {useEffect, useState} from "react";
import "../../../styles/prompt-style.css"
import CommentCell from "../post/CommentCell.jsx";

export default function ModalView({ onClose, issueId, icon, name, symbol, price, change, issueDate }) {

    const [type, setType] = useState("ChatGPT")
    const [prompt, setPrompt] = useState("")

    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")
    const [news1, setNews1] = useState("")
    const [news1Link, setNews1Link] = useState("")
    const [news2, setNews2] = useState("")
    const [news2Link, setNews2Link] = useState("")

    const [commentList, setCommentList] = useState([])

    const handleSubmit = (e) => {
        e.preventDefault();
        if (prompt.trim()) {
            alert(`새 댓글: ${prompt}`);
            setPrompt(""); // 입력 후 초기화
        }
    };

    useEffect(() => {
        // axios
        //     .get(`http://${rest_api_host}:${rest_api_port}/api/v2/board/${boardIdx}`, {headers: {"Authorization": `Bearer ${token}`}})
        //     .then(res => {
        //
        //     })
        //     .catch(err => {
        //
        //     });
        setTitle("트럼프 대통령 당선, 비트코인 가격 상승 촉발")
        setContent("도널드 트럼프 전 미국 대통령의 당선이 공식 인증된 1월 6일, 비트코인 " +
            "가격은 10만 달러 선을 재돌파하며 큰 폭의 상승을 보였습니다. 트럼프 " +
            "당선인은 선거 기간 동안 미국을 '암호화폐의 수도'로 만들겠다는 공약을 " +
            "내세웠으며, 이러한 친암호화폐 정책 기대감이 투자자들의 심리를 " +
            "자극했습니다. 그러나 취임 이후 구체적인 정책 부재와 경제 불확실성으로 인해 비트코인 가격은 하락세로 전환되어, 2월 18일 기준 9만5,507달러로 최고점 대비 14% 하락하였습니다.")
        setNews1("'트럼프 당선' 공식 인증에 비트코인 상승…10만달러선 탈환")
        setNews1Link("https://www.naver.com")
        setNews2("이제 '트럼프 트레이드'는 금?…달러·비트코인은 주춤")
        setNews2Link("https://www.naver.com")
        setCommentList([
            { content: "비트코인 게시판 내용 1", author: "User1", date: "2025.02.18"},
            { content: "비트코인 게시판 내용 2", author: "User2", date: "2025.02.19"},
            { content: "내가 쓴 비트코인 게시판 내용 1", author: "my", date: "2025.02.19"},
        ])
    }, []);

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-btn" onClick={onClose}/>
                <div style={{display: "flex"}}>
                    <div className="user-info-section">
                        <img src={icon} alt="User" className="user-icon"/>
                        <div>
                            <p>{name}</p>
                            <p style={{color: "gray", marginTop: -15}}>{symbol}</p>
                        </div>
                    </div>
                    <div style={{marginLeft: "60px"}}>
                        <p>종가</p>
                        <p style={{marginTop: -15}}>전일 대비</p>
                    </div>
                    <div style={{width: '110px', textAlign: 'right'}}>
                        <p>
                            {price.toLocaleString()}원
                        </p>
                        <p style={{marginTop: -15}} className={`change ${change >= 0 ? "positive" : "negative"}`}>
                            {change > 0 && <>+</>}
                            {change.toFixed(2)}%
                        </p>
                    </div>
                </div>
                <p style={{marginTop: -20}}>{issueDate}</p>

                {(type === "ChatGPT") && <>
                    <div className="chat-section">
                        <button className="chat-btn-on">ChatGPT</button>
                        <button className="chat-btn" onClick={() => {
                            setType("debate")
                        }}>토론방
                        </button>
                    </div>
                    <div className="news-section">
                        <h2 className="news-title">{title}</h2>
                        <p className="news-content">{content}</p>
                    </div>
                    <div className="chat-messages">
                        <button className="chat-message" onClick={() => {
                            window.open(news1Link)
                        }}>{news1}</button>
                        <button className="chat-message" onClick={() => {
                            window.open(news2Link)
                        }}>{news2}</button>
                    </div>
                    <form className="comment-form" onSubmit={handleSubmit}>
                        <textarea
                            className="comment-input"
                            placeholder="궁금한 내용을 입력하세요!"
                            value={prompt}
                            onChange={(e) => setPrompt(e.target.value)}/>
                        <button type="submit" className="gpt-send-btn"/>
                    </form>
                </>
                }

                {(type === "debate") && <>
                    <div className="chat-section">
                        <button className="chat-btn" onClick={() => {
                            setType("ChatGPT")
                        }}>ChatGPT
                        </button>
                        <button className="chat-btn-on">토론방</button>
                    </div>
                    <div className="comment-list">
                        {commentList.map((comment, index) =>
                            <CommentCell key={index} {...comment} />
                        )}
                    </div>
                    <form className="comment-form" onSubmit={handleSubmit}>
                        <textarea
                            className="comment-input"
                            placeholder="새로운 글을 입력하세요"
                            value={prompt}
                            onChange={(e) => setPrompt(e.target.value)}/>
                        <button type="submit" className="send-btn"/>
                    </form>
                </>}


            </div>
        </div>
    );
}