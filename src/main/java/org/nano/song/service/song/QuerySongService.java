package org.nano.song.service.song;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.song.QuerySongBean;
import org.nano.song.info.response.song.QuerySongResponse;

import java.text.ParseException;

public interface QuerySongService {

    /**
     * 通过歌曲集合id查询歌曲
     *
     * @param querySongBean 查询歌曲参数
     * @return 查询歌曲响应
     * @throws ResourceNotFoundException 查找数据不存在
     */
    QuerySongResponse querySongBySongCollectionId(QuerySongBean querySongBean) throws ResourceNotFoundException;

    /**
     * 通过弹唱日期查询歌曲
     *
     * @param querySongBean 查询歌曲参数
     * @return 查询歌曲响应
     * @throws ResourceNotFoundException 查找数据不存在
     * @throws ParseException            格式转换错误
     */
    QuerySongResponse querySongByPerformanceDate(QuerySongBean querySongBean) throws ResourceNotFoundException, ParseException;
}
