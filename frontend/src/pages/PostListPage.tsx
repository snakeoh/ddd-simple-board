import { useQuery } from "@tanstack/react-query";
import api from "../api/axios";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

type Post = {
    postId: string;
    authorName: string;
    title: string;
    createdAt: string;
};

export default function PostListPage() {
    const navigate = useNavigate();
    const [showForm, setShowForm] = useState(false);
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [error, setError] = useState("");

    const {
        data,
        isLoading,
        error: queryError,
        refetch,
    } = useQuery<Post[]>({
        queryKey: ["posts"],
        queryFn: async () => {
            const response = await api.get("/api/posts");
            return response.data;
        },
    });

    const handleWriteClick = () => setShowForm(true);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        try {
            await api.post("/api/posts", {
                title,
                content,
                authorName: localStorage.getItem("username"),
            });
            setShowForm(false);
            setTitle("");
            setContent("");
            refetch();
        } catch (err) {
            setError("글 등록에 실패했습니다.");
        }
    };

    if (isLoading) return <div>Loading...</div>;
    if (queryError) return <div>에러가 발생했습니다: {(queryError as Error).message}</div>;

    return (
        <div>
            <button onClick={handleWriteClick} className="bg-blue-500 text-white px-4 py-2 rounded mb-4">
                글쓰기
            </button>
            {showForm && (
                <form onSubmit={handleSubmit} className="mb-6 p-4 border rounded">
                    <div>
                        <label className="block mb-1">제목</label>
                        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} placeholder="제목_" className="bordeer p-2 w-full mb-2" required />
                    </div>
                    <div>
                        <label className="block mb-1">내용</label>
                        <textarea value={content} onChange={(e) => setContent(e.target.value)} placeholder="내용_" className="border p-2 w-full rounded mb-2" required />
                    </div>
                    {error && <p className="text-red-500 text-sm">{error}</p>}
                    <button type="submit" className="bg-green-500 text-white px-4 py-2 rounded">
                        등록
                    </button>
                    <button type="button" onClick={() => setShowForm(false)} className="ml-2 px-4 py-2 rounded border">
                        취소
                    </button>
                </form>
            )}
            <h1>게시글 목록</h1>
            <ul>
                {data?.map((post) => (
                    <li key={post.postId}>
                        <Link to={`/posts/${post.postId}`}>
                            <b>{post.title}</b>
                        </Link>
                        {" - "}
                        {post.authorName}
                    </li>
                ))}
            </ul>
        </div>
    );
}
