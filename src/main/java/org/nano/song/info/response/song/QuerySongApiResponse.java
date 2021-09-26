package org.nano.song.info.response.song;

import lombok.Data;
import org.nano.song.info.bean.song.ReturnSongBean;

import java.util.List;

/**
 * 查询歌曲响应
 */
@Data
public class QuerySongApiResponse {
    // 歌曲查询结果列表
    private List<ReturnSongBean> returnSongBeanList;
}
