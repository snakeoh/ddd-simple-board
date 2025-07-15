// import { isLoggedIn } from "../utils/auth";
import { useEffect, useState } from "react";
import api from "../api/axios";

type Comment = {
    commentId: string;
    authorName: string;
    content: string;
    createdAt: string;
};

interface Props {
    postId: string;
}

export default function CommentList({ postId }: Props) {
    const [comments, setComments] = useState<Comment[]>([]);

    useEffect(() => {
        api.get(`/api/posts/${postId}/comments`)
            .then((response) => setComments(response.data))
            .catch((error) => console.error("댓글 불러오기 실패:", error));
    }, [postId]);

    return (
        <div className="mt-8">
            <h2 className="text-xl font-semibold mb-2">댓글</h2>
            {comments.length === 0 && <p>댓글이 없습니다.</p>}
            <ul className="space-y-4">
                {comments.map((comment) => (
                    <li key={comment.commentId} className="border-b pb-2">
                        <div className="text-sm text-gray-700">
                            {comment.authorName} | {new Date(comment.createdAt).toLocaleString()}
                        </div>
                        <div className="mt-1">{comment.content}</div>
                    </li>
                ))}
            </ul>
        </div>
    );
}
