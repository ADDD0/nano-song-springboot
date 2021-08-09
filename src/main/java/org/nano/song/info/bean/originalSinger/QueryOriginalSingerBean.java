package org.nano.song.info.bean.originalSinger;

import lombok.Data;

/**
 * 查询原唱歌手参数
 */
@Data
public class QueryOriginalSingerBean {
    // 歌曲集合id
    private Integer songCollectionId;
    // 歌手id
    private Integer singerId;
}
