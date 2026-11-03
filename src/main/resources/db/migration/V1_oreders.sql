
-- 이메일 등 대소문자 구분이 필요한 문자열은 테이블 단위로 utf8mb4_bin을 지정한다.

CREATE TABLE orders (
                        id            INT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(100) COLLATE utf8mb4_bin NOT NULL,
                        address       VARCHAR(200) NOT NULL,
                        postcode      VARCHAR(5)   NOT NULL,
                        shipping_date DATE         NOT NULL,
                        status        ENUM('PLACED','CANCELLED') NOT NULL,
                        memo          VARCHAR(200) NOT NULL DEFAULT '',
                        total_amount  INT          NOT NULL,
                        created_at    DATETIME(0)  NOT NULL,
                        cancelled_at  DATETIME(0)  NULL,
                        INDEX idx_orders_ship          (shipping_date, status, email),
                        INDEX idx_orders_email_created (email, created_at, id)
) ;