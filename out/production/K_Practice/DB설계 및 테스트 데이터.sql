-- 1. 테이블 삭제 (기존 객체들이 있을 경우 삭제)
BEGIN
    FOR rec IN (SELECT table_name FROM user_tables) LOOP
        EXECUTE IMMEDIATE 'DROP TABLE ' || rec.table_name || ' CASCADE CONSTRAINTS';
    END LOOP;
END;
/
BEGIN
    FOR rec IN (SELECT sequence_name FROM user_sequences) LOOP
        EXECUTE IMMEDIATE 'DROP SEQUENCE ' || rec.sequence_name;
    END LOOP;
END;
/
BEGIN
    FOR rec IN (SELECT trigger_name FROM user_triggers) LOOP
        EXECUTE IMMEDIATE 'DROP TRIGGER ' || rec.trigger_name;
    END LOOP;
END;
/

-- 2. 시퀀스 생성
CREATE SEQUENCE seq_rank_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_employee_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_ingredient_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_product_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sale_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_order_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_payment_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_report_id START WITH 1 INCREMENT BY 1;

-- 3. Rank 테이블 생성
CREATE TABLE "BASIC"."RANK" 
(
    "RID" INT PRIMARY KEY,  -- 직급 ID
    "RankName" VARCHAR(255) NOT NULL,  -- 직급 이름
    "Description" VARCHAR(255)  -- 직급 설명
);

-- 4. Employees 테이블 생성
CREATE TABLE "BASIC"."EMPLOYEES" 
(
    "EID" INT PRIMARY KEY,  -- 직원 ID
    "ID" VARCHAR(255) NOT NULL,  -- 직원 아이디
    "PW" VARCHAR(255) NOT NULL,  -- 직원 비밀번호
    "Name" VARCHAR(255) NOT NULL,  -- 직원 이름
    "Contact" VARCHAR(255) NOT NULL,  -- 연락처
    "Rank" INT,  -- 직급 (외래키)
    "JoinDate" DATE,  -- 입사일
    FOREIGN KEY ("Rank") REFERENCES "BASIC"."RANK" ("RID")
);

-- 5. IngredientCategories 테이블 생성
CREATE TABLE "BASIC"."INGREDIENTCATEGORIES" 
(
    "CATEGORY_ID" INT PRIMARY KEY,  -- 카테고리 고유 ID
    "CATEGORY_NAME" VARCHAR(255) NOT NULL  -- 카테고리 이름
);

-- 6. Ingredients 테이블 생성
CREATE TABLE "BASIC"."INGREDIENTS" 
(
    "INGREDIENT_ID" INT PRIMARY KEY,  -- 재료 고유 ID
    "INGREDIENT_NAME" VARCHAR2(255) NOT NULL,  -- 재료 이름
    "CATEGORY_ID" INT,  -- 재료 카테고리 (외래키)
    "STOCK" INT NOT NULL,  -- 재고 수량
    "UNIT_PRICE" DECIMAL(10,2) NOT NULL,  -- 재료 단가
    FOREIGN KEY ("CATEGORY_ID") REFERENCES "BASIC"."INGREDIENTCATEGORIES" ("CATEGORY_ID")
);

-- 7. ProductCategories 테이블 생성
CREATE TABLE "BASIC"."PRODUCTCATEGORIES" 
(
    "CATEGORY_ID" INT PRIMARY KEY,  -- 상품 카테고리 ID
    "CATEGORY_NAME" VARCHAR(255) NOT NULL  -- 카테고리 이름
);

-- 8. Products 테이블 생성
CREATE TABLE "BASIC"."PRODUCTS" 
(
    "PRODUCT_ID" INT PRIMARY KEY,  -- 상품 ID
    "PRODUCT_NAME" VARCHAR2(255) NOT NULL,  -- 상품 이름
    "CATEGORY_ID" INT,  -- 상품 카테고리
    "PRICE" DECIMAL(10,2) NOT NULL,  -- 가격
    FOREIGN KEY ("CATEGORY_ID") REFERENCES "BASIC"."PRODUCTCATEGORIES" ("CATEGORY_ID")
);

