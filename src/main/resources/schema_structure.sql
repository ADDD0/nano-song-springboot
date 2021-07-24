DROP TABLE IF EXISTS `singer`;
CREATE TABLE IF NOT EXISTS `singer` (
  `SINGER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '歌手id',
  `SINGER_NAME` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '歌手姓名',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  `LOGICAL_DELETE_FLAG` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志',
  PRIMARY KEY (`SINGER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='歌手' ;


DROP TABLE IF EXISTS `song_collection`;
CREATE TABLE IF NOT EXISTS `song_collection` (
  `SONG_COLLECTION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '歌曲集合id',
  `SONG_TITLE` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '歌曲标题',
  `CHINESE_TITLE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '中文标题',
  `ENGLISH_TITLE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '英文标题',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  `LOGICAL_DELETE_FLAG` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志',
  PRIMARY KEY (`SONG_COLLECTION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='歌曲集合' ;


DROP TABLE IF EXISTS `song`;
CREATE TABLE IF NOT EXISTS `song` (
  `SONG_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '歌曲id',
  `SONG_NAME` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '歌名',
  `PATH` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '路径',
  `SONG_COLLECTION_ID` int(10) unsigned NOT NULL COMMENT '歌曲集合id',
  `PERFORMANCE_DATE` datetime NOT NULL COMMENT '弹唱日期',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  `LOGICAL_DELETE_FLAG` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志',
  PRIMARY KEY (`SONG_ID`),
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='歌曲' ;
ALTER TABLE `song`
  ADD CONSTRAINT `song_ibfk_1` FOREIGN KEY (`SONG_COLLECTION_ID`) REFERENCES `song_collection` (`SONG_COLLECTION_ID`) ON DELETE CASCADE ON UPDATE CASCADE;


DROP TABLE IF EXISTS `original_singer`;
CREATE TABLE IF NOT EXISTS `original_singer` (
  `ORIGINAL_SINGER_RELATION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '原唱歌手关系id',
  `SONG_COLLECTION_ID` int(10) unsigned NOT NULL COMMENT '歌曲集合id',
  `SINGER_ID` int(10) unsigned NOT NULL COMMENT '歌手id',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`ORIGINAL_SINGER_RELATION_ID`),
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='原唱歌手' ;
ALTER TABLE `original_singer`
  ADD CONSTRAINT `original_singer_ibfk_2` FOREIGN KEY (`SINGER_ID`) REFERENCES `singer` (`SINGER_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `original_singer_ibfk_1` FOREIGN KEY (`SONG_COLLECTION_ID`) REFERENCES `song_collection` (`SONG_COLLECTION_ID`) ON DELETE CASCADE ON UPDATE CASCADE;


DROP TABLE IF EXISTS `cover_singer`;
CREATE TABLE IF NOT EXISTS `cover_singer` (
  `COVER_SINGER_RELATION_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '翻唱歌手关系id',
  `SONG_ID` int(10) unsigned NOT NULL COMMENT '歌曲id',
  `SINGER_ID` int(10) unsigned NOT NULL COMMENT '歌手id',
  `CREATED_DATE` datetime NOT NULL COMMENT '创建时间',
  `LAST_MODIFIED_DATE` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`COVER_SINGER_RELATION_ID`),
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='翻唱歌手' ;
ALTER TABLE `cover_singer`
  ADD CONSTRAINT `cover_singer_ibfk_2` FOREIGN KEY (`SINGER_ID`) REFERENCES `singer` (`SINGER_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cover_singer_ibfk_1` FOREIGN KEY (`SONG_ID`) REFERENCES `song` (`SONG_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
