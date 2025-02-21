import React, {useEffect, useState} from "react";
import "../../styles/chart-style.css"
import NewsView from "../main/news/NewsView.jsx";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import PostListView from "./post/PostListView.jsx";
import ModalView from "./prompt/ModalView.jsx";
import CandleChartCell from "./CandleChartCell.jsx";

export default function ChartView() {

    const location = useLocation()

    const { coinId } = useParams()
    const { icon, name, price, change, prior } = location.state || {}

    const [type, setType] = useState("news");
    const [issueId, setIssueId] = useState(0);
    const [issueDate, setIssueDate] = useState("2025년 1월 6일");
    const [modalOpen, setModalOpen] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        if (prior !== undefined) {
            setType("board");
        }
    }, [])

    return (<div className="content">
        <div className="chart-nav">
            <button className="chart-btn-on" onClick={() => {
                navigate("/")
            }}>Coin
            </button>
            <button className="chart-btn" onClick={() => {
                navigate("/")
            }}>News
            </button>
        </div>

        <div style={{display: 'flex'}}>
            <img src={icon} alt="User" className="chart-coin-icon"/>
            <div className="chart-coin-card-info">
                <p className="chart-coin-card-name">{name}</p>
                <p className="chart-coin-card-symbol">{coinId}</p>
            </div>
        </div>
        <div style={{display: "flex", justifyContent: "space-between", width: "650px"}}>
            <div style={{width: '110px', textAlign: 'right'}}>
            <p style={{marginTop: -5, fontSize: '16px', color:"#252525"}}>
                    {price.toLocaleString()}원
                </p>
                <p style={{marginTop: -15}} className={`change ${change >= 0 ? "positive" : "negative"}`}>
                    {change > 0 && <>+</>}
                    {change.toFixed(2)}%
                </p>
            </div>

            <div style={{width: '110px', textAlign: 'right'}}>
                <p style={{marginTop: -25, fontSize: '16px', color:"#252525"}}>
                    {price.toLocaleString()}원
                </p>
                <p style={{marginTop: -12, fontSize: "12px", color: "#6C757D"}}>
                    2025.02.21
                </p>
            </div>
        </div>

        <CandleChartCell/>
        <div style={{
            width: 650,
            height: 480,
            border: "1px solid gray",
            display: "flex",
            justifyContent: "center",
            alignItems: "center"
        }} onClick={() => {
            setModalOpen(true);
        }}>
            chart
        </div>

        {(type === "news") && <>
            <div className="chart-nav">
                <button className="chart-btn-on">News</button>
                <button className="chart-btn" onClick={() => {
                    setType("board")
                }}>게시판
                </button>
            </div>
            <NewsView type={coinId}/>
        </>}

        {(type === "board") && <>
            <div className="chart-nav">
                <button className="chart-btn" onClick={() => {
                    setType("news")
                }}>News
                </button>
                <button className="chart-btn-on">게시판</button>
            </div>
            <PostListView name={name} symbol={coinId} icon={icon} price={price} change={change}/>
        </>}

        {modalOpen && <ModalView onClose={() => {setModalOpen(false)}} issueId={issueId} icon={icon} name={name} symbol={coinId} price={price} change={change} issueDate={issueDate}/>}
    </div>)
}