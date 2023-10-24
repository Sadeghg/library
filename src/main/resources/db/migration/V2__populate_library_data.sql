USE library;

-- Insert writers
INSERT INTO participator (name, participation_type)
values ('writer-1', 'WRITER'),
       ('writer-2', 'WRITER'),
       ('writer-3', 'WRITER'),
       ('writer-4', 'WRITER'),
       ('writer-5', 'WRITER'),
       ('writer-6', 'WRITER');

-- Insert translators
INSERT INTO participator (name, participation_type)
values ('translator-1', 'TRANSLATOR'),
       ('translator-2', 'TRANSLATOR'),
       ('translator-3', 'TRANSLATOR'),
       ('translator-4', 'TRANSLATOR'),
       ('translator-5', 'TRANSLATOR'),
       ('translator-6', 'TRANSLATOR'),
       ('translator-7', 'TRANSLATOR'),
       ('translator-8', 'TRANSLATOR'),
       ('translator-9', 'TRANSLATOR'),
       ('translator-10', 'TRANSLATOR');


INSERT INTO book (available_count, isbn, publish_date, publish_set, published_year, title)
values (10, 'ISBN102010', '2010-10-11', 10, 2010, 'destiny'),
       (7, 'ISBN012011', '2011-01-11', 1, 2011, 'hope'),
       (3, 'ISBN012012', '2012-10-09', 1, 2012, 'faith'),
       (1, 'ISBN022013', '2013-10-03', 2, 2013, 'green'),
       (11, 'ISBN112007', '2007-07-01', 11, 2007, 'red'),
       (3, 'ISBN022009', '2009-01-01', 2, 2009, 'yellow'),
       (7, 'ISBN032007', '2007-07-07', 3, 2007, 'black'),
       (9, 'ISBN032009', '2009-01-12', 3, 2009, 'gray'),
       (1, 'ISBN032011', '2011-11-11', 3, 2011, 'blue'),
       (11, 'ISBN012005', '2005-03-05', 1, 2005, 'white'),
       (7, 'ISBN021999', '1999-11-11', 2, 1999, 'orange'),
       (5, 'ISBN031998', '1998-01-12', 3, 1998, 'color'),
       (13, 'ISBN042001', '2001-02-17', 4, 2001, 'solid'),
       (11, 'ISBN052002', '2002-12-19', 5, 2002, 'soft'),
       (9, 'ISBN071997', '1997-04-21', 7, 1997, 'blur'),
       (2, 'ISBN111995', '1995-07-23', 11, 1995, 'zodiac'),
       (3, 'ISBN072011', '2011-10-17', 7, 2011, 'roads'),
       (7, 'ISBN092001', '2001-01-29', 9, 2001, 'books!'),
       (7, 'ISBN171991', '1991-03-27', 17, 1991, 'get more');


INSERT INTO book_writers (book_id, writer_id)
VALUES (1, 2),
       (1, 3),
       (2, 4),
       (3, 3),
       (4, 1),
       (5, 6),
       (6, 1),
       (6, 3),
       (6, 5),
       (7, 2),
       (8, 3),
       (9, 1),
       (10, 1),
       (11, 2),
       (12, 3),
       (13, 4),
       (14, 5),
       (15, 6),
       (16, 1),
       (17, 2),
       (17, 3),
       (18, 1),
       (19, 5),
       (19, 3);

INSERT INTO book_translators (book_id, translator_id)
VALUES (1, 7),
       (1, 8),
       (1, 9),
       (2, 11),
       (3, 12),
       (4, 16),
       (5, 14),
       (6, 10),
       (6, 12),
       (6, 11),
       (7, 7),
       (7, 8),
       (7, 9),
       (8, 13),
       (9, 10),
       (10, 7),
       (11, 8),
       (12, 9),
       (13, 11),
       (14, 12),
       (15, 16),
       (16, 15),
       (17, 14),
       (17, 10),
       (18, 9),
       (19, 7),
       (19, 8);

INSERT INTO customer (birth_date, checkout_count, customer_score, father_name, gender, last_name, membership_date, name,
                      national_code) value ('1997-01-11', 2, 0, 'Thor', 'MALE', 'Farzaneh', '2011-10-07', 'Hamed', '1234567891') ,
    ('1994-01-11', 2, 0, 'Odin', 'MALE', 'Sobhan', '2010-09-17', 'Samad', '1234567892'),
    ('1991-01-11', 2, 0, 'Tyre', 'FEMALE', 'Saman', '2002-11-21', 'Athena', '1234567893'),
    ('1991-01-11', 2, 0, 'Soltan', 'FEMALE', 'Sarah', '2002-11-21', 'Heshmat', '1234567894'),
       ('1991-01-11', 1, 0, 'Thunder', 'FEMALE', 'Joan', '2001-11-21', 'Ferdos', '1234567895'),
    ('1991-01-12', 1, 0, 'sator', 'MALE', 'TP', '2004-09-21', 'nill', '1234567896'),
    ('1992-03-11', 1, 0, 'Atlas', 'FEMALE', 'Jill', '2003-10-01', 'valentine', '1234567897'),
    ('1992-01-17', 2, 0, 'miller', 'MALE', 'Jakson', '2005-12-19', 'james', '1234567898');



INSERT INTO library.checkout (checkout_number, deadline, end, refunded, start, book_id, customer_id)
VALUES ('1419c95f-8346-4615-af69-d5e3b790a40v', '2023-10-22', null, false, '2023-10-16', 1, 1),
       ('1419c95f-8346-4615-af69-d5e3b790a40g', '2023-10-20', null, false, '2023-10-16', 2, 1),
       ('1419c95f-8346-4615-af69-d5e3b790a4ac', '2023-10-22', null, false, '2023-10-19', 3, 2),
       ('1419c95f-8346-4615-af69-d5e3b790a41h', '2023-10-20', null, false, '2023-10-19', 4, 2),
       ('1419c95f-8346-4615-af69-d5e3b790a40o', '2023-10-23', null, false, '2023-10-17', 5, 3),
       ('1419c95f-8346-4615-af69-d5e3b790a40k', '2023-10-21', null, false, '2023-10-17', 7, 3),
       ('1419c95f-8346-4615-af69-d5e3b790a41g', '2023-10-21', null, false, '2023-10-11', 7, 4),
       ('1419c95f-8346-4615-af69-d5e3ba9ra12d', '2023-10-22', null, false, '2023-10-13', 11, 4),
       ('1419c95f-8346-4615-af69-d5e3bll0a4fa', '2023-10-22', null, false, '2023-10-11', 5, 5),
       ('1419c95f-8346-4615-af69-d5e3b790111d', '2023-10-22', null, false, '2023-10-13', 2, 6),
       ('1419c95f-8346-4615-af69-d5e3b790a4fa', '2021-10-22', null, false, '2021-10-11', 1, 7),
       ('1419c95f-8346-4615-af69-d5e34190a12d', '2021-10-22', null, false, '2021-10-13', 4, 8),
       ('1419c95f-8346-4615-af69-d5e3b0a4llvm', '2021-10-22', null, false, '2021-10-11', 3, 8);