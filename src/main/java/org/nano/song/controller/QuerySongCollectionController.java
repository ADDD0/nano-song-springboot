package org.nano.song.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.nano.song.controller.handler.exception.ResourceNotFoundException;
import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.OriginalSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.SongCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Comparator;

@CrossOrigin
@RequestMapping(Constant.URL_QUERY_SONG_COLLECTION)
@RestController
@Slf4j
@Transactional
public class QuerySongCollectionController {

    @Autowired
    private OriginalSingerRepository originalSingerRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Data
    private static class QuerySongCollectionResponse {
        // 歌曲集合查询结果列表
        private ArrayList<QueryResponseResource> queryResponseResourceArrayList;

        @Data
        private static class QueryResponseResource {
            // 歌曲集合
            private SongCollection songCollection;
            // 各歌曲集合原唱列表
            private ArrayList<Singer> singerArrayList;
        }
    }

    @Data
    public static class QuerySongTitleRequest {
        // 歌曲标题
        @NotBlank(message = Constant.ERR_MSG_SONG_TITLE_EMPTY)
        private String songTitle;
    }

    @PostMapping(Constant.QUERY_CONTENT_SONG_TITLE)
    @ResponseBody
    public ResponseEntity<?> query(@RequestBody @Validated @NotEmpty QuerySongTitleRequest request) throws ResourceNotFoundException {

        String songTitle = request.getSongTitle();

        // 查找歌曲集合
        ArrayList<SongCollection> songCollectionArrayList = songCollectionRepository.findAllBySongTitleContainingAndLogicalDeleteFlag(songTitle, false)
                .orElse(new ArrayList<>());

        ArrayList<QuerySongCollectionResponse.QueryResponseResource> queryResponseResourceArrayList = new ArrayList<>();
        for (SongCollection songCollection : songCollectionArrayList) {
            // 查找歌曲集合原唱
            ArrayList<OriginalSinger> originalSingerArrayList = originalSingerRepository.findAllBySongCollectionId(songCollection.getSongCollectionId())
                    .orElse(new ArrayList<>());

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (OriginalSinger originalSinger : originalSingerArrayList) {
                // 检查歌手是否存在 若不存在 报400
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), false)
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
                singerArrayList.add(singer);
            }

            // 歌手数组排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));
            // 构建歌曲集合查询结果
            QuerySongCollectionResponse.QueryResponseResource responseResource = new QuerySongCollectionResponse.QueryResponseResource();
            responseResource.setSongCollection(songCollection);
            responseResource.setSingerArrayList(singerArrayList);
            // 插入歌曲集合查询结果
            queryResponseResourceArrayList.add(responseResource);
        }
        QuerySongCollectionResponse response = new QuerySongCollectionResponse();
        // 歌曲集合查询结果按歌曲标题排序
        queryResponseResourceArrayList.sort(Comparator.comparing(o -> o.getSongCollection().getSongTitle()));

        response.setQueryResponseResourceArrayList(queryResponseResourceArrayList);
        log.info(Constant.LOG_QUERY + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Data
    public static class QuerySingerNameRequest {
        // 歌手名
        @NotBlank(message = Constant.ERR_MSG_SINGER_NAME_EMPTY)
        private String singerName;
    }

    @PostMapping(Constant.QUERY_CONTENT_SINGER_NAME)
    @ResponseBody
    public ResponseEntity<?> query(@RequestBody @Validated @NotEmpty QuerySingerNameRequest request) throws ResourceNotFoundException {

        String singerName = request.getSingerName();

        // 查找歌手
        ArrayList<Singer> singerArrayList = singerRepository.findAllBySingerNameContainingAndLogicalDeleteFlag(singerName, false)
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

        ArrayList<QuerySongCollectionResponse.QueryResponseResource> queryResponseResourceArrayList = new ArrayList<>();
        for (int songCollectionId : songCollectionIdArrayList) {
            // 查找歌曲集合 若不存在 报400
            SongCollection songCollection = songCollectionRepository.findBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, false)
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, "id=" + songCollectionId));
            // 查找歌曲原唱
            ArrayList<OriginalSinger> originalSingerArrayList = originalSingerRepository.findAllBySongCollectionId(songCollectionId)
                    .orElse(new ArrayList<>());

            singerArrayList = new ArrayList<>();
            for (OriginalSinger originalSinger : originalSingerArrayList) {
                // 检查各位歌手是否存在 若不存在 报400
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(originalSinger.getSingerId(), false)
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + originalSinger.getSingerId()));
                singerArrayList.add(singer);
            }

            // 歌手数组排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));
            // 构建歌曲集合查询结果
            QuerySongCollectionResponse.QueryResponseResource responseResource = new QuerySongCollectionResponse.QueryResponseResource();
            responseResource.setSongCollection(songCollection);
            responseResource.setSingerArrayList(singerArrayList);
            // 插入歌曲集合查询结果
            queryResponseResourceArrayList.add(responseResource);
        }
        QuerySongCollectionResponse response = new QuerySongCollectionResponse();
        // 歌曲集合查询结果按歌曲标题排序
        queryResponseResourceArrayList.sort(Comparator.comparing(o -> o.getSongCollection().getSongTitle()));

        response.setQueryResponseResourceArrayList(queryResponseResourceArrayList);
        log.info(Constant.LOG_QUERY + response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
