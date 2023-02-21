CREATE TABLE IF NOT EXISTS `users` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20),
    `email` varchar(50),
    `password` varchar(20),
    `enabled` boolean
    );

INSERT INTO users (name, email, password, enabled) values ("Vasyl", "bazelik777@gmail.com",
 "asd", 1);