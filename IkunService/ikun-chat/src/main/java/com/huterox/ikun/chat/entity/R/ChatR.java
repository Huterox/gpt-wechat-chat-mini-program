package com.huterox.ikun.chat.entity.R;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatR implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double spendTime;
    private String res;
}