CREATE DATABASE IF NOT EXISTS hospital_app_db;
USE hospital_app_db;

CREATE TABLE patient
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    surname      VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    national_id  CHAR(11) NOT NULL UNIQUE,
    birth_date   DATE,
    is_deleted   TINYINT(1) NOT NULL DEFAULT 0,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by   VARCHAR(255),
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by   VARCHAR(255),
    phone_number CHAR(10) NOT NULL UNIQUE,
    role         VARCHAR(50) NOT NULL
);

CREATE TABLE doctor
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name                VARCHAR(255) NOT NULL,
    surname             VARCHAR(255) NOT NULL,
    email               VARCHAR(255) NOT NULL UNIQUE,
    password            VARCHAR(255) NOT NULL,
    registration_number CHAR(8) NOT NULL UNIQUE,
    department          VARCHAR(255) NOT NULL,
    proficiency         VARCHAR(255),
    is_deleted          TINYINT(1) NOT NULL DEFAULT 0,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by          VARCHAR(255),
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by          VARCHAR(255),
    phone_number        CHAR(10) NOT NULL UNIQUE,
    role                VARCHAR(50) NOT NULL
);

CREATE TABLE patient_doctor
(
    patient_id  BIGINT NOT NULL,
    doctor_id   BIGINT NOT NULL,
    PRIMARY KEY (patient_id, doctor_id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patient (id) ON DELETE CASCADE,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (id) ON DELETE CASCADE
);
