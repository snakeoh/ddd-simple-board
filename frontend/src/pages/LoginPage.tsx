import { useState } from "react";
import api from "../api/axios";
// import { useNavigate } from "react-router-dom";

export default function LoginPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    // const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");

        try {
            const response = await api.post("/auth/login", {
                username,
                password,
            });

            const accessToken = response.data.accessToken;
            localStorage.setItem("accessToken", accessToken);
            localStorage.setItem("username", username);
            alert("로그인 성공!");
            window.location.replace("/posts");
        } catch (error: unknown) {
            console.error("로그인 실패:", error);
            setError("로그인 요청에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.");
            console.error("로그인 실패:", error);
        }
    };

    return (
        <div className="max-w-md mx-auto mt-20 p-6 boder rounded-lg shadow">
            <h1 className="text-2xl font-bold mb-4">로그인</h1>
            <form onSubmit={handleLogin} className="space-y-4">
                <div>
                    <label className="block mb-1 font-medium">사용자 이름</label>
                    <input type="text" className="w-full p-2 border rounded" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
                <div>
                    <label className="block mb-1 font-medium">비밀번호</label>
                    <input type="password" className="w-full p-2 border rounded" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                {error && <p className="text-red-500 text-sm">{error}</p>}
                <button type="submit" className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700">
                    로그인
                </button>
            </form>
        </div>
    );
}
