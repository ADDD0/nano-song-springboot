package org.nano.song.service.singer;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.info.bean.singer.AddSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 新增歌手服务接口实现类
 */
@Service
@Transactional
public class AddSingerServiceImpl implements AddSingerService {

    @Autowired
    private SingerRepository singerRepository;

    /**
     * 新增歌手
     *
     * @param addSingerBean 新增歌手参数
     * @throws ResourceExistException 资源已存在
     */
    @Override
    public void addSinger(AddSingerBean addSingerBean) throws ResourceExistException {

        String singerName = addSingerBean.getSingerName();
        // 通过歌手姓名查询歌手
        if (singerRepository.findBySingerNameAndLogicalDeleteFlag(singerName, DELETE_FLAG.UNDELETED.getCode()).isPresent()) {
            // 若歌手存在 报400
            throw new ResourceExistException(Constant.SHOW_SINGER, singerName);
        }

        Singer singer = new Singer();
        // 设置实体属性
        singer.setSingerName(singerName);
        singer.setLogicalDeleteFlag(false);
        // 数据持久化
        singerRepository.save(singer);
    }
}
