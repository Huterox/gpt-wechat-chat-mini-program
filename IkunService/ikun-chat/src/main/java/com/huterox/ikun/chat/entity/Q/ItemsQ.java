package com.huterox.ikun.chat.entity.Q;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsQ {

    @NotNull
    private String wid;
    @NotNull(message = "page不能为空")
    private Integer page;
    @NotNull
    @Max(20)
    private Integer limit;
}
