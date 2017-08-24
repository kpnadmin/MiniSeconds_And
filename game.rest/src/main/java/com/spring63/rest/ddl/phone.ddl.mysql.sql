
-- create a table TB_PHONE
DROP TABLE IF EXISTS TB_PHONE;
CREATE TABLE TB_PHONE (
      name          NVARCHAR(20)    NOT NULL 
    , manufacturer  NVARCHAR(40)    NOT NULL 
    , price         INTEGER         DEFAULT 0 NOT NULL 
);

select * from TB_Phone;




-- create a table TB_PERSON
DROP TABLE IF EXISTS TB_PERSON;
CREATE TABLE TB_PERSON (
      id        NVARCHAR(20)    NOT NULL
    , pw        NVARCHAR(40)    NOT NULL
    , name      NVARCHAR(40)    NOT NULL
    , email     NVARCHAR(40)    NOT NULL
);

select * from TB_PERSON;


commit;
