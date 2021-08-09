package org.nano.song.info.response.songCollection;

import lombok.Data;
import org.nano.song.info.bean.songCollection.ReturnSongCollectionBean;

import java.util.ArrayList;

/**
 * 查询歌曲集合响应
 */
@Data
public class QuerySongCollectionResponse {
    // 歌曲集合查询结果列表
    private ArrayList<ReturnSongCollectionBean> returnSongCollectionBeanArrayList;
}
