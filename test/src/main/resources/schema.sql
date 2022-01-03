CREATE TABLE `user` (
	id				bigint 			NOT NULL AUTO_INCREMENT 				COMMENT '사용자 PK',
	name 			varchar(20) 	NOT NULL 								COMMENT '사용자 이름',
	nickname 		varchar(30) 	NOT NULL 								COMMENT '사용자 별명',
	password 		varchar(100) 	NOT NULL 								COMMENT '사용자 비밀번호',
	mobile 			long 			NOT NULL 								COMMENT '사용자 전화번호',
	email 			varchar(100)	NOT NULL 								COMMENT '사용자 이메일 주소',
	sex 			int 					 DEFAULT 0						COMMENT '사용자 성별',
	created_at 		datetime 		NOT NULL DEFAULT CURRENT_TIMESTAMP()	COMMENT '가입 날짜',
	modified_at 	datetime 												COMMENT '회원 정보 수정 날짜',
	last_login_at 	datetime 												COMMENT '마지막 로그인 날짜',
	PRIMARY KEY (`id`),
	CONSTRAINT unique_user_email UNIQUE (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `order_history` (
	id				bigint 			NOT NULL AUTO_INCREMENT 				COMMENT '주문 PK',
	order_no		varchar(12)		NOT NULL								COMMENT '주문 번호',
	product_name	varchar(100)	NOT NULL								COMMENT '주문 상품 이름',
	user_id			bigint			NOT NULL								COMMENT 'user FK',
	created_at 		datetime 		NOT NULL DEFAULT CURRENT_TIMESTAMP()	COMMENT '주문 생성 날짜',
	paid_at 		datetime 												COMMENT '결제 날짜',
	PRIMARY KEY (`id`),
	CONSTRAINT unique_order_product UNIQUE (`order_no`),
	CONSTRAINT foreign_key_order_user FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;