INSERT INTO `${TABLE}` (`player`,`category`,`stat`,`value`) " +
          "values (?,?,?,?) ON DUPLICATE KEY UPDATE `value`=?