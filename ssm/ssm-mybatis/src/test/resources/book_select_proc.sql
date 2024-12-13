DROP PROCEDURE IF EXISTS book_select_proc;
CREATE PROCEDURE book_select_proc(IN p_name VARCHAR(50))
BEGIN
    IF p_name IS NOT NULL AND p_name != '' THEN
        SELECT bid, title, author, price FROM book WHERE title LIKE CONCAT('%', p_name, '%');
    ELSE
        SELECT bid, title, author, price FROM book;
    END IF;
END;
DELIMITER;