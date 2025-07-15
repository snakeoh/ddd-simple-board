import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api/axios";
import CommentList from "../components/CommentList";

type Post = {
    postId: string;
    authorName: string;
    title: string;
    content: string;
    createdAt: string;
    updatedAt: string;
};

export default function PostDetailPage() {
    const { postId } = useParams<{ postId: string }>();
    const [post, setPost] = useState<Post | null>(null);

    useEffect(() => {
        api.get(`/api/posts/${postId}`)
            .then((response) => setPost(response.data))
            .catch((error) => console.error("게시글 불러오기 실패:", error));
    }, [postId]);

    if (!post) return <div>Loading...</div>;

    return (
        <>
            <div className="p-6 max-w-3xl mx-auto">
                <h1 className="text-2xl font-bold">{post.title}</h1>
                <p className="text-grey-600">
                    작성자: {post.authorName} | {new Date(post.createdAt).toLocaleString()}
                </p>
                <div className="mt-4 whitespace-pre-line">{post.content}</div>
            </div>
            <CommentList postId={post.postId} />
        </>
    );
}
