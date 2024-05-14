DROP TABLE if exists USERS;
DROP TABLE if exists GROUPS;
CREATE TABLE IF NOT EXISTS `USERS` (
                         `ID`	number 	generated always as identity primary key,
                         `NICKNAME`	nvarchar2	NOT NULL,
                         `TOKEN`	nvarchar2	NOT NULL,
                         `GROUP_ID` number NULL
);

CREATE TABLE IF NOT EXISTS `GROUPS` (
                          `ID` number generated always as identity primary key ,
                          `NAME`	nvarchar2  NOT NULL unique
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

INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user11', 'token11' , 1);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user12', 'token12' , 2);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user13', 'token13' , 1);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user14', 'token14' , 2);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user15', 'token15' , 1);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user16', 'token16' , 2);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user17', 'token17' , 1);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user18', 'token18' , 2);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user19', 'token19' , 1);
INSERT INTO USERS (nickname, token, group_id) VALUES ( 'user20', 'token20' , 2);
