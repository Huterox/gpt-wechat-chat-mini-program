package com.huterox.ikun.chat.entity.R;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgR implements Serializable {

    private static final long serialVersionUID = 1L;
    private String type;
    private String avatarUrl;
    private String content;

}
