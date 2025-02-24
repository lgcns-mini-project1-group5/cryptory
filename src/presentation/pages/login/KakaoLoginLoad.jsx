import {useEffect} from "react";
import {Cookies, useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";

export default function KakaoLoginLoad() {

    const cookies = useCookies(["accessToken"]);

    const navigate = useNavigate();

    useEffect(() => {
        console.log(cookies)
    }, []);

    return (<>
        로그인중...
    </>)
}