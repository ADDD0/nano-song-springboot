package org.nano.song.controller;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.OriginalSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.request.songCollection.QuerySongCollectionBySingerNameRequest;
import org.nano.song.info.request.songCollection.QuerySongCollectionBySongTitleRequest;
import org.nano.song.info.response.songCollection.QuerySongCollectionResponse;
import org.nano.song.info.response.songCollection.QuerySongCollectionResponseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Comparator;

@CrossOrigin
@RequestMapping(Constant.URL_QUERY_SONG_COLLECTION)
@RestController
@Transactional
public class QuerySongCollectionController {

    @Autowired
    private OriginalSingerRepository originalSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @PostMapping(Constant.QUERY_CONTENT_SONG_TITLE)
    @ResponseBody
    public ResponseEntity<?> query(@RequestBody @Validated @NotEmpty QuerySongCollectionBySongTitleRequest request)
            throws ResourceNotFoundException {

        String songTitle = request.getSongTitle();

        // 查找歌曲集合
        ArrayList<SongCollection> songCollectionArrayList = songCollectionRepository.findAllBySongTitleContainingAndLogicalDeleteFlag(songTitle, DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());

        ArrayList<QuerySongCollectionResponseResource> queryResponseResourceArrayList = new ArrayList<>();
        for (SongCollection songCollection : songCollectionArrayList) {
            // 查找歌曲集合原唱
            ArrayList<OriginalSinger> originalSingerArrayList = originalSingerRepository.findAllBySongCollectionId(songCollection.getSongCollectionId())
                    .orElse(new ArrayList<>());

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (OriginalSinger originalSinger : originalSingerArrayList) {
                // 检查歌手是否存在 若不存在 报400
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
                singerArrayList.add(singer);
            }

            // 歌手数组排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));
            // 构建歌曲集合查询结果
            QuerySongCollectionResponseResource responseResource = new QuerySongCollectionResponseResource();
            responseResource.setSongCollection(songCollection);
            responseResource.setSingerArrayList(singerArrayList);
            // 插入歌曲集合查询结果
            queryResponseResourceArrayList.add(responseResource);
        }
        QuerySongCollectionResponse response = new QuerySongCollectionResponse();
        // 歌曲集合查询结果按歌曲标题排序
        queryResponseResourceArrayList.sort(Comparator.comparing(o -> o.getSongCollection().getSongTitle()));

        response.setQuerySongCollectionResponseResourceArrayList(queryResponseResourceArrayList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(Constant.QUERY_CONTENT_SINGER_NAME)
    @ResponseBody
    public ResponseEntity<?> query(@RequestBody @Validated @NotEmpty QuerySongCollectionBySingerNameRequest request) throws ResourceNotFoundException {

        String singerName = request.getSingerName();

        // 查找歌手
        ArrayList<Singer> singerArrayList = singerRepository.findAllBySingerNameContainingAndLogicalDeleteFlag(singerName, DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());

        // 查找该歌手演唱过的所有歌曲集合
        ArrayList<Integer> songCollectionIdArrayList = new ArrayList<>();
        for (Singer singer : singerArrayList) {
            ArrayList<OriginalSinger> partOriginalSingerArrayList = originalSingerRepository.findAllBySingerId(singer.getSingerId())
                    .orElse(new ArrayList<>());

            for (OriginalSinger partOriginalSinger : partOriginalSingerArrayList) {
                // 获取歌曲集合ID
                int songCollectionId = partOriginalSinger.getSongCollectionId();
                if (!songCollectionIdArrayList.contains(songCollectionId)) {
                    // 保证歌曲集合不会被重复填加
                    songCollectionIdArrayList.add(songCollectionId);
                }
            }
        }

        ArrayList<QuerySongCollectionResponseResource> queryResponseResourceArrayList = new ArrayList<>();
        for (int songCollectionId : songCollectionIdArrayList) {
            // 查找歌曲集合 若不存在 报400
            SongCollection songCollection = songCollectionRepository.findBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, DELETE_FLAG.UNDELETED.getCode())
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, "id=" + songCollectionId));
            // 查找歌曲原唱
            ArrayList<OriginalSinger> originalSingerArrayList = originalSingerRepository.findAllBySongCollectionId(songCollectionId)
                    .orElse(new ArrayList<>());

            singerArrayList = new ArrayList<>();
            for (OriginalSinger originalSinger : originalSingerArrayList) {
                // 检查各位歌手是否存在 若不存在 报400
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
                singerArrayList.add(singer);
            }

            // 歌手数组排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));
            // 构建歌曲集合查询结果
            QuerySongCollectionResponseResource responseResource = new QuerySongCollectionResponseResource();
            responseResource.setSongCollection(songCollection);
            responseResource.setSingerArrayList(singerArrayList);
            // 插入歌曲集合查询结果
            queryResponseResourceArrayList.add(responseResource);
        }
        QuerySongCollectionResponse response = new QuerySongCollectionResponse();
        // 歌曲集合查询结果按歌曲标题排序
        queryResponseResourceArrayList.sort(Comparator.comparing(o -> o.getSongCollection().getSongTitle()));

        response.setQuerySongCollectionResponseResourceArrayList(queryResponseResourceArrayList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
