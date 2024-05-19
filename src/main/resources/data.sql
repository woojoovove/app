DROP TABLE if exists USER_GROUP;
DROP TABLE if exists USERS;
DROP TABLE if exists GROUPS;
CREATE TABLE IF NOT EXISTS `USERS` (
                         `ID`	number 	generated always as identity primary key,
                         `NICKNAME`	nvarchar2	NOT NULL,
                         `TOKEN`	nvarchar2	NOT NULL
);

CREATE TABLE IF NOT EXISTS `GROUPS` (
                          `ID` number generated always as identity primary key ,
                          `NAME`	nvarchar2  NOT NULL unique
);

CREATE TABLE IF NOT EXISTS `USER_GROUP` (
                                        `ID` number generated always as identity primary key ,
                                        `USER_ID`	number  NOT NULL references USERS(ID),
                                        `GROUP_ID` number NOT NULL references GROUPS(ID)
);

INSERT INTO USERS (nickname, token) VALUES ( 'user1', 'token1' );
INSERT INTO USERS (nickname, token) VALUES ( 'user2', 'token2' );
INSERT INTO USERS (nickname, token) VALUES ( 'user3', 'token3' );
INSERT INTO USERS (nickname, token) VALUES ( 'user4', 'token4' );
INSERT INTO USERS (nickname, token) VALUES ( 'user5', 'token5' );
INSERT INTO USERS (nickname, token) VALUES ( 'user6', 'token6' );
INSERT INTO USERS (nickname, token) VALUES ( 'user7', 'token7' );
INSERT INTO USERS (nickname, token) VALUES ( 'user8', 'token8' );
INSERT INTO USERS (nickname, token) VALUES ( 'user9', 'token9' );
INSERT INTO USERS (nickname, token) VALUES ( 'user10', 'token10' );

INSERT INTO GROUPS (name) VALUES ( 'group1' );
INSERT INTO GROUPS (name) VALUES ( 'group2' );
INSERT INTO GROUPS (name) VALUES ( 'group3' );
INSERT INTO GROUPS (name) VALUES ( 'group4' );
INSERT INTO GROUPS (name) VALUES ( 'group5' );

INSERT INTO USERS (nickname, token) VALUES ( 'user11', 'token11' );
INSERT INTO USERS (nickname, token) VALUES ( 'user12', 'token12' );
INSERT INTO USERS (nickname, token) VALUES ( 'user13', 'token13' );
INSERT INTO USERS (nickname, token) VALUES ( 'user14', 'token14' );
INSERT INTO USERS (nickname, token) VALUES ( 'user15', 'token15' );
INSERT INTO USERS (nickname, token) VALUES ( 'user16', 'token16' );
INSERT INTO USERS (nickname, token) VALUES ( 'user17', 'token17' );
INSERT INTO USERS (nickname, token) VALUES ( 'user18', 'token18' );
INSERT INTO USERS (nickname, token) VALUES ( 'user19', 'token19' );
INSERT INTO USERS (nickname, token) VALUES ( 'user20', 'token20' );

INSERT INTO USER_GROUP (user_id, group_id) VALUES ( '11', '1' );
INSERT INTO USER_GROUP (user_ID, group_id) VALUES ( '12', '2' );
INSERT INTO USER_GROUP (user_id, group_id) VALUES ( '13', '1' );
INSERT INTO USER_GROUP (user_ID, group_id) VALUES ( '14', '2' );
INSERT INTO USER_GROUP (user_id, group_id) VALUES ( '15', '1' );
INSERT INTO USER_GROUP (user_ID, group_id) VALUES ( '16', '2' );
INSERT INTO USER_GROUP (user_id, group_id) VALUES ( '17', '1' );
INSERT INTO USER_GROUP (user_ID, group_id) VALUES ( '18', '2' );
INSERT INTO USER_GROUP (user_id, group_id) VALUES ( '19', '1' );
INSERT INTO USER_GROUP (user_ID, group_id) VALUES ( '20', '2' );
