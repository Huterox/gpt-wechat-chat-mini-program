package com.huterox.ikun.chat.entity.Q;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatQW  implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String wid;
    @Length(min = 1,max = 248,message="单次对话内容不能超过248")
    private String msg;
    @NotNull
    private String itemid;

}
