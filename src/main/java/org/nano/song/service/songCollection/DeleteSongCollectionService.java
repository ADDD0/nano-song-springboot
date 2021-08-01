package org.nano.song.service.songCollection;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.songCollection.DeleteSongCollectionBean;

public interface DeleteSongCollectionService {

    /**
     * 删除歌曲集合
     *
     * @param deleteSongCollectionBean 删除歌曲集合参数
     * @throws ResourceNotFoundException 查找数据不存在
     */
    void deleteSongCollection(DeleteSongCollectionBean deleteSongCollectionBean) throws ResourceNotFoundException;
}
