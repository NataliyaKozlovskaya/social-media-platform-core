CREATE TABLE User (
id INTEGER PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50) NOT NULL,
password VARCHAR(200) NOT NULL,
email VARCHAR(50) NOT NULL
);


CREATE TABLE Message(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    from_user_id INTEGER,
    to_user_id INTEGER,
    content VARCHAR(200),
    CONSTRAINT FOREIGN KEY (from_user_id) REFERENCES User(id),
    CONSTRAINT FOREIGN KEY (to_user_id) REFERENCES User(id)
);

CREATE TABLE Post(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(30),
    content VARCHAR(200),
    time DATETIME,
    updatedTime DATETIME,
    user_id  INTEGER,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES User(id)
);
ALTER TABLE Post add column updated_time DATETIME;
ALTER TABLE Post drop column updatedTime;


CREATE TABLE Picture(
        id INTEGER PRIMARY KEY AUTO_INCREMENT,
        post_id INTEGER,
        icon BLOB,
        CONSTRAINT FOREIGN KEY (post_id) REFERENCES Post(id));


CREATE TABLE Friendship(
                           id INTEGER PRIMARY KEY AUTO_INCREMENT,
                           user_id INTEGER,
                           friend_id INTEGER,
                           subscription BOOLEAN,
                           friendship BOOLEAN,
                           correspondence BOOLEAN,
                           CONSTRAINT FOREIGN KEY (user_id) REFERENCES User(id),
                           CONSTRAINT FOREIGN KEY (friend_id) REFERENCES User(id),
                           CONSTRAINT UNIQUE (user_id, friend_id));
