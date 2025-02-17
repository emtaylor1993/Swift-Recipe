/*
 * INITIAL DATABASE DATA
 *
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 *
 * @description
 *    This .sql file represents the initial load of users into the database.
 */

INSERT INTO users (first_name, last_name, email, username, password) VALUES 
    ('Sade', 'Jn Baptiste', 'sjnbapt1@students.towson.edu', 'sjnbapt1', 'demo1'),
    ('Lakshmi', 'Kotikalapudi', 'ikotika1@students.towson.edu', 'lkotika1', 'demo2'),
    ('Andy', 'Nguyen', 'anguye27@students.towson.edu', 'anguye27', 'demo3'),
    ('Shivani', 'Samarla', 'ssamarl1@students.towson.edu', 'ssamarl1', 'demo4'),
    ('Emmanuel', 'Taylor', 'etaylor5@students.towson.edu', 'etaylor5', 'demo5');

ALTER TABLE recipe
ALTER COLUMN instructions VARBINARY;