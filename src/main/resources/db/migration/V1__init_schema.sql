-- Initial Database Migration Schema for EduTrack

CREATE TABLE IF NOT EXISTS Students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    student_id VARCHAR(100),
    dob DATE,
    course VARCHAR(255),
    year_of_study VARCHAR(50),
    gender VARCHAR(50),
    status VARCHAR(50),
    address TEXT,
    notes TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS Courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(255),
    course_code VARCHAR(100),
    instructor VARCHAR(255),
    credits INT DEFAULT 0,
    enrolled INT DEFAULT 0,
    capacity INT DEFAULT 0,
    status VARCHAR(50),
    department VARCHAR(255),
    description TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS Attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_db_id INT,
    student_id VARCHAR(100),
    student_name VARCHAR(255),
    course_code VARCHAR(100),
    course_name VARCHAR(255),
    date DATE,
    start_time VARCHAR(50),
    end_time VARCHAR(50),
    status VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
