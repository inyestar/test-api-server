-- 사용자 데이터
INSERT INTO `user` (id, name, nickname, password, mobile, email)
VALUES (1, 'johnz', 'testnick', 'sdDaf1sd#fasd', 01312312, 'sdasdf@naver.com');
INSERT INTO `user` (name, nickname, password, mobile, email)
VALUES ('sam', 'testnick1', 'sdDaf1sd#fasd', 01312312, 'xerfds@naver.com');
INSERT INTO `user` (name, nickname, password, mobile, email)
VALUES ('sara', 'testnick2', 'sdDaf1sd#fasd', 01312312, 'dferwe@naver.com');
INSERT INTO `user` (name, nickname, password, mobile, email)
VALUES ('abc', 'testnick3', 'sdDaf1sd#fasd', 01312312, 'xerfds1@naver.com');
INSERT INTO `user` (name, nickname, password, mobile, email)
VALUES ('def', 'testnick4', 'sdDaf1sd#fasd', 01312312, 'dferwe3@naver.com');
INSERT INTO `user` (name, nickname, password, mobile, email)
VALUES ('hij', 'testnick5', 'sdDaf1sd#fasd', 01312312, 'xerfds5z@naver.com');
INSERT INTO `user` (name, nickname, password, mobile, email)
VALUES ('kmlz', 'testnick6', 'sdDaf1sd#fasd', 01312312, 'dferwe9@naver.com');

-- 주문 데이터
INSERT INTO `order_history` (id, order_no, product_name, user_id)
VALUES (1, 'DSFJAKELAFSD', '이것은 프로덕트', 1);