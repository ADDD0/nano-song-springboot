package org.nano.song.service.song;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.song.DeleteSongBean;

/**
 * 删除歌曲服务接口类
 */
public interface DeleteSongService {

    /**
     * 删除歌曲
     *
     * @param deleteSongBean 删除歌曲参数
     * @throws ResourceNotFoundException 资源未找到
     */
    void deleteSong(DeleteSongBean deleteSongBean) throws ResourceNotFoundException;
}
