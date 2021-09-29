package org.nano.song.service.singer;

import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.info.bean.singer.AddSingerBean;

/**
 * 新增歌手服务接口类
 */
public interface AddSingerService {

    /**
     * 新增歌手
     *
     * @param addSingerBean 新增歌手参数
     * @throws ResourceExistException 资源已存在
     */
    void addSinger(AddSingerBean addSingerBean) throws ResourceExistException;
}
