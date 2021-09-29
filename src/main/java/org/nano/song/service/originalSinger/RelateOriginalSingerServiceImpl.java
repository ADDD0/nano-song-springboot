package org.nano.song.service.originalSinger;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.BindOriginalSingerBean;
import org.nano.song.info.bean.originalSinger.RelateOriginalSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 关联原唱歌手服务接口实现类
 */
@Service
@Transactional
public class RelateOriginalSingerServiceImpl implements RelateOriginalSingerService {

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Autowired
    private BindOriginalSingerService bindOriginalSingerService;

    /**
     * 关联原唱歌手
     *
     * @param relateOriginalSingerBean 关联原唱歌手参数
     * @throws ResourceNotFoundException  资源未找到
     * @throws BindRelationExistException 绑定关系已存在
     */
    @Override
    public void relateOriginalSinger(RelateOriginalSingerBean relateOriginalSingerBean)
            throws ResourceNotFoundException, BindRelationExistException {

        String singerName = relateOriginalSingerBean.getSingerName();
        String songTitle = relateOriginalSingerBean.getSongTitle();
        // 通过歌曲标题查询歌曲集合 若不存在 报400
        SongCollection songCollection = songCollectionRepository.findBySongTitleAndLogicalDeleteFlag(songTitle, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, songTitle));
        // 通过歌手姓名查询歌手 若不存在 报400
        Singer singer = singerRepository.findBySingerNameAndLogicalDeleteFlag(singerName, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, singerName));

        BindOriginalSingerBean bindOriginalSingerBean = new BindOriginalSingerBean();
        bindOriginalSingerBean.setSingerId(singer.getSingerId());
        bindOriginalSingerBean.setSongCollectionId(songCollection.getSongCollectionId());
        // 绑定原唱歌手
        bindOriginalSingerService.bindOriginalSinger(bindOriginalSingerBean);
    }
}
