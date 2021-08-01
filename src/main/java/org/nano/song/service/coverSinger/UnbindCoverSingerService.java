package org.nano.song.service.coverSinger;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.UnbindCoverSingerBean;

public interface UnbindCoverSingerService {

    /**
     * 解绑翻唱歌手
     *
     * @param unbindCoverSingerBean 解绑翻唱歌手参数
     * @throws ResourceNotFoundException 查找数据不存在
     */
    void unbindCoverSinger(UnbindCoverSingerBean unbindCoverSingerBean) throws ResourceNotFoundException;
}
