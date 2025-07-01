-- 게시글 샘플
INSERT INTO posts (post_id, author_name_value, title, content, created_at, updated_at)
VALUES 
  ('11111111-1111-1111-1111-111111111111', '홍길동', '첫 번째 게시글입니다.', '내용을 입력합니다.', NOW(), NOW()),
  ('22222222-2222-2222-2222-222222222222', '이몽룡', '두 번째 글', '두 번째 내용입니다.', NOW(), NOW());

-- 댓글 샘플
INSERT INTO comments (comment_id, author_name_value, content, created_at, post_id)
VALUES 
  ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '성춘향', '첫 번째 댓글입니다.', NOW(), '11111111-1111-1111-1111-111111111111'),
  ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '변학도', '두 번째 댓글입니다.', NOW(), '11111111-1111-1111-1111-111111111111'),
  ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', '방자', '두 번째 글에 단 댓글', NOW(), '22222222-2222-2222-2222-222222222222');
