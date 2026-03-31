create table users (id bigint not null auto_increment, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
create table users (id bigint not null auto_increment, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
create table users (id bigint not null auto_increment, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
CREATE TABLE projects (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          description VARCHAR(255),
                          user_id BIGINT,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);