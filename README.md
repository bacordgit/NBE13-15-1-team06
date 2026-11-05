# NBE13-15-1-team06
1차 팀프로젝트 6팀
### 1. 팀원별 담당 작업
- 박성훈 : statistics 담당(배송·통계 기능 API 4개: 배송 묶음(A14), 접수일별 주문 통계(A17), 메뉴 통계(A18), 고객 통계(A24))
- 맹주석 : order 담당 (주문 접수(A11), 이메일별 목록(A12), 상세(A13), 취소(A16), 메모 수정(A22), 재주문(A23))
- 정민성 : category 담당
- 배재영 : menu 담당 (전체 메뉴 조회(A06), id로 메뉴 조회(A07), 생성(A08), 수정(A09), 삭제(A10), 판매 가능 메뉴 조회(A19),이름으로 메뉴 조회(A20), 판매 여부 변경(A21))
초기 데이터 설정: InitDataRunner
- 
### 2. 공동 구현 내역
10개의 Exception을 나눠서 개발하였다.

### 3. 완료 기능
   - 카테고리 목록 조회
   - 카테고리 상세 조회
   - 카테고리 등록
   - 카테고리 수정
   - 카테고리 삭제
   - 전체 메뉴 목록 조회
   - 메뉴 상세 조회
   - 메뉴 등록
   - 메뉴 일반 수정
   - 메뉴 삭제
   - 주문 접수
   - 이메일별 주문
   - 주문 상세
   - 배송 묶음
   - 카테고리별 메뉴
   - 주문 취소
   - 접수일별 주문 통계
   - 접수일별 메뉴 통계
   - 판매 중 메뉴
   - 메뉴명 검색
   - 판매 여부 변경
   - 주문 메모 수정
   - 재주문
   - 접수일별 고객 통계

### 4. 미완료 기능
- 업무 규칙 자동 테스트
- 실제 DB 롤백 저장 테스트
- Redis 메뉴 조회 캐시
- 별도 메뉴 페이징 API
- RestClient 외부 조회,실패 처리

### 5. 개발 환경
- Java 21
- Spring boot 4.1.2
- MySQL 8.4.11

### 6. DB 설정
- 설정 파일 위치: src/main/resources/application.yaml
- DB_URL: jdbc:mysql://127.0.0.1:3306/kdt_project1?allowPublicKeyRetrieval=true&sslMode=DISABLED
- username: root
- password: kdtpass

### 7.DB 준비 및 초기 데이터

테이블 4개는 다음과 같이 생성했다.
```sql
CREATE TABLE categories(
id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(100) COLLATE utf8mb4_bin NOT NULL,
CONSTRAINT pk_categories PRIMARY KEY (id),
CONSTRAINT uk_categories_name UNIQUE (name)
);

CREATE TABLE menus(
id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(100)   CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
price INT NOT NULL,
description VARCHAR(300) NOT NULL,
available BOOLEAN NOT NULL,
category_id BIGINT NOT NULL,
CONSTRAINT pk_menus PRIMARY KEY (id),
CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE orders(
id BIGINT NOT NULL AUTO_INCREMENT,
email VARCHAR(100)   CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
address VARCHAR(200) NOT NULL,
postcode VARCHAR(5) NOT NULL,
created_at DATETIME NOT NULL,
updated_at DATETIME,
total_amount BIGINT NOT NULL,
status ENUM('PLACED','CANCELLED') NOT NULL,
canceled_at DATETIME,
shipping_date DATE NOT NULL,
memo VARCHAR(200) NOT NULL DEFAULT '',
CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE order_items (
id          BIGINT       NOT NULL AUTO_INCREMENT,
order_id    BIGINT       NOT NULL,
menu_id     BIGINT       NOT NULL,
menu_name   VARCHAR(100) NOT NULL,
unit_price  BIGINT       NOT NULL,
quantity    BIGINT       NOT NULL,
line_amount BIGINT       NOT NULL,
CONSTRAINT pk_order_items PRIMARY KEY (id),
CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id),
CONSTRAINT fk_order_items_menu  FOREIGN KEY (menu_id)  REFERENCES menus (id)
);
```

초기 데이터는 다음과 같다.
- categories
 싱글오리진, 블렌더
- menus

  | 메뉴명 | 가격(원) | 설명 |
  | --- | --- | --- |
  | Columbia Nariñó | 5000 | 콜롬비아 나리뇨 원두를 담은 커피빈 패키지입니다. |
  | Brazil Serra Do Caparaó | 7000 | 브라질 세하 두 카파라오 원두를 담은 커피빈 패키지입니다. |
  | Columbia Quindío (White Wine Extended Fermentation) | 9000 | 콜롬비아 킨디오 원두를 담은 커피빈 패키지입니다. |
  | Ethiopia Sidamo | 11000 | 에티오피아 시다모 원두를 담은 커피빈 패키지입니다. |
- 스프링 실행시, 자동으로 초기 데이터가 저장된다.

### 8. 백엔드, web 실행
1. MySQL과 팀의 백엔드를 실행합니다.
2. 다른 터미널에서 제공된 web 폴더로 이동한 뒤 다음 명령을 실행합니다.
   java -m jdk.httpserver -b 127.0.0.1 -p 8000
   ​
3. http://127.0.0.1:8000/에 접속합니다.
4. 연결 설정 → 실제 API 연결을 선택하고, API 서버 주소에 http://127.0.0.1:8080을 입력합니다. 주소 끝에 /api/v1을 붙이지 않습니다.

### http 점검
- 경로: `requests/api-check.http`
- 실행 순서: 파일 위에서부터 차례로 실행 (정상 24개 + 오류 6개)

### 시각 경계값 확인
- 기준: <예: 오후 2시 이전 접수는 당일 배송 묶음, 2시 이후는 다음 날 묶음>
- 확인 방법: 13:59:59 와 14:00:00 에 접수된 주문 데이터를 준비해 배송 묶음 API 결과에서 서로 다른 묶음으로 나오는지 확인
- 접수일별 통계: 23:59:59 와 00:00:00 주문이 서로 다른 날짜로 집계되는지 확인

## 9. 평가 대상
- 브랜치: `feature/init`

## 10. 선택 심화
- 구현한 선택 심화 없음 (Redis 캐시, 메뉴 페이징, RestClient 외부 조회는 미완료)