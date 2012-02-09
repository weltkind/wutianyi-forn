DELIMITER $$

DROP PROCEDURE IF EXISTS `work`.`paging` $$
CREATE PROCEDURE `work`.`paging` (in t int, in d varchar(56),in curPage int)
BEGIN
  declare total int default 0;
  declare totalPage int default 0;
  select count(id) into total from king_root where type=t and date = d;
  if total%50 > 0 then
    set totalPage = floor(total / 50 )+ 1;
  else
    set totalPage = floor(total / 50 );
  end if;
  if curPage > totalPage then
    set curPage = totalPage;
  end if;
  set @tt = t;
  set @dd = d;
  set @s = (curPage - 1) * 50;

  prepare stmt from "select * from king_root where type=? and date=? limit ?,50";
  execute stmt using @tt,@dd,@s;

END $$

DELIMITER ;