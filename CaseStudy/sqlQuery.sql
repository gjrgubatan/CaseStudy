/*Please run this line of codes below first, the 2nd line then the 3rd line*/
SET GLOBAL sql_mode='';
use CaseStudy;

/*Please run the spring boot app first, then stop. This is to generate the database and tables. Then please run the lines below,
to insert an admin account for it to pass the login system. Thank you.*/
INSERT INTO admin VALUES (1, "admin@gmail.com", "Admin", "Root", "password");
ALTER TABLE employees
DROP COLUMN email;
