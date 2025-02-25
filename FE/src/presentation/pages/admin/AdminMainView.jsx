import {useState} from "react";
import "../../styles/main-style.css"
import AdminCoinView from "./adminCoin/AdminCoinView";
import AdminDashboardView from "./adminDashboard/AdminDashboardView";
import AdminUserView from "./adminUser/AdminUserView";

export default function AdminMainView() {

    const [type, setType] = useState("coin");


    return (<div className="content">
        {(type === 'coin') && <>
            <div className="main-nav">
                <button className="main-btn-on">Coin</button>
                <button className="main-btn" onClick={() => {setType("dashboard")}}>Dash Board</button>
                <button className="main-btn" onClick={() => {setType("user")}}>User</button>
            </div>
            <AdminCoinView/>
        </>}

        {(type === 'dashboard') && <>
            <div className="main-nav">
                <button className="main-btn" onClick={() => {setType("coin")}}>Coin</button>
                <button className="main-btn-on">Dash Board</button>
                <button className="main-btn" onClick={() => {setType("user")}}>User</button>
            </div>
            <AdminDashboardView/>
        </>}

        {(type === 'user') && <>
            <div className="main-nav">
                <button className="main-btn" onClick={() => {setType("coin")}}>Coin</button>
                <button className="main-btn" onClick={() => {setType("dashboard")}}>Dash Board</button>
                <button className="main-btn-on">User</button>
            </div>
            <AdminUserView/>
        </>}
    </div>)
}