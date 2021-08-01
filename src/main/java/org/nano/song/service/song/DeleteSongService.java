package org.nano.song.service.song;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.song.DeleteSongBean;

public interface DeleteSongService {

    /**
     * 删除歌曲
     *
     * @param deleteSongBean 删除歌曲参数
     * @throws ResourceNotFoundException 查找数据不存在
     */
    void deleteSong(DeleteSongBean deleteSongBean) throws ResourceNotFoundException;
}
