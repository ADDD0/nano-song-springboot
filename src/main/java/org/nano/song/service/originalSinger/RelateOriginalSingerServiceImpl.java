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

@Service
@Transactional
public class RelateOriginalSingerServiceImpl implements RelateOriginalSingerService {

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Autowired
    private BindOriginalSingerService bindOriginalSingerService;

    @Override
    public void relateOriginalSinger(RelateOriginalSingerBean relateOriginalSingerBean)
            throws ResourceNotFoundException, BindRelationExistException {

        String singerName = relateOriginalSingerBean.getSingerName();
        String songTitle = relateOriginalSingerBean.getSongTitle();
        // 通过歌曲标题查找歌曲集合 若不存在 报400
        SongCollection songCollection = songCollectionRepository.findBySongTitleAndLogicalDeleteFlag(songTitle, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, songTitle));
        // 通过歌手姓名查找歌手 若不存在 报400
        Singer singer = singerRepository.findBySingerNameAndLogicalDeleteFlag(singerName, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, singerName));

        BindOriginalSingerBean bindOriginalSingerBean = new BindOriginalSingerBean();
        bindOriginalSingerBean.setSingerId(singer.getSingerId());
        bindOriginalSingerBean.setSongCollectionId(songCollection.getSongCollectionId());
        // 绑定原唱歌手
        bindOriginalSingerService.bindOriginalSinger(bindOriginalSingerBean);
    }
}
