package org.nano.song.controller;

import org.nano.song.domain.Constant;
import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.songCollection.AddSongCollectionBean;
import org.nano.song.info.bean.songCollection.DeleteSongCollectionBean;
import org.nano.song.info.request.songCollection.AddSongCollectionRequest;
import org.nano.song.info.request.songCollection.DeleteSongCollectionRequest;
import org.nano.song.service.songCollection.AddSongCollectionService;
import org.nano.song.service.songCollection.DeleteSongCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@CrossOrigin
@RequestMapping(Constant.URL_SONG_COLLECTION)
@RestController
public class SongCollectionController {

    @Autowired
    private AddSongCollectionService addSongCollectionService;

    @Autowired
    private DeleteSongCollectionService deleteSongCollectionService;

    @PostMapping(Constant.OPERATION_ADD)
    @ResponseBody
    public ResponseEntity<?> addSongCollection(@RequestBody @Validated @NotEmpty AddSongCollectionRequest request)
            throws ResourceExistException {

        AddSongCollectionBean addSongCollectionBean = new AddSongCollectionBean();
        addSongCollectionBean.setSongTitle(request.getSongTitle());
        addSongCollectionBean.setChineseTitle(request.getChineseTitle());
        addSongCollectionBean.setEnglishTitle(request.getEnglishTitle());
        // 新增歌曲集合
        addSongCollectionService.addSongCollection(addSongCollectionBean);

        return new ResponseEntity<>(Constant.MSG_ADD_SUCCESS, HttpStatus.OK);
    }

    @PostMapping(Constant.OPERATION_DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteSongCollection(@RequestBody @Validated @NotEmpty DeleteSongCollectionRequest request)
            throws ResourceNotFoundException {

        DeleteSongCollectionBean deleteSongCollectionBean = new DeleteSongCollectionBean();
        deleteSongCollectionBean.setSongCollectionId(request.getSongCollectionId());
        // 删除歌曲集合
        deleteSongCollectionService.deleteSongCollection(deleteSongCollectionBean);

        return new ResponseEntity<>(Constant.MSG_DELETE_SUCCESS, HttpStatus.OK);
    }
}