-- 9. ProductIngredients 테이블 생성
CREATE TABLE "BASIC"."PRODUCTINGREDIENTS" 
(
    "PRODUCT_ID" INT,  -- 상품 ID
    "INGREDIENT_ID" INT,  -- 재료 ID
    "QUANTITY" INT NOT NULL,  -- 재료 수량
    PRIMARY KEY ("PRODUCT_ID", "INGREDIENT_ID"),
    FOREIGN KEY ("PRODUCT_ID") REFERENCES "BASIC"."PRODUCTS" ("PRODUCT_ID"),
    FOREIGN KEY ("INGREDIENT_ID") REFERENCES "BASIC"."INGREDIENTS" ("INGREDIENT_ID")
);

-- 10. Sales 테이블 생성
CREATE TABLE "BASIC"."SALES" 
(
    "SALE_ID" INT PRIMARY KEY,  -- 판매 ID
    "SALE_DATE" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 판매 일시
    "PRODUCT_ID" INT,  -- 상품 ID
    "QUANTITY" INT NOT NULL,  -- 판매 수량
    "TOTAL_PRICE" DECIMAL(10,2) NOT NULL,  -- 총 판매 금액
    FOREIGN KEY ("PRODUCT_ID") REFERENCES "BASIC"."PRODUCTS" ("PRODUCT_ID")
);

-- 11. SalesIngredients 테이블 생성
CREATE TABLE "BASIC"."SALESINGREDIENTS" 
(
    "SALE_ID" INT,
    "PRODUCT_ID" INT,
    "INGREDIENT_ID" INT,
    "QUANTITY" INT NOT NULL,
    PRIMARY KEY ("SALE_ID", "PRODUCT_ID", "INGREDIENT_ID"),
    FOREIGN KEY ("SALE_ID") REFERENCES "BASIC"."SALES" ("SALE_ID"),
    FOREIGN KEY ("PRODUCT_ID") REFERENCES "BASIC"."PRODUCTS" ("PRODUCT_ID"),
    FOREIGN KEY ("INGREDIENT_ID") REFERENCES "BASIC"."INGREDIENTS" ("INGREDIENT_ID")
);

-- 12. Orders 테이블 생성
CREATE TABLE "BASIC"."ORDERS" 
(
    "ORDER_ID" INT PRIMARY KEY,  -- 주문 ID
    "ORDER_DATE" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 주문 일시
    "SUPPLIER" VARCHAR2(255),  -- 공급자
    "INGREDIENT_ID" INT,  -- 재료 ID
    "QUANTITY" INT NOT NULL,  -- 주문 수량
    "TOTAL_PRICE" DECIMAL(10,2) NOT NULL,  -- 총 주문 금액
    FOREIGN KEY ("INGREDIENT_ID") REFERENCES "BASIC"."INGREDIENTS" ("INGREDIENT_ID")
);

-- 13. SalaryReport 테이블 생성
CREATE TABLE "BASIC"."SALARYREPORT" 
(
    "REPORT_ID" INT PRIMARY KEY,  -- 급여 보고서 ID
    "REPORT_DATE" DATE,  -- 보고서 작성일
    "START_DATE" DATE,  -- 시작일
    "END_DATE" DATE,  -- 종료일
    "TOTAL_EMPLOYEES" INT,  -- 총 직원 수
    "TOTAL_SALARY" DECIMAL(10,2)  -- 총 급여
);

-- 14. SalaryPaymentDetails 테이블 생성
CREATE TABLE "BASIC"."SALARYPAYMENTDETAILS" 
(
    "PAYMENT_ID" INT PRIMARY KEY,  -- 급여 지급 ID
    "EMPLOYEE_ID" INT,  -- 직원 ID
    "PAYMENT_DATE" DATE,  -- 급여 지급일
    "AMOUNT" DECIMAL(10,2),  -- 지급된 급여 금액
    "REPORT_ID" INT,  -- 급여 보고서 ID
    FOREIGN KEY ("EMPLOYEE_ID") REFERENCES "BASIC"."EMPLOYEES" ("EID"),
    FOREIGN KEY ("REPORT_ID") REFERENCES "BASIC"."SALARYREPORT" ("REPORT_ID")
);

