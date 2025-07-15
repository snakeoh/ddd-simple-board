import { useQuery } from "@tanstack/react-query";
import api from "../api/axios";
import { Link } from "react-router-dom";

type Post = {
    postId: string;
    authorName: string;
    title: string;
    createdAt: string;
};

export default function PostListPage() {
    const { data, isLoading, error } = useQuery<Post[]>({
        queryKey: ["posts"],
        queryFn: async () => {
            const response = await api.get("/api/posts");
            return response.data;
        },
    });

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>에러가 발생했습니다: {(error as Error).message}</div>;

    return (
        <div>
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
