import React, {useEffect, useState} from "react";
import "../../../styles/prompt-style.css"
import CommentCell from "../post/CommentCell.jsx";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import InputCell from "./InputCell.jsx";
import ResponseCell from "./ResponseCell.jsx";

export default function ModalView({ onClose, coinId, issueId, icon, name, symbol, price, change, issueDate, isNew }) {

    const rest_api_host = import.meta.env.VITE_REST_API_HOST;
    const rest_api_port = import.meta.env.VITE_REST_API_PORT;
    const gpt_api_port = import.meta.env.VITE_GPT_API_PORT;

    const isLogin = sessionStorage.getItem("isLogin");
    const navigate = useNavigate();

    const [type, setType] = useState("ChatGPT")
    const [prompt, setPrompt] = useState("")

    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")
    const [news1, setNews1] = useState("")
    const [news1Link, setNews1Link] = useState("")
    const [news2, setNews2] = useState("")
    const [news2Link, setNews2Link] = useState("")

    const [commentList, setCommentList] = useState([])

    const [responseList, setResponseList] = useState([])

    const handleSubmit = (e) => {
        e.preventDefault();
        if (prompt.trim()) {
            alert(`새 댓글: ${prompt}`);
            setPrompt(""); // 입력 후 초기화
        }
    };

    useEffect(() => {

        if (issueId === "new") {
            axios({
                method: "POST",
                url: `http://${rest_api_host}:${gpt_api_port}/api/v1/issue`,
                data: {
                    "name": name,
                    "date": issueDate,
                },
                headers: { "Content-Type": "application/json"}
            })
                .then(res => {
                    console.log(res)
                    setTitle(res.data.title)
                    setContent(res.data.content)
                    setNews1(res.data.news1_title)
                    setNews1Link(res.data.news1_link)
                    setNews2(res.data.news2_title)
                    setNews2Link(res.data.news2_link)
                })
                .catch((err) => {

                })
        }
        else {
            axios
                .get(`http://${rest_api_host}:${rest_api_port}/api/v1/coins/${coinId}/issues/${issueId}`, {headers: {"Content-Type": "application/json", "Authorization": `Bearer ${sessionStorage.getItem("token")}`}})
                .then(res => {
                    console.log(res);
                    if (res.data.status.code === 404) {
                        throw new Error("존재하지 않는 이슈입니다."); // 강제로 catch()로 이동
                    }
                    
                    setTitle(res.data.title)
                    setContent(res.data.summaryContent)
                    setNews1(res.data.newsTitle)
                    setNews1Link(res.data.source)
                    // setTitle("트럼프 대통령 당선, 비트코인 가격 상승 촉발")
                    // setContent("도널드 트럼프 전 미국 대통령의 당선이 공식 인증된 1월 6일, 비트코인 " +
                    //     "가격은 10만 달러 선을 재돌파하며 큰 폭의 상승을 보였습니다. 트럼프 " +
                    //     "당선인은 선거 기간 동안 미국을 '암호화폐의 수도'로 만들겠다는 공약을 " +
                    //     "내세웠으며, 이러한 친암호화폐 정책 기대감이 투자자들의 심리를 " +
                    //     "자극했습니다. 그러나 취임 이후 구체적인 정책 부재와 경제 불확실성으로 인해 비트코인 가격은 하락세로 전환되어, 2월 18일 기준 9만5,507달러로 최고점 대비 14% 하락하였습니다.")
                    // setNews1("'트럼프 당선' 공식 인증에 비트코인 상승…10만달러선 탈환")
                    // setNews1Link("https://www.naver.com")

                    // 현재 두 번째 뉴스는 임의로 넣어둠
                    setNews2("이제 '트럼프 트레이드'는 금?…달러·비트코인은 주춤")
                    setNews2Link("https://www.naver.com")
                })
                .catch(err => {
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
                });
        }
    }, []);

    const getCommentList = () => {
        axios
            .get(`http://${rest_api_host}:${rest_api_port}/api/v1/coins/${coinId}/issues/${issueId}/comments`, {headers: {"Content-Type": "application/json"}})
            .then(res => {
                let tempList = [];
                res.data.comments.map(item => {
                    tempList.push({ content: item.content, author: item.nickname, date: item.createdAt})
                });
                setCommentList(tempList);
            })
            .catch((err) => {
                setCommentList([
                    { content: "비트코인 게시판 내용 1", author: "User1", date: "2025.02.18"},
                    { content: "비트코인 게시판 내용 2", author: "User2", date: "2025.02.19"},
                    { content: "내가 쓴 비트코인 게시판 내용 1", author: "my", date: "2025.02.19"},
                ])
            })
    }

    const commentPost = () => {
        axios
            .post(`http://${rest_api_host}:${rest_api_port}/api/v1/coins/${coinId}/issues/${issueId}/comments`,
                {
                    params: {"content": prompt},
                    headers: {"Content-Type": "application/json", "Authorization": `Bearer ${sessionStorage.getItem("token")}`}
                })
            .then(res => {
                alert("등록되었습니다.")
                setCommentList([...commentList, { content: res.data.content, author: res.data.nickname, date: res.data.createdAt, commentId: res.data.id}]);
            })
            .catch((err) => {
                setCommentList([
                    { content: "비트코인 게시판 내용 1", author: "User1", date: "2025.02.18", commentId: 1},
                    { content: "비트코인 게시판 내용 2", author: "User2", date: "2025.02.19", commentId: 2},
                    { content: "내가 쓴 비트코인 게시판 내용 1", author: "my", date: "2025.02.19", commentId: 3},
                ])
            })
    }

    const commentEdit = (id) => {
        axios
            .patch(`http://${rest_api_host}:${rest_api_port}/api/v1/coins/${coinId}/issues/${issueId}/comments/${id}`,
                {
                    params: {"content": prompt},
                    headers: {"Content-Type": "application/json", "Authorization": `Bearer ${sessionStorage.getItem("token")}`}
                })
            .then(res => {
                alert("수정되었습니다.")
                let tempCommentList = [];
                commentList.map((item) => {
                    if (item.commentId === id) {
                        tempCommentList.push({ content: prompt, author: item.author, date: item.date, commentId: item.commentId})
                    } // 수정 필요
                    else { tempCommentList.push(item) }
                })
                setCommentList(tempCommentList);
            })
            .catch((err) => {

            })
    }

    const commentDelete = (id) => {
        axios
            .put(`http://${rest_api_host}:${rest_api_port}/api/v1/coins/${coinId}/issues/${issueId}/comments/${id}`,
                {
                    headers: {"Content-Type": "application/json", "Authorization": `Bearer ${sessionStorage.getItem("token")}`}
                })
            .then(res => {
                alert("삭제되었습니다.")
                let tempCommentList = [];
                commentList.map((item) => {
                    if (item.commentId !== id) {
                        tempCommentList.push(item)
                    }
                })
                setCommentList(tempCommentList);
            })
            .catch((err) => {

            })
    }

    const getGPTResponse = () => {
        axios({
            method: "POST",
            url: `http://${rest_api_host}:${gpt_api_port}/api/v1/prompt`,
            data: {
                "name": name,
                "date": issueDate,
                "title": title,
                "content": content,
                "prompt": prompt,
                "skip": "search"
            },
            headers: { "Content-Type": "application/json"}
        })
        .then(res => {
            console.log(res.data.content)
            setResponseList([...responseList, {"input": prompt, "response": res.data.content}])
            setPrompt("")
        })
        .catch((err) => {

        })
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-btn" onClick={onClose}/>
                <div style={{display: "flex"}}>
                    <div className="user-info-section">
                        <img src={icon} alt={name} className="coin-card-icon"/>
                        <div className="coin-card-info">
                            <p className="coin-card-name">{name}</p>
                            <p className="coin-card-symbol">{symbol}</p>
                        </div>
                    </div>
                    <div style={{marginLeft: "60px", color:"#6C757D", fontSize: "16px"}}>
                        <p>종가</p>
                        <p style={{marginTop: -15}}>전일 대비</p>
                    </div>
                    <div style={{width: '110px', textAlign: 'right', fontSize: "16px"}}>
                        <p>
                            {price.toLocaleString()}원
                        </p>
                        <p style={{marginTop: -15, fontSize: "16px"}} className={`change ${change >= 0 ? "positive" : "negative"}`}>
                            {change > 0 && <>+</>}
                            {change.toFixed(2)}%
                        </p>
                    </div>
                </div>
                <p style={{marginTop: -20, marginLeft:10, color:"#6C757D", fontSize:18}}>{issueDate}</p>

                {(type === "ChatGPT") && <>
                    {(issueId !== "new") && <div className="prompt-nav">
                        <button className="prompt-btn-on">ChatGPT</button>
                        <button className="prompt-btn" onClick={() => {
                            getCommentList();
                            setType("debate");
                        }}>토론방
                        </button>
                    </div>}
                    <div className="prompt-section">
                        <div className="news-section">
                            <h2 className="prompt-news-title">{title}</h2>
                            <p className="prompt-news-content">{content}</p>
                        </div>
                        <div className="chat-messages">
                            {(news1.length > 0) && <button className="chat-message" onClick={() => {
                                window.open(news1Link)
                            }}>{news1}</button>}
                            {(news2.length > 0) && <button className="chat-message" onClick={() => {
                                window.open(news2Link)
                            }}>{news2}</button>}
                        </div>
                    </div>

                    {(responseList.length > 0) && <div className="prompt-section">
                        {responseList.map((response) => {return (<>
                            <InputCell content={response.input} />
                            <ResponseCell content={response.response} />
                        </>)})}
                    </div>}

                    {(isLogin === null) && <div className="comment-form">
                        <p className="un-login-prompt"><a className="un-login-prompt-toLogin" onClick={() => {navigate("/login")}}>로그인</a> 후 이용할 수 있습니다</p>
                    </div>}

                    {(isLogin !== null) && <form className="comment-form" onSubmit={handleSubmit}>
                            <textarea
                                className="comment-input"
                                placeholder="궁금한 내용을 입력하세요!"
                                value={prompt}
                                onChange={(e) => setPrompt(e.target.value)}/>
                        <button type="submit" className="gpt-send-btn" onClick={() => {getGPTResponse()}}/>
                    </form>}
                </>
                }

                {(type === "debate") && <>
                    <div className="prompt-nav">
                        <button className="prompt-btn" onClick={() => {
                            setType("ChatGPT")
                        }}>ChatGPT
                        </button>
                        <button className="prompt-btn-on">토론방</button>
                    </div>
                    <div className="prompt-section">
                        <div className="comment-list">
                            {commentList.map((comment, index) =>
                                <CommentCell key={index} {...comment} />
                            )}
                        </div>
                    </div>

                    {(isLogin === null) && <div className="comment-form">
                        <p className="un-login-prompt"><a className="un-login-prompt-toLogin" onClick={() => {navigate("/login")}}>로그인</a> 후 이용할 수 있습니다</p>
                    </div>}

                    {(isLogin !== null) && <form className="comment-form" onSubmit={handleSubmit}>
                        <textarea
                            className="comment-input"
                            placeholder="새로운 글을 입력하세요"
                            value={prompt}
                            onChange={(e) => setPrompt(e.target.value)}/>
                        <button type="submit" className="send-btn" onClick={() => {commentPost()}}/>
                    </form>}
                </>}


            </div>
        </div>
    );
}