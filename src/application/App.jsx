import {createBrowserRouter, Outlet, RouterProvider, useNavigate} from "react-router-dom";
import MainView from "../presentation/pages/main/MainView.jsx";
import MypageView from "../presentation/pages/mypage/MypageView.jsx";
import ChartView from "../presentation/pages/chart/ChartView.jsx";
import PostListView from "../presentation/pages/chart/post/PostListView.jsx";
import AdminMainView from "../presentation/pages/admin/AdminMainView.jsx";
import AdminCoinView from "../presentation/pages/admin/adminCoin/AdminCoinView.jsx";
import AdminDashboardView from "../presentation/pages/admin/adminDashboard/AdminDashboardView.jsx";
import AdminUserView from "../presentation/pages/admin/adminUser/AdminUserView.jsx";
import AdminLoginView from "../presentation/pages/admin/adminLogin/AdminLoginView.jsx";
import AdminCoinManagementView from "../presentation/pages/admin/adminCoin/AdminCoinManagementView.jsx";
import CoinView from "../presentation/pages/main/coin/CoinView.jsx";
import NewsView from "../presentation/pages/main/news/NewsView.jsx";

import "../presentation/styles/header-style.css"
import LoginView from "../presentation/pages/login/LoginView.jsx";
import PostCreateView from "../presentation/pages/chart/post/PostCreateView.jsx";
import PostEditView from "../presentation/pages/chart/post/PostEditView.jsx";
import PostView from "../presentation/pages/chart/post/PostView.jsx";
import KakaoLoginLoad from "../presentation/pages/login/KakaoLoginLoad.jsx";
import KakaoLogout from "../presentation/pages/login/KakaoLogout.jsx";
import React, {useEffect, useState} from "react";
import axios from "axios";

const Layout = () => {

    const rest_api_host = import.meta.env.VITE_REST_API_HOST;
    const rest_api_port = import.meta.env.VITE_REST_API_PORT;

    const login = sessionStorage.getItem("isLogin");
    const navigate = useNavigate();

    const websiteForm = {
        width: 1920,
        height: 1080,
        backgroundColor: '#FFFFFF',
        margin: 'auto',
    }

    const [nickname, setNickname] = useState("")
    const [profile, setProfile] = useState("")

    useEffect(() => {

        if (login) {
            axios
                .get(`http://${rest_api_host}:${rest_api_port}/api/v1/users/me`, {headers: {"Authorization": `Bearer ${sessionStorage.getItem("token")}`}})
                .then(res => {
                    setNickname(res.data.userId);
                    setProfile(res.data.imageUrl)
                    sessionStorage.setItem("name", res.data.userId)
                })
                .catch(err => {
                    setNickname("UserName")
                    setProfile("https://cryptologos.cc/logos/bitcoin-btc-logo.png")
                });

        }
    }, []);

    return (<div style={websiteForm}>
        <banner className="banner">
            <header className="header">
                <h1 className="title" onClick={() => {navigate("/")}}>Cryptory</h1>
                {(login) && <div className="user-info">
                    <img src={profile} alt={nickname} className="coin-icon"/>
                    <span className="username" onClick={() => {
                        navigate("/mypage")
                    }}>UserName</span>
                    <button className="logout-btn" onClick={() => {
                        navigate("/kakaologout")
                    }}>Logout
                    </button>
                </div>}
                {(!login) && <div className="user-info">
                    <button className="logout-btn" onClick={() => {navigate("/login")}}>Login</button>
                </div>}
            </header>
        </banner>
        <Outlet/>
    </div>)
}

const AdminLayout = () => {

    const navigate = useNavigate();

    const websiteForm = {
        width: 1920,
        height: 1080,
        backgroundColor: '#F8F8F8',
        margin: 'auto',
    }

    return (<div style={websiteForm}>
        <banner className="banner">
            <header className="header">
                <h1 className="title" onClick={() => {navigate("/admin")}}>Cryptory</h1>
                <div className="user-info">
                    <span className="username" onClick={() => {navigate("/mypage")}}>UserName</span>
                    <button className="logout-btn">Logout</button>
                </div>
            </header>
        </banner>
        <Outlet/>
    </div>)
}

const router = createBrowserRouter([
    {
        path: '/',
        element: <Layout/>,
        children: [
            { path: "", element: <MainView/> },
            { path: "coin", element: <CoinView/> },
            { path: "news", element: <NewsView/> },
            { path: "mypage", element: <MypageView/> },
            { path: "coin/:coinId", element: <ChartView/> },
            { path: "post", element: <PostCreateView/> },
            { path: "post/:postId", element: <PostView/> },
            { path: "post/edit", element: <PostEditView/> },
            { path: "login", element: <LoginView/> },
            { path: "oauth2/callback", element: <KakaoLoginLoad/> },
            { path: "kakaologout", element: <KakaoLogout/> },
        ]
    },
    {
        path: '/admin',
        element: <AdminLayout />,
        children: [
            { path: "", element: <AdminMainView/> },
            { path: "coin", element: <AdminCoinView/> },
            { path: "coin/:coinId", element: <AdminCoinManagementView/> },
            { path: "dashboard", element: <AdminDashboardView/> },
            { path: "user", element: <AdminUserView/> },
            { path: "login", element: <AdminLoginView/> },
        ]
    },
])

export default function App() {
    return <RouterProvider router={router} />
}