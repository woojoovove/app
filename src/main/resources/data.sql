DROP TABLE if exists USERS;
DROP TABLE if exists GROUPS;
CREATE TABLE IF NOT EXISTS `USERS` (
                         `ID`	number 	generated always as identity primary key,
                         `NICKNAME`	nvarchar2	NOT NULL,
                         `TOKEN`	nvarchar2	NOT NULL
);

CREATE TABLE IF NOT EXISTS `GROUPS` (
                          `ID` number generated always as identity primary key ,
                          `NAME`	nvarchar2  NOT NULL unique ,
                          `USER_ID`	number	NULL
);

