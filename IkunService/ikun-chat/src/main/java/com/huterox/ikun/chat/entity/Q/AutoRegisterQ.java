package com.huterox.ikun.chat.entity.Q;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoRegisterQ implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String wid;

}