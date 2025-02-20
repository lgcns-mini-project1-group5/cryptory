import axios from "axios";
import "../../styles/login-style.css"

export default function LoginView() {

    const kakao_button_style = {
        width: "200px",
        height: "40px",
        backgroundImage: 'URL("/kakao_login.png")',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        border: 'none',
        borderRadius: '5px',
    }

    const kakaoLogin = () => {
        axios.get("http://localhost:8080/kakaoLogin")
            .then(res => {
                window.location.href = res.data
            })
            .catch(err => {
                console.log(err);
            });
    }

    return (<div className="login-section">
        <h2 className="login-title">Login</h2>
        <button style={kakao_button_style} onClick={kakaoLogin}></button>
    </div>)
}