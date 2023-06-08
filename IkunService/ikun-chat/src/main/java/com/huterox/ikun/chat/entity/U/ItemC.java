package com.huterox.ikun.chat.entity.U;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemC implements Serializable {
    private static final long serialVersionUID = 1L;

    private String timeDay;
    private String itemid;
}