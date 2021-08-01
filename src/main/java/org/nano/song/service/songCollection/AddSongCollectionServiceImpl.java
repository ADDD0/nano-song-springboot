package org.nano.song.service.songCollection;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.info.bean.songCollection.AddSongCollectionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddSongCollectionServiceImpl implements AddSongCollectionService {

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Override
    public void addSongCollection(AddSongCollectionBean addSongCollectionBean) throws ResourceExistException {

        String songTitle = addSongCollectionBean.getSongTitle();
        String chineseTitle = addSongCollectionBean.getChineseTitle();
        String englishTitle = addSongCollectionBean.getEnglishTitle();
        // 通过歌曲标题查找歌曲集合
        if (songCollectionRepository.findBySongTitleAndLogicalDeleteFlag(songTitle, DELETE_FLAG.UNDELETED.getCode()).isPresent()) {
            // 若歌曲集合存在 报400
            throw new ResourceExistException(Constant.SHOW_SONG_COLLECTION, songTitle);
        }

        SongCollection songCollection = new SongCollection();
        // 设置实体属性
        songCollection.setSongTitle(songTitle);
        songCollection.setChineseTitle(chineseTitle);
        songCollection.setEnglishTitle(englishTitle);
        songCollection.setLogicalDeleteFlag(false);
        // 数据持久化
        songCollectionRepository.save(songCollection);
    }
}
