package org.nano.song.service.singer;

import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.info.bean.singer.AddSingerBean;

public interface AddSingerService {

    /**
     * 新增歌手
     *
     * @param addSingerBean 新增歌手参数
     * @throws ResourceExistException 查找数据已存在
     */
    void addSinger(AddSingerBean addSingerBean) throws ResourceExistException;
}
