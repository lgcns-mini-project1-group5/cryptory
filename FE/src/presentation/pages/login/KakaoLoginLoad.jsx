import {useEffect} from "react";
import {Cookies, useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import axios from "axios";

export default function KakaoLoginLoad() {
    const [cookies] = useCookies();
    const navigate = useNavigate();

    useEffect(() => {
        if (cookies.accessToken) {
            console.log('Access Token:', cookies.accessToken);
            
            axios.get('http://localhost:8080/api/v1/users/me', {
                headers: {
                    Authorization: `Bearer ${cookies.accessToken}`,
                },
            })
            .then(response => {
                console.log('회원 정보:', response.data);
                navigate('/');
            })
            .catch(error => {
                console.error('회원 정보 조회 오류:', error);
                navigate('/login');
            });
        } else {
            console.log('No Access Token found');
            navigate('/login');
        }
    }, [cookies, navigate]);


    return (
        <>
            로그인중...
        </>
    );
}