import {useParams, useNavigate} from "react-router-dom";

export default function AdminCoinCard({cryptoId, name, symbol, logoUrl, isDisplayed}) {

    //const { coinId } = useParams();

    const navigate = useNavigate();

    return (
        <div className={`admin-coin-item ${isDisplayed ? "displayed" : ""}`}
            onClick={() => {navigate(`/admin/coin/${cryptoId}`, {state: {name:name, symbol:symbol, logoUrl:logoUrl, isDisplayedFront:isDisplayed}})}}
        >
            
            <img src={logoUrl} alt={name} className="admin-coin-logo" />
            <div style={{ display: "flex", flexDirection: "column", gap: "3px" }}>
                <span className="admin-coin-name">{name}</span>
                <small className="admin-coin-symbol">{symbol}</small>
            </div>
        </div>
    )
}