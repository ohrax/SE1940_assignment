CREATE DATABASE LeaveManagementDB;
GO

USE LeaveManagementDB;
GO

CREATE TABLE divisions (
    division_id INT IDENTITY(1,1) PRIMARY KEY,
    division_name NVARCHAR(100) NOT NULL
);

CREATE TABLE roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(50) NOT NULL
);

CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL,
    password NVARCHAR(50) NOT NULL,
	full_name NVARCHAR(100) NOT NULL,
    division_id INT,
    manager_id INT,
	created_date DATE DEFAULT GETDATE(),
    FOREIGN KEY (division_id) REFERENCES divisions(division_id),
    FOREIGN KEY (manager_id) REFERENCES users(user_id)
);

CREATE TABLE user_roles (
    user_id INT,
    role_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE leave_requests (
    request_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    title NVARCHAR(255) NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    reason NVARCHAR(MAX),
    status NVARCHAR(20) DEFAULT 'Inprogress',
    processed_by INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (processed_by) REFERENCES users(user_id)
);

-- Sample data
INSERT INTO divisions (division_name) VALUES ('IT'), ('HR'), ('Sales');
INSERT INTO roles (role_name) VALUES ('employee'), ('leader'), ('department_manager'), ('admin');
INSERT INTO users (username, password, full_name, division_id, manager_id, created_date) VALUES 
    ('emp1', '123', 'Nguyen Van A', 1, 2, '2025-02-01'),
    ('leader1', '123', 'Tran Thi B', 1, 4, '2025-01-15'),
    ('mgr1', '123', 'Bui Van C', 1, 4, '2025-02-27'),
    ('admin1', '123', 'Nguyen Viet Boss', 1, NULL, '2025-03-01');
INSERT INTO users (username, password, full_name, division_id, manager_id) VALUES
	('emp2','123', 'Ngo Van D', 1, 2),
	('emp3','123', 'Dao Quang E', 1, 7),
	('leader2','123', 'Do Hong G', 1, 4);
INSERT INTO user_roles (user_id, role_id) VALUES 
    (1, 1), (2, 2), (3, 3), (4, 4);