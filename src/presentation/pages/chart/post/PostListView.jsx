import axios from "axios";
import {useEffect, useState} from "react";
import PostCard from "./PostCard.jsx";
import {useNavigate} from "react-router-dom";

export default function PostListView({ name, symbol, icon, price, change }) {

    const [postData, setPostData] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        // axios
        //     .get(`http://${rest_api_host}:${rest_api_port}/api/v2/board/${boardIdx}`, {headers: {"Authorization": `Bearer ${token}`}})
        //     .then(res => {
        //
        //     })
        //     .catch(err => {
        //
        //     });

        setPostData([
            {
                postId: 1,
                title: "비트코인 게시판 제목 1",
                author: "User1",
                date: "2025.02.19",
            },
        ]);
    }, [])

    return (<>
        <button className="write-btn" onClick={() => {navigate("/post", {state: {name:name, symbol:symbol, icon:icon, price:price, change:change}})}}>글쓰기</button>
        <div className="board">

            {postData.map((post, index) => (
                <PostCard key={index} {...post} name={name} symbol={symbol} icon={icon} price={price} change={change} />
            ))}
        </div>
    </>);
}