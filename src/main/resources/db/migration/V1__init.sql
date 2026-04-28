CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =====================================================
-- COMMON BASE (OPTIONAL: audit columns template idea)
-- =====================================================

-- =====================================================
-- ORGANIZATION DOMAIN
-- =====================================================

CREATE TABLE departments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) UNIQUE NOT NULL,
    parent_id UUID NULL,
    manager_id UUID NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_department_parent FOREIGN KEY (parent_id) REFERENCES departments(id)
);

CREATE INDEX idx_departments_parent_id ON departments(parent_id);

-- =====================================================

CREATE TABLE job_positions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    code VARCHAR(100) UNIQUE NOT NULL,
    level VARCHAR(50),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

-- =====================================================

CREATE TABLE locations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255),
    address TEXT,
    country VARCHAR(100),
    city VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

-- =====================================================
-- EMPLOYEE DOMAIN
-- =====================================================

CREATE TABLE employees (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_code VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(50),
    date_of_birth DATE,
    gender VARCHAR(20),
    marital_status VARCHAR(50),
    hire_date DATE,
    status VARCHAR(50),
    department_id UUID,
    position_id UUID,
    location_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES departments(id),
    CONSTRAINT fk_employee_position FOREIGN KEY (position_id) REFERENCES job_positions(id),
    CONSTRAINT fk_employee_location FOREIGN KEY (location_id) REFERENCES locations(id)
);

CREATE INDEX idx_employee_department ON employees(department_id);

-- =====================================================

CREATE TABLE employee_addresses (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    address TEXT,
    type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_address_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- =====================================================

CREATE TABLE employee_documents (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    document_type VARCHAR(50),
    document_number VARCHAR(100),
    file_url TEXT,
    issued_date DATE,
    expiry_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_document_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- =====================================================

CREATE TABLE employee_emergency_contacts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    name VARCHAR(255),
    relationship VARCHAR(100),
    phone VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_emergency_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- =====================================================
-- EMPLOYMENT DOMAIN
-- =====================================================

CREATE TABLE employment_contracts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    contract_type VARCHAR(50),
    start_date DATE,
    end_date DATE,
    salary NUMERIC(15,2),
    currency VARCHAR(10),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- =====================================================

CREATE TABLE salary_histories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    base_salary NUMERIC(15,2),
    effective_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_salary_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- =====================================================
-- ATTENDANCE DOMAIN
-- =====================================================

CREATE TABLE work_shifts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100),
    start_time TIME,
    end_time TIME
);

-- =====================================================

CREATE TABLE attendance_records (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    work_date DATE NOT NULL,
    check_in TIMESTAMP,
    check_out TIMESTAMP,
    status VARCHAR(50),
    shift_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT fk_attendance_shift FOREIGN KEY (shift_id) REFERENCES work_shifts(id)
);

CREATE INDEX idx_attendance_employee_date
ON attendance_records(employee_id, work_date);

-- =====================================================
-- LEAVE DOMAIN
-- =====================================================

CREATE TABLE leave_types (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100),
    days_allowed INT
);

-- =====================================================

CREATE TABLE leave_balances (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    leave_type_id UUID NOT NULL,
    remaining_days NUMERIC(5,2),
    CONSTRAINT fk_balance_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT fk_balance_leave_type FOREIGN KEY (leave_type_id) REFERENCES leave_types(id)
);

-- =====================================================

CREATE TABLE leave_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    leave_type_id UUID NOT NULL,
    start_date DATE,
    end_date DATE,
    reason TEXT,
    status VARCHAR(50),
    approved_by UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_leave_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT fk_leave_type FOREIGN KEY (leave_type_id) REFERENCES leave_types(id)
);

-- =====================================================
-- PAYROLL DOMAIN
-- =====================================================

CREATE TABLE payrolls (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    month INT,
    year INT,
    base_salary NUMERIC(15,2),
    total_allowance NUMERIC(15,2),
    total_deduction NUMERIC(15,2),
    net_salary NUMERIC(15,2),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payroll_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE INDEX idx_payroll_employee_period
ON payrolls(employee_id, month, year);

-- =====================================================

CREATE TABLE payroll_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    payroll_id UUID NOT NULL,
    type VARCHAR(50),
    name VARCHAR(255),
    amount NUMERIC(15,2),
    CONSTRAINT fk_payroll_item FOREIGN KEY (payroll_id) REFERENCES payrolls(id)
);

-- =====================================================
-- RECRUITMENT DOMAIN
-- =====================================================

CREATE TABLE job_postings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    position_id UUID,
    department_id UUID,
    description TEXT,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_position FOREIGN KEY (position_id) REFERENCES job_positions(id),
    CONSTRAINT fk_job_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- =====================================================

CREATE TABLE candidates (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    resume_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================

CREATE TABLE applications (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    candidate_id UUID,
    job_posting_id UUID,
    status VARCHAR(50),
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_candidate FOREIGN KEY (candidate_id) REFERENCES candidates(id),
    CONSTRAINT fk_app_job FOREIGN KEY (job_posting_id) REFERENCES job_postings(id)
);

-- =====================================================
-- PERFORMANCE DOMAIN
-- =====================================================

CREATE TABLE performance_reviews (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID,
    reviewer_id UUID,
    period VARCHAR(50),
    score NUMERIC(5,2),
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- =====================================================
-- AUTH DOMAIN
-- =====================================================

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID,
    username VARCHAR(100) UNIQUE,
    password_hash TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- =====================================================

create table if not exists role (
  id          uuid primary key default gen_random_uuid(),
  code        text not null unique,  -- ADMIN, USER...
  name        text not null,
  description text
);

-- =====================================================

create table if not exists permissionModel (
  id          uuid primary key default gen_random_uuid(),
  code        text not null unique,  -- user:read, post:write...
  name        text,
  description text
);

-- =====================================================

create table if not exists user_role (
  user_id     uuid not null references users(id) on delete cascade,
  role_id     uuid not null references role(id) on delete cascade,
  primary key (user_id, role_id)
);

-- =====================================================

create table if not exists role_permission (
  role_id        uuid not null references role(id) on delete cascade,
  permission_id  uuid not null references permissionModel(id) on delete cascade,
  primary key (role_id, permission_id)
);

-- =====================================================
-- AUDIT LOG
-- =====================================================

CREATE TABLE audit_logs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID,
    action VARCHAR(100),
    entity_type VARCHAR(100),
    entity_id UUID,
    old_value JSONB,
    new_value JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_entity
ON audit_logs(entity_type, entity_id);

-- =====================================================
-- END OF SCHEMA
-- =====================================================
