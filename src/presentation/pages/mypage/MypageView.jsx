import React, {useEffect, useState} from "react";
import "../../styles/mypage-style.css"

export default function MypageView() {

    const [nickname, setNickname] = useState("")
    const [profile, setProfile] = useState("")
    const [editNickname, setEditNickname] = useState(false)
    const [newNickname, setNewNickname] = useState("");
    const [newProfile, setNewProfile] = useState("")

    useEffect(() => {
        // axios
        //     .get(`http://${rest_api_host}:${rest_api_port}/api/v2/board/${boardIdx}`, {headers: {"Authorization": `Bearer ${token}`}})
        //     .then(res => {
        //
        //     })
        //     .catch(err => {
        //
        //     });
        setNickname("UserName")
        setNewNickname("UserName")
        setProfile("https://cryptologos.cc/logos/bitcoin-btc-logo.png")
    }, []);

    const handleProfileImageChange = () => {

    };

    return (<div className="profile-container">
        <div className="profile-image-section">
            <img src={profile} alt="Profile" className="profile-image" />
            <label className="profile-image-btn">
                프로필 사진 변경
                <input type="file" accept="image/*" onChange={handleProfileImageChange} hidden />
            </label>
        </div>

        <div className="nickname-section">
            <p className="nickname-label">닉네임</p>
            {(!editNickname) && <p className="nickname-text">{nickname}</p>}
            {(editNickname) && <input
                type="text"
                className="nickname-input"
                value={newNickname}
                onChange={(e) => setNewNickname(e.target.value)}
            />}
        </div>
        <button className="nickname-btn" onClick={() => {
            setEditNickname(true)
        }}>닉네임 변경
        </button>

        <div className="action-buttons">
            <p className="kakao-login">KakaoLogin</p>
            <button className="delete-account-btn">회원 탈퇴</button>
        </div>

    </div>);
}