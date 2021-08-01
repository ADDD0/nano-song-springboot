package org.nano.song.info.response.songCollection;

import lombok.Data;

import java.util.ArrayList;

@Data
public class QuerySongCollectionResponse {
    // 歌曲集合查询结果列表
    private ArrayList<QuerySongCollectionResponseResource> querySongCollectionResponseResourceArrayList;
}
