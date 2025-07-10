import { useQuery } from "@tanstack/react-query";
import api from "../api/axios";

type Post = {
    postId: string;
    authorName: string;
    title: string;
    createdAt: string;
};

export default function PostListPage() {
    const { data, isLoading } = useQuery<Post[]>({
        queryKey: ["posts"],
        queryFn: async () => {
            const response = await api.get("/posts");
            return response.data;
        },
    });

    if (isLoading) return <div>Loading...</div>;

    return (
        <div>
            <h1>게시글 목록</h1>
            <ul>
                {data?.map((post) => (
                    <li key={post.postId}>
                        <b>{post.title}</b> - {post.authorName} -
                    </li>
                ))}
            </ul>
        </div>
    );
}
