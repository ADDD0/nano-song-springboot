package org.nano.song.service.originalSinger;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.OriginalSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.BindOriginalSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 绑定原唱歌手服务接口实现类
 */
@Service
@Transactional
public class BindOriginalSingerServiceImpl implements BindOriginalSingerService {

    @Autowired
    private OriginalSingerRepository originalSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    /**
     * 绑定原唱歌手
     *
     * @param bindOriginalSingerBean 绑定原唱歌手参数
     * @throws ResourceNotFoundException  资源未找到
     * @throws BindRelationExistException 绑定关系已存在
     */
    @Override
    public void bindOriginalSinger(BindOriginalSingerBean bindOriginalSingerBean) throws ResourceNotFoundException, BindRelationExistException {

        int singerId = bindOriginalSingerBean.getSingerId();
        int songCollectionId = bindOriginalSingerBean.getSongCollectionId();
        // 通过歌曲集合id查询歌曲集合 若不存在 报400
        SongCollection songCollection = songCollectionRepository.findBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, "id=" + songCollectionId));
        // 通过歌手id查询歌手 若不存在 报400
        Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(singerId, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + singerId));

        // 检查关联关系是否存在
        if (originalSingerRepository.findBySongCollectionIdAndSingerId(songCollectionId, singerId).isPresent()) {
            // 若关联关系存在 报400
            throw new BindRelationExistException(Constant.SHOW_SONG_COLLECTION, songCollection.getSongTitle(), Constant.SHOW_SINGER, singer.getSingerName());
        }

        OriginalSinger originalSinger = new OriginalSinger();
        // 设置实体属性
        originalSinger.setSingerId(singerId);
        originalSinger.setSongCollectionId(songCollectionId);
        // 数据持久化
        originalSingerRepository.save(originalSinger);
    }
}
