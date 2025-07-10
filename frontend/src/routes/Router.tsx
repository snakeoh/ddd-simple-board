import { BrowserRouter, Routes, Route } from "react-router-dom";
import PostListPage from "../pages/PostListPage";

export default function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<PostListPage />} />
                {/* 추후 상세보기, 로그인, 글쓰기 등 추가 */}
            </Routes>
        </BrowserRouter>
    );
}
