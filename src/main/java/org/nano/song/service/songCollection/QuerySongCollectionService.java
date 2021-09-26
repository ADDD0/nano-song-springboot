package org.nano.song.service.songCollection;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.songCollection.QuerySongCollectionBean;
import org.nano.song.info.response.songCollection.QuerySongCollectionApiResponse;

public interface QuerySongCollectionService {

    /**
     * 通过歌曲标题查询歌曲集合
     *
     * @param querySongCollectionBean 查询歌曲集合参数
     * @return 查询歌曲集合响应
     * @throws ResourceNotFoundException 查找数据不存在
     */
    QuerySongCollectionApiResponse querySongCollectionBySongTitle(QuerySongCollectionBean querySongCollectionBean) throws ResourceNotFoundException;

    /**
     * 通过歌手姓名查询歌曲集合
     *
     * @param querySongCollectionBean 查询歌曲集合参数
     * @return 查询歌曲集合响应
     * @throws ResourceNotFoundException 查找数据不存在
     */
    QuerySongCollectionApiResponse querySongCollectionBySingerName(QuerySongCollectionBean querySongCollectionBean) throws ResourceNotFoundException;
}
