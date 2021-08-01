package org.nano.song.info.request.songCollection;

import lombok.Data;
import org.nano.song.domain.Constant;

import javax.validation.constraints.NotNull;

@Data
public class DeleteSongCollectionRequest {
    // 歌曲集合id
    @NotNull(message = Constant.ERR_MSG_SONG_COLLECTION_ID_EMPTY)
    private int songCollectionId;
}