-- 15. SalesReport 테이블 생성
CREATE TABLE "BASIC"."SALESREPORT" 
(
    "REPORT_ID" INT PRIMARY KEY,  -- 매출 보고서 ID
    "REPORT_DATE" DATE,  -- 보고서 작성일
    "START_DATE" DATE,  -- 시작일
    "END_DATE" DATE,  -- 종료일
    "TOTAL_PRODUCTS" INT,  -- 판매된 상품의 고유 종류 수
    "TOTAL_QUANTITY" INT,  -- 판매된 총 수량
    "TOTAL_REVENUE" DECIMAL(10,2)  -- 총 매출 금액
);

-- 16. OrderReport 테이블 생성
CREATE TABLE "BASIC"."ORDERREPORT" 
(
    "REPORT_ID" INT PRIMARY KEY,  -- 주문 보고서 ID
    "REPORT_DATE" DATE,  -- 보고서 작성일
    "START_DATE" DATE,  -- 시작일
    "END_DATE" DATE,  -- 종료일
    "TOTAL_INGREDIENTS" INT,  -- 주문된 재료의 고유 종류 수
    "TOTAL_QUANTITY" INT,  -- 주문된 총 수량
    "TOTAL_AMOUNT" DECIMAL(10,2)  -- 총 주문 금액
);

-- 17. ProfitLossStatement 테이블 생성
CREATE TABLE "BASIC"."PROFITLOSSSTATEMENT" 
(
    "REPORT_ID" INT PRIMARY KEY,  -- 손익 계산서 ID
    "REPORT_DATE" DATE,  -- 보고서 작성일
    "TOTAL_REVENUE" DECIMAL(10,2),  -- 총 매출
    "TOTAL_EXPENSES" DECIMAL(10,2),  -- 총 비용
    "NET_PROFIT" DECIMAL(10,2)  -- 순이익
);

-- 18. 트리거 생성: 판매 후 재고 업데이트
CREATE OR REPLACE TRIGGER basic.TRG_UPDATE_STOCK_AFTER_SALE
AFTER INSERT ON "BASIC"."SALES"
FOR EACH ROW
DECLARE
    v_product_id NUMBER := :NEW.PRODUCT_ID;
    v_sale_quantity NUMBER := :NEW.QUANTITY;
BEGIN
    -- 재고 업데이트
    FOR ingredient_rec IN (
        SELECT pi.INGREDIENT_ID, 
               pi.QUANTITY * :NEW.QUANTITY AS required_quantity
        FROM "BASIC"."PRODUCTINGREDIENTS" pi
        WHERE pi.PRODUCT_ID = v_product_id
    ) LOOP
        UPDATE "BASIC"."INGREDIENTS"
        SET STOCK = STOCK - ingredient_rec.required_quantity
        WHERE INGREDIENT_ID = ingredient_rec.INGREDIENT_ID
        AND STOCK >= ingredient_rec.required_quantity;
        
        -- 재고 부족 처리
        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(-20001, '재고 부족: INGREDIENT_ID = ' || ingredient_rec.INGREDIENT_ID);
        END IF;
    END LOOP;
    
    -- 판매된 재료 정보 기록
    INSERT INTO "BASIC"."SALESINGREDIENTS" 
    (
        "SALE_ID", 
        "PRODUCT_ID", 
        "INGREDIENT_ID", 
        "QUANTITY"
    )
    SELECT 
        :NEW.SALE_ID, 
        :NEW.PRODUCT_ID, 
        pi.INGREDIENT_ID, 
        pi.QUANTITY * :NEW.QUANTITY
    FROM "BASIC"."PRODUCTINGREDIENTS" pi
    WHERE pi.PRODUCT_ID = v_product_id;
    
EXCEPTION
    WHEN OTHERS THEN
        -- 예외 발생 시 처리
        RAISE;
END;
/

-- 기본 데이터 삽입
-- 1. Rank 테이블 데이터 삽입
INSERT INTO basic.RANK ("RID", "RankName", "Description") 
VALUES (seq_rank_id.NEXTVAL, '크루', '기본 직원');

INSERT INTO basic.RANK ("RID", "RankName", "Description") 
VALUES (seq_rank_id.NEXTVAL, '매니저', '중간 관리자');

