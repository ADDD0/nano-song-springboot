package org.nano.song.service.songCollection;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.QueryOriginalSingerBean;
import org.nano.song.info.bean.originalSinger.ReturnOriginalSingerBean;
import org.nano.song.info.bean.songCollection.QuerySongCollectionBean;
import org.nano.song.info.bean.songCollection.ReturnSongCollectionBean;
import org.nano.song.info.response.songCollection.QuerySongCollectionResponse;
import org.nano.song.service.originalSinger.QueryOriginalSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;

@Service
@Transactional
public class QuerySongCollectionServiceImpl implements QuerySongCollectionService {

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Autowired
    private QueryOriginalSingerService queryOriginalSingerService;

    @Override
    public QuerySongCollectionResponse querySongCollectionBySongTitle(QuerySongCollectionBean querySongCollectionBean)
            throws ResourceNotFoundException {

        String songTitle = querySongCollectionBean.getSongTitle();
        // 通过歌曲标题查找所有歌曲集合
        ArrayList<SongCollection> songCollectionArrayList = songCollectionRepository.findAllBySongTitleContainingAndLogicalDeleteFlag(songTitle, DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());

        ArrayList<ReturnSongCollectionBean> returnSongCollectionBeanArrayList = new ArrayList<>();
        for (SongCollection songCollection : songCollectionArrayList) {
            QueryOriginalSingerBean queryOriginalSingerBean = new QueryOriginalSingerBean();
            queryOriginalSingerBean.setSongCollectionId(songCollection.getSongCollectionId());
            // 查询原唱歌手
            ReturnOriginalSingerBean returnOriginalSingerBean = queryOriginalSingerService.queryOriginalSinger(queryOriginalSingerBean);
            ArrayList<OriginalSinger> originalSingerArrayList = returnOriginalSingerBean.getOriginalSingerArrayList();

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (OriginalSinger originalSinger : originalSingerArrayList) {
                // 通过歌手id查找歌手
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
                singerArrayList.add(singer);
            }
            // 原唱歌手排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));

            ReturnSongCollectionBean returnSongCollectionBean = new ReturnSongCollectionBean();
            returnSongCollectionBean.setSongCollection(songCollection);
            returnSongCollectionBean.setSingerArrayList(singerArrayList);
            // 插入当前歌曲集合查询结果
            returnSongCollectionBeanArrayList.add(returnSongCollectionBean);
        }
        // 歌曲集合查询结果按歌曲标题排序
        returnSongCollectionBeanArrayList.sort(Comparator.comparing(o -> o.getSongCollection().getSongTitle()));

        QuerySongCollectionResponse querySongCollectionResponse = new QuerySongCollectionResponse();
        querySongCollectionResponse.setReturnSongCollectionBeanArrayList(returnSongCollectionBeanArrayList);

        return querySongCollectionResponse;
    }

    @Override
    public QuerySongCollectionResponse querySongCollectionBySingerName(QuerySongCollectionBean querySongCollectionBean)
            throws ResourceNotFoundException {

        String singerName = querySongCollectionBean.getSingerName();
        // 通过歌手姓名查找所有歌手
        ArrayList<Singer> querySingerArrayList = singerRepository.findAllBySingerNameContainingAndLogicalDeleteFlag(singerName, DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());

        // 查找该歌手演唱过的所有歌曲集合
        ArrayList<Integer> songCollectionIdArrayList = new ArrayList<>();
        for (Singer singer : querySingerArrayList) {
            QueryOriginalSingerBean queryOriginalSingerBean = new QueryOriginalSingerBean();
            queryOriginalSingerBean.setSingerId(singer.getSingerId());
            // 查询部分原唱歌手
            ReturnOriginalSingerBean returnOriginalSingerBean = queryOriginalSingerService.queryOriginalSinger(queryOriginalSingerBean);
            ArrayList<OriginalSinger> partOriginalSingerArrayList = returnOriginalSingerBean.getOriginalSingerArrayList();

            for (OriginalSinger partOriginalSinger : partOriginalSingerArrayList) {
                // 获取歌曲集合id
                int songCollectionId = partOriginalSinger.getSongCollectionId();
                if (!songCollectionIdArrayList.contains(songCollectionId)) {
                    // 保证歌曲集合不会被重复填加
                    songCollectionIdArrayList.add(songCollectionId);
                }
            }
        }

        ArrayList<ReturnSongCollectionBean> returnSongCollectionBeanArrayList = new ArrayList<>();
        for (int songCollectionId : songCollectionIdArrayList) {
            // 通过歌曲集合id查找歌曲集合 若不存在 报400
            SongCollection songCollection = songCollectionRepository.findBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, DELETE_FLAG.UNDELETED.getCode())
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, "id=" + songCollectionId));

            QueryOriginalSingerBean queryOriginalSingerBean = new QueryOriginalSingerBean();
            queryOriginalSingerBean.setSongCollectionId(songCollection.getSongCollectionId());
            // 查询全部原唱歌手
            ReturnOriginalSingerBean returnOriginalSingerBean = queryOriginalSingerService.queryOriginalSinger(queryOriginalSingerBean);
            ArrayList<OriginalSinger> allOriginalSingerArrayList = returnOriginalSingerBean.getOriginalSingerArrayList();

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (OriginalSinger originalSinger : allOriginalSingerArrayList) {
                // 通过歌手id查找歌手
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
                singerArrayList.add(singer);
            }
            // 原唱歌手排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));

            ReturnSongCollectionBean returnSongCollectionBean = new ReturnSongCollectionBean();
            returnSongCollectionBean.setSongCollection(songCollection);
            returnSongCollectionBean.setSingerArrayList(singerArrayList);
            // 插入当前歌曲集合查询结果
            returnSongCollectionBeanArrayList.add(returnSongCollectionBean);
        }
        // 歌曲集合查询结果按歌曲标题排序
        returnSongCollectionBeanArrayList.sort(Comparator.comparing(o -> o.getSongCollection().getSongTitle()));

        QuerySongCollectionResponse querySongCollectionResponse = new QuerySongCollectionResponse();
        querySongCollectionResponse.setReturnSongCollectionBeanArrayList(returnSongCollectionBeanArrayList);

        return querySongCollectionResponse;
    }
}
