package org.nano.song.info.bean.songCollection;

import lombok.Data;

/**
 * 查询歌曲集合参数
 */
@Data
public class QuerySongCollectionBean {
    // 歌曲标题
    private String songTitle;
    // 歌手姓名
    private String singerName;
}
