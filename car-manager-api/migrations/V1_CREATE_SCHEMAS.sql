CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    photo_path VARCHAR(255),
    email VARCHAR(50) NOT NULL UNIQUE,
    login VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    birth_day DATE NOT NULL,
    last_login DATETIME,
    created_at DATETIME NOT NULL
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS cars (
    id BIGINT NOT NULL AUTO_INCREMENT,
    car_year INT NOT NULL,
    license_plate VARCHAR(7) NOT NULL UNIQUE,
    model VARCHAR(20) NOT NULL,
    color VARCHAR(20) NOT NULL,
    photo_path VARCHAR(255),
    user_id INT NOT NULL
    PRIMARY KEY(id)
    FOREIGN KEY(user_id) REFERENCES users(id)
);