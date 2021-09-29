package org.nano.song.service.songCollection;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.songCollection.QuerySongCollectionBean;
import org.nano.song.info.response.songCollection.QuerySongCollectionApiResponse;

/**
 * 查询歌曲集合服务接口类
 */
public interface QuerySongCollectionService {

    /**
     * 通过歌曲标题查询歌曲集合
     *
     * @param querySongCollectionBean 查询歌曲集合参数
     * @return 查询歌曲集合响应
     * @throws ResourceNotFoundException 资源未找到
     */
    QuerySongCollectionApiResponse querySongCollectionBySongTitle(QuerySongCollectionBean querySongCollectionBean) throws ResourceNotFoundException;

    /**
     * 通过歌手姓名查询歌曲集合
     *
     * @param querySongCollectionBean 查询歌曲集合参数
     * @return 查询歌曲集合响应
     * @throws ResourceNotFoundException 资源未找到
     */
    QuerySongCollectionApiResponse querySongCollectionBySingerName(QuerySongCollectionBean querySongCollectionBean) throws ResourceNotFoundException;
}
