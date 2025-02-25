import {useLocation, useNavigate, useParams} from "react-router-dom";
import { useEffect, useState } from "react";

import "../../../styles/admin-coindetail-style.css"

export default function AdminCoinManagementView() {

    const navigate = useNavigate();
    const location = useLocation()
    const { name, symbol, logoUrl } = location.state || {}


    return (
        <div className="content">

            <div className="admin-coindetail-nav">
                <button className="admin-coindetail-btn-on" onClick={() => {
                    navigate("/admin")
                }}>Coin
                </button>
                <button className="admin-coindetail-btn" onClick={() => {
                    navigate("/admin")
                }}>Dash Board
                </button>
                <button className="admin-coindetail-btn" onClick={() => {
                    navigate("/admin")
                }}>User
                </button>
            </div>

            <header>
                <div style={{display: 'flex'}}>
                    <img src={logoUrl} className="admin-coindetail-logo"/>
                    <div className="admin-coindetail-info">
                        <p className="admin-coindetail-name">{name}</p>
                        <p className="admin-coindetail-symbol">{symbol}</p>
                    </div>
                    <button>ON</button>
                </div>
            </header>

        </div>
    )
}