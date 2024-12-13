DROP PROCEDURE IF EXISTS book_multi_select_proc;
CREATE PROCEDURE book_multi_select_proc(IN p_start INT, IN p_linesize INT)
BEGIN
    SELECT bid, title, author, price FROM book LIMIT p_start, p_linesize;
    SELECT COUNT(*) AS count FROM book;
END;
DELIMITER;