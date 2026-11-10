INSERT INTO students (student_id, student_name, age, email, phone_number, department, student_year, admission_date, marks, status) VALUES
(1,'Aarav Kumar',20,'aarav@example.com','9000000001','CSE',2,'2024-06-10',88.50,'ACTIVE'),
(2,'Ananya Rao',21,'ananya@example.com','9000000002','ECE',3,'2023-06-12',91.00,'ACTIVE'),
(3,'Rahul Reddy',19,'rahul@example.com','9000000003','CSE',1,'2025-06-15',76.00,'ACTIVE'),
(4,'Priya Sharma',22,'priya@example.com','9000000004','IT',4,'2022-06-10',95.25,'ACTIVE'),
(5,'Vikram Singh',20,'vikram@example.com','9000000005','MECH',2,'2024-06-11',68.00,'ACTIVE'),
(6,'Sneha Patel',21,'sneha@example.com','9000000006','CSE',3,'2023-06-10',84.75,'ACTIVE'),
(7,'Kiran Kumar',20,'kiran@example.com','9000000007','ECE',2,'2024-06-14',73.50,'ACTIVE'),
(8,'Divya Rani',22,'divya@example.com','9000000008','IT',4,'2022-06-13',89.00,'GRADUATED'),
(9,'Arjun Das',19,'arjun@example.com','9000000009','CSE',1,'2025-06-16',81.25,'ACTIVE'),
(10,'Meera Nair',21,'meera@example.com','9000000010','CIVIL',3,'2023-06-17',78.50,'ACTIVE');

INSERT INTO courses (course_id, course_name, department, credits) VALUES
(1,'Java Programming','CSE',4),
(2,'Database Systems','CSE',3),
(3,'Computer Networks','IT',3),
(4,'Digital Electronics','ECE',4),
(5,'Engineering Mathematics','GENERAL',3);

INSERT INTO enrollments (student_id, course_id) VALUES
(1,1),(1,2),(2,4),(3,1),(4,3),(6,2),(7,4),(9,1);