INSERT INTO basic.RANK ("RID", "RankName", "Description") 
VALUES (seq_rank_id.NEXTVAL, '점장', '최고 관리자');

-- 2. IngredientCategories 테이블 데이터 삽입
-- IngredientCategories 테이블에 재료 카테고리 데이터를 삽입.
INSERT INTO basic.INGREDIENTCATEGORIES ("CATEGORY_ID", "CATEGORY_NAME") 
VALUES (1, 'Liquid');

INSERT INTO basic.INGREDIENTCATEGORIES ("CATEGORY_ID", "CATEGORY_NAME") 
VALUES (2, 'Solid');

INSERT INTO basic.INGREDIENTCATEGORIES ("CATEGORY_ID", "CATEGORY_NAME") 
VALUES (3, 'Powder');

INSERT INTO basic.INGREDIENTCATEGORIES ("CATEGORY_ID", "CATEGORY_NAME") 
VALUES (4, 'Fresh Produce');


-- 3. Ingredients 테이블 데이터 삽입
-- Ingredients 테이블에 재료 정보를 삽입합니다. 이 때 **CATEGORY_ID**는 IngredientCategories 테이블의 외래 키.
INSERT INTO basic.INGREDIENTS ("INGREDIENT_ID", "INGREDIENT_NAME", "CATEGORY_ID", "STOCK", "UNIT_PRICE") 
VALUES (seq_ingredient_id.NEXTVAL, 'Water', 1, 5000, 0.10);

INSERT INTO basic.INGREDIENTS ("INGREDIENT_ID", "INGREDIENT_NAME", "CATEGORY_ID", "STOCK", "UNIT_PRICE") 
VALUES (seq_ingredient_id.NEXTVAL, 'Sugar', 2, 2000, 0.20);

INSERT INTO basic.INGREDIENTS ("INGREDIENT_ID", "INGREDIENT_NAME", "CATEGORY_ID", "STOCK", "UNIT_PRICE") 
VALUES (seq_ingredient_id.NEXTVAL, 'Coffee Beans', 2, 1000, 0.50);

INSERT INTO basic.INGREDIENTS ("INGREDIENT_ID", "INGREDIENT_NAME", "CATEGORY_ID", "STOCK", "UNIT_PRICE") 
VALUES (seq_ingredient_id.NEXTVAL, 'Milk', 1, 3000, 0.30);

INSERT INTO basic.INGREDIENTS ("INGREDIENT_ID", "INGREDIENT_NAME", "CATEGORY_ID", "STOCK", "UNIT_PRICE") 
VALUES (seq_ingredient_id.NEXTVAL, 'Cocoa Powder', 3, 800, 0.40);


-- 4. Products 테이블 데이터 삽입
-- Products 테이블에 제품 정보를 삽입합니다. **CATEGORY_ID**는 ProductCategories 테이블의 외래 키.

-- ProductCategories 테이블 데이터 삽입
INSERT INTO basic.PRODUCTCATEGORIES ("CATEGORY_ID", "CATEGORY_NAME") 
VALUES (1, 'Coffee');

INSERT INTO basic.PRODUCTCATEGORIES ("CATEGORY_ID", "CATEGORY_NAME") 
VALUES (2, 'Tea');

INSERT INTO basic.PRODUCTCATEGORIES ("CATEGORY_ID", "CATEGORY_NAME") 
VALUES (3, 'Dessert');

INSERT INTO basic.PRODUCTCATEGORIES ("CATEGORY_ID", "CATEGORY_NAME") 
VALUES (4, 'Beverage');

-- Products 테이블 데이터 삽입
INSERT INTO basic.PRODUCTS ("PRODUCT_ID", "PRODUCT_NAME", "CATEGORY_ID", "PRICE") 
VALUES (seq_product_id.NEXTVAL, 'Americano', 1, 3500);

INSERT INTO basic.PRODUCTS ("PRODUCT_ID", "PRODUCT_NAME", "CATEGORY_ID", "PRICE") 
VALUES (seq_product_id.NEXTVAL, 'Latte', 1, 4000);

