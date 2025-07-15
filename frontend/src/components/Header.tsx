import { useEffect, useState } from "react";

export default function Header() {
    const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem("accessToken") !== null);

    useEffect(() => {
        const handleStorage = () => {
            setIsLoggedIn(localStorage.getItem("accessToken") !== null);
        };
        window.addEventListener("storage", handleStorage);
        return () => window.removeEventListener("storage", handleStorage);
    }, []);

    const handleLogout = () => {
        localStorage.removeItem("accessToken");
        setIsLoggedIn(false);
        window.location.href = "/";
    };

    return <header>{isLoggedIn ? <button onClick={handleLogout}>로그아웃</button> : <a href="/login">로그인</a>}</header>;
}
