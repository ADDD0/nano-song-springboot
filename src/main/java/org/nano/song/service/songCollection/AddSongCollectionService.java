package org.nano.song.service.songCollection;

import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.info.bean.songCollection.AddSongCollectionBean;

public interface AddSongCollectionService {

    /**
     * 新增歌曲集合
     *
     * @param addSongCollectionBean 新增歌曲集合参数
     * @throws ResourceExistException 查找数据已存在
     */
    void addSongCollection(AddSongCollectionBean addSongCollectionBean) throws ResourceExistException;
}