INSERT INTO basic.PRODUCTS ("PRODUCT_ID", "PRODUCT_NAME", "CATEGORY_ID", "PRICE") 
VALUES (seq_product_id.NEXTVAL, 'Green Tea', 2, 3000);

INSERT INTO basic.PRODUCTS ("PRODUCT_ID", "PRODUCT_NAME", "CATEGORY_ID", "PRICE") 
VALUES (seq_product_id.NEXTVAL, 'Chocolate Cake', 3, 6000);

INSERT INTO basic.PRODUCTS ("PRODUCT_ID", "PRODUCT_NAME", "CATEGORY_ID", "PRICE") 
VALUES (seq_product_id.NEXTVAL, 'Orange Juice', 4, 4500);


-- 5. ProductIngredients 테이블 데이터 삽입
-- ProductIngredients 테이블에 각 제품에 필요한 재료의 관계를 삽입.
INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (1, 1, 200); -- Americano: Water

INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (1, 3, 20); -- Americano: Coffee Beans

INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (2, 1, 150); -- Latte: Water

INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (2, 3, 15); -- Latte: Coffee Beans

INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (2, 4, 100); -- Latte: Milk

INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (3, 1, 250); -- Green Tea: Water

INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (4, 5, 50); -- Chocolate Cake: Cocoa Powder

INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (4, 2, 100); -- Chocolate Cake: Sugar

INSERT INTO basic.PRODUCTINGREDIENTS ("PRODUCT_ID", "INGREDIENT_ID", "QUANTITY") 
VALUES (5, 1, 300); -- Orange Juice: Water


-- 6. SALARYREPORT 테이블 데이터 삽입
-- 급여 보고서 SALARYREPORT 테이블에 데이터를 삽입.
INSERT INTO basic.SALARYREPORT ("REPORT_ID", "REPORT_DATE", "START_DATE", "END_DATE", "TOTAL_EMPLOYEES", "TOTAL_SALARY") 
VALUES (seq_report_id.NEXTVAL, SYSDATE, ADD_MONTHS(SYSDATE, -1), SYSDATE, 3, 90000.00);

-- 7. SALARYPAYMENTDETAILS 테이블 데이터 삽입
-- SALARYPAYMENTDETAILS 테이블에 급여 지급 내역을 삽입합니다. REPORT_ID는 SALARYREPORT에서 삽입된 가장 최신 REPORT_ID를 참조.
INSERT INTO basic.SALARYPAYMENTDETAILS ("PAYMENT_ID", "EMPLOYEE_ID", "PAYMENT_DATE", "AMOUNT", "REPORT_ID") 
VALUES (seq_payment_id.NEXTVAL, 1, SYSDATE, 30000, (SELECT MAX("REPORT_ID") FROM basic.SALARYREPORT));

INSERT INTO basic.SALARYPAYMENTDETAILS ("PAYMENT_ID", "EMPLOYEE_ID", "PAYMENT_DATE", "AMOUNT", "REPORT_ID") 
VALUES (seq_payment_id.NEXTVAL, 2, SYSDATE, 40000, (SELECT MAX("REPORT_ID") FROM basic.SALARYREPORT));

INSERT INTO basic.SALARYPAYMENTDETAILS ("PAYMENT_ID", "EMPLOYEE_ID", "PAYMENT_DATE", "AMOUNT", "REPORT_ID") 
VALUES (seq_payment_id.NEXTVAL, 3, SYSDATE, 50000, (SELECT MAX("REPORT_ID") FROM basic.SALARYREPORT));

-- 테스트 쿼리:
-- SALARYREPORT 테이블 확인
SELECT * FROM basic.SALARYREPORT;

-- SALARYPAYMENTDETAILS 테이블 확인
SELECT * FROM basic.SALARYPAYMENTDETAILS;

-- EMPLOYEES 테이블과 SALARYPAYMENTDETAILS 테이블의 결합 조회
SELECT e."Name", e."ID", spd."AMOUNT", spd."PAYMENT_DATE"
FROM basic.EMPLOYEES e
JOIN basic.SALARYPAYMENTDETAILS spd 
ON e."EID" = spd."EMPLOYEE_ID";



