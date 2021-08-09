package org.nano.song.info.bean.song;

import lombok.Data;

/**
 * 查询歌曲参数
 */
@Data
public class QuerySongBean {
    // 弹唱日期
    private String performanceDate;
    // 歌曲id
    private Integer songCollectionId;
}
