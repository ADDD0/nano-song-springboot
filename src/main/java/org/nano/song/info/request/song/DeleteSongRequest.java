package org.nano.song.info.request.song;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteSongRequest {
    // 歌曲id
    @NotNull
    private int songId;
}
