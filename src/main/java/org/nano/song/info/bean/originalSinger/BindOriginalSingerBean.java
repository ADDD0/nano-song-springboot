package org.nano.song.info.bean.originalSinger;

import lombok.Data;

/**
 * 绑定原唱歌手参数
 */
@Data
public class BindOriginalSingerBean {
    // 歌手id
    private int singerId;
    // 歌曲集合id
    private int songCollectionId;
}
