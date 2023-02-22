CREATE TABLE IF NOT EXISTS `users` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`  varchar(255) UNIQUE,
    `email`  varchar(255) UNIQUE,
    `password` varchar(255)
    );

INSERT INTO users (username, email, password) values ("Vasyl", "vasyl@test.com",
 "$2a$10$8LtFPpMIaGm7HNO9FSHAHOoCMvl9L8FVwRdHgdTesrJ4GS..lgdV2");

CREATE TABLE IF NOT EXISTS `avatar`(
    `user_id` int PRIMARY KEY,
    `file_name` varchar(255) NOT NULL,
    `content` BLOB(2048000) NOT NULL
);
ALTER TABLE avatar ADD FOREIGN KEY (user_id) REFERENCES users(id);

CREATE TABLE IF NOT EXISTS `user_role`(
`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`role` varchar(50),
`user_id` int NOT NULL,
 FOREIGN KEY(user_id) references users(id)
);
 insert into user_role (role, user_id) values ("ADMIN", 1);

