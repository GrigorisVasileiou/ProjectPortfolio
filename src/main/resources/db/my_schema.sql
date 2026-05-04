CREATE TABLE users (id bigint not null auto_increment, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
CREATE TABLE projects (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), user_id BIGINT, FOREIGN KEY (user_id) REFERENCES users(id));
CREATE TABLE use_cases (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255),
                           actors VARCHAR(255),
                           preconditions VARCHAR(255),
                           main_flow TEXT,
                           postconditions VARCHAR(255),
                           project_id BIGINT,
                           FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE CRC_Cards (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           class_name VARCHAR(255),
                           responsibilities TEXT,
                           collaborators TEXT,
                           project_id BIGINT,
                           FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE crc_usecase (
                             crc_id BIGINT NOT NULL,
                             usecase_id BIGINT NOT NULL,
                             PRIMARY KEY (crc_id, usecase_id),
                             FOREIGN KEY (crc_id) REFERENCES CRC_Cards(id),
                             FOREIGN KEY (usecase_id) REFERENCES use_cases(id)
);