-- 게시글 샘플
INSERT INTO posts (post_id, author_name, title, content, created_at, updated_at, status, visible) VALUES ('11111111-1111-1111-1111-111111111111', 'aaa', '첫 번째 게시글입니다.', '내용을 입력합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'NORMAL', true), ('22222222-2222-2222-2222-222222222222', 'bbb', '두 번째 글', '두 번째 내용입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'NORMAL', true);

-- 댓글 샘플
INSERT INTO comments (comment_id, author_name, content, created_at, post_id) VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'ccc', '첫 번째 댓글입니다.', CURRENT_TIMESTAMP, '11111111-1111-1111-1111-111111111111'), ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'ddd', '두 번째 댓글입니다.', CURRENT_TIMESTAMP, '11111111-1111-1111-1111-111111111111'), ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'ee', '두 번째 글에 단 댓글', CURRENT_TIMESTAMP, '22222222-2222-2222-2222-222222222222');

-- INSERT INTO users (user_id, username, password, role) VALUES ('bbbbbbb2-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'aaa', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=', 'ADMIN');
INSERT INTO users (user_id, username, password, role) VALUES ('bbbbbbb2-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'aaa', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'ADMIN');
