package org.nano.song.info.response.song;

import lombok.Data;

import java.util.ArrayList;

@Data
public class QuerySongResponse {
    // 歌曲查询结果列表
    private ArrayList<QuerySongResponseResource> querySongResponseResourceArrayList;
}
