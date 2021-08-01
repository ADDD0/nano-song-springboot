package org.nano.song.service.originalSinger;

import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.UnbindOriginalSingerBean;

public interface UnbindOriginalSingerService {

    /**
     * 解绑原唱歌手
     *
     * @param unbindOriginalSingerBean 解绑原唱歌手参数
     * @throws ResourceNotFoundException 查找数据不存在
     */
    void unbindOriginalSinger(UnbindOriginalSingerBean unbindOriginalSingerBean) throws ResourceNotFoundException;
}
