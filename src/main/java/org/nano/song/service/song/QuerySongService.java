package org.nano.song.service.song;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.song.QuerySongBean;
import org.nano.song.info.response.song.QuerySongApiResponse;

import java.text.ParseException;

/**
 * 查询歌曲服务接口类
 */
public interface QuerySongService {

    /**
     * 通过歌曲集合id查询歌曲
     *
     * @param querySongBean 查询歌曲参数
     * @return 查询歌曲响应
     * @throws ResourceNotFoundException 资源未找到
     */
    QuerySongApiResponse querySongBySongCollectionId(QuerySongBean querySongBean) throws ResourceNotFoundException;

    /**
     * 通过弹唱日期查询歌曲
     *
     * @param querySongBean 查询歌曲参数
     * @return 查询歌曲响应
     * @throws ResourceNotFoundException 资源未找到
     * @throws ParseException            格式转换错误
     */
    QuerySongApiResponse querySongByPerformanceDate(QuerySongBean querySongBean) throws ResourceNotFoundException, ParseException;
}
