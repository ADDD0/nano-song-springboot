package org.nano.song.controller;

import org.nano.song.domain.Constant;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.songCollection.QuerySongCollectionBean;
import org.nano.song.info.request.songCollection.QuerySongCollectionBySingerNameRequest;
import org.nano.song.info.request.songCollection.QuerySongCollectionBySongTitleRequest;
import org.nano.song.info.response.songCollection.QuerySongCollectionResponse;
import org.nano.song.service.songCollection.QuerySongCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@CrossOrigin
@RequestMapping(Constant.URL_QUERY_SONG_COLLECTION)
@RestController
public class QuerySongCollectionController {

    @Autowired
    private QuerySongCollectionService querySongCollectionService;

    @PostMapping(Constant.QUERY_CONTENT_SONG_TITLE)
    @ResponseBody
    public ResponseEntity<QuerySongCollectionResponse> query(@RequestBody @Validated @NotEmpty QuerySongCollectionBySongTitleRequest request)
            throws ResourceNotFoundException {

        QuerySongCollectionBean querySongCollectionBean = new QuerySongCollectionBean();
        querySongCollectionBean.setSingerName(null);
        querySongCollectionBean.setSongTitle(request.getSongTitle());
        // 返回查询歌曲集合
        return new ResponseEntity<>(querySongCollectionService.querySongCollectionBySongTitle(querySongCollectionBean), HttpStatus.OK);
    }

    @PostMapping(Constant.QUERY_CONTENT_SINGER_NAME)
    @ResponseBody
    public ResponseEntity<QuerySongCollectionResponse> query(@RequestBody @Validated @NotEmpty QuerySongCollectionBySingerNameRequest request)
            throws ResourceNotFoundException {

        QuerySongCollectionBean querySongCollectionBean = new QuerySongCollectionBean();
        querySongCollectionBean.setSingerName(request.getSingerName());
        querySongCollectionBean.setSongTitle(null);
        // 返回查询歌曲集合
        return new ResponseEntity<>(querySongCollectionService.querySongCollectionBySingerName(querySongCollectionBean), HttpStatus.OK);
    }
}
