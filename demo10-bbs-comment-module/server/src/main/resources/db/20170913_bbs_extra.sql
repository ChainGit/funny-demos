USE bbs;

DELIMITER //
DROP FUNCTION IF EXISTS queryReply;
CREATE FUNCTION `queryReply`(parentCommentId INT)
  RETURNS VARCHAR(4000)
  BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);

    SET sTemp = '$';
    SET sTempChd = cast(parentCommentId AS CHAR);

    WHILE sTempChd IS NOT NULL DO
      SET sTemp = CONCAT(sTemp, ',', sTempChd);
      SELECT group_concat(cid)
      INTO sTempChd
      FROM t_comment
      WHERE FIND_IN_SET(pid, sTempChd) > 0;
    END WHILE;
    RETURN sTemp;
  END
//
DELIMITER ;