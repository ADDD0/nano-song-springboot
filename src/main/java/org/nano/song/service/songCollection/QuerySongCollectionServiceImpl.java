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
import org.nano.song.info.response.songCollection.QuerySongCollectionApiResponse;
import org.nano.song.service.originalSinger.QueryOriginalSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public QuerySongCollectionApiResponse querySongCollectionBySongTitle(QuerySongCollectionBean querySongCollectionBean)
            throws ResourceNotFoundException {

        String songTitle = querySongCollectionBean.getSongTitle();
        // 通过歌曲标题查找所有歌曲集合
        List<SongCollection> songCollectionList = songCollectionRepository.findAllBySongTitleContainingAndLogicalDeleteFlag(songTitle, DELETE_FLAG.UNDELETED.getCode())
                .orElse(Collections.emptyList());

        List<ReturnSongCollectionBean> returnSongCollectionBeanList = new ArrayList<>();
        for (SongCollection songCollection : songCollectionList) {
            QueryOriginalSingerBean queryOriginalSingerBean = new QueryOriginalSingerBean();
            queryOriginalSingerBean.setSongCollectionId(songCollection.getSongCollectionId());
            // 查询原唱歌手
            ReturnOriginalSingerBean returnOriginalSingerBean = queryOriginalSingerService.queryOriginalSinger(queryOriginalSingerBean);
            List<OriginalSinger> originalSingerList = returnOriginalSingerBean.getOriginalSingerList();

            List<Singer> singerList = new ArrayList<>();
            for (OriginalSinger originalSinger : originalSingerList) {
                // 通过歌手id查找歌手
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
                singerList.add(singer);
            }
            // 原唱歌手排序
            singerList.sort(Comparator.comparing(Singer::getSingerName));

            ReturnSongCollectionBean returnSongCollectionBean = new ReturnSongCollectionBean();
            returnSongCollectionBean.setSongCollection(songCollection);
            returnSongCollectionBean.setSingerList(singerList);
            // 插入当前歌曲集合查询结果
            returnSongCollectionBeanList.add(returnSongCollectionBean);
        }
        // 歌曲集合查询结果按歌曲标题排序
        returnSongCollectionBeanList.sort(Comparator.comparing(o -> o.getSongCollection().getSongTitle()));

        QuerySongCollectionApiResponse querySongCollectionApiResponse = new QuerySongCollectionApiResponse();
        querySongCollectionApiResponse.setReturnSongCollectionBeanList(returnSongCollectionBeanList);

        return querySongCollectionApiResponse;
    }

    @Override
    public QuerySongCollectionApiResponse querySongCollectionBySingerName(QuerySongCollectionBean querySongCollectionBean)
            throws ResourceNotFoundException {

        String singerName = querySongCollectionBean.getSingerName();
        // 通过歌手姓名查找所有歌手
        List<Singer> querySingerList = singerRepository.findAllBySingerNameContainingAndLogicalDeleteFlag(singerName, DELETE_FLAG.UNDELETED.getCode())
                .orElse(Collections.emptyList());

        // 查找该歌手演唱过的所有歌曲集合
        List<Integer> songCollectionIdList = new ArrayList<>();
        for (Singer singer : querySingerList) {
            QueryOriginalSingerBean queryOriginalSingerBean = new QueryOriginalSingerBean();
            queryOriginalSingerBean.setSingerId(singer.getSingerId());
            // 查询部分原唱歌手
            ReturnOriginalSingerBean returnOriginalSingerBean = queryOriginalSingerService.queryOriginalSinger(queryOriginalSingerBean);
            List<OriginalSinger> partOriginalSingerList = returnOriginalSingerBean.getOriginalSingerList();

            for (OriginalSinger partOriginalSinger : partOriginalSingerList) {
                // 获取歌曲集合id
                int songCollectionId = partOriginalSinger.getSongCollectionId();
                if (!songCollectionIdList.contains(songCollectionId)) {
                    // 保证歌曲集合不会被重复填加
                    songCollectionIdList.add(songCollectionId);
                }
            }
        }

        List<ReturnSongCollectionBean> returnSongCollectionBeanList = new ArrayList<>();
        for (int songCollectionId : songCollectionIdList) {
            // 通过歌曲集合id查找歌曲集合 若不存在 报400
            SongCollection songCollection = songCollectionRepository.findBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, DELETE_FLAG.UNDELETED.getCode())
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, "id=" + songCollectionId));

            QueryOriginalSingerBean queryOriginalSingerBean = new QueryOriginalSingerBean();
            queryOriginalSingerBean.setSongCollectionId(songCollection.getSongCollectionId());
            // 查询全部原唱歌手
            ReturnOriginalSingerBean returnOriginalSingerBean = queryOriginalSingerService.queryOriginalSinger(queryOriginalSingerBean);
            List<OriginalSinger> allOriginalSingerList = returnOriginalSingerBean.getOriginalSingerList();

            List<Singer> singerList = new ArrayList<>();
            for (OriginalSinger originalSinger : allOriginalSingerList) {
                // 通过歌手id查找歌手
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
                singerList.add(singer);
            }
            // 原唱歌手排序
            singerList.sort(Comparator.comparing(Singer::getSingerName));

            ReturnSongCollectionBean returnSongCollectionBean = new ReturnSongCollectionBean();
            returnSongCollectionBean.setSongCollection(songCollection);
            returnSongCollectionBean.setSingerList(singerList);
            // 插入当前歌曲集合查询结果
            returnSongCollectionBeanList.add(returnSongCollectionBean);
        }
        // 歌曲集合查询结果按歌曲标题排序
        returnSongCollectionBeanList.sort(Comparator.comparing(o -> o.getSongCollection().getSongTitle()));

        QuerySongCollectionApiResponse querySongCollectionApiResponse = new QuerySongCollectionApiResponse();
        querySongCollectionApiResponse.setReturnSongCollectionBeanList(returnSongCollectionBeanList);

        return querySongCollectionApiResponse;
    }
}
