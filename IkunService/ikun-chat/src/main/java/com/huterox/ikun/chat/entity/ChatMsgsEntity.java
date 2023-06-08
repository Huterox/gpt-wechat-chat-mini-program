package com.huterox.ikun.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Huterox
 * @email 3139541502@qq.com
 * @date 2023-05-01 09:55:08
 */
@Data
@TableName("chat_msgs")
public class ChatMsgsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * itemid
	 */
	private String itemsid;
	/**
	 * 内容
	 */
	private String message;
	/**
	 * 时间
	 */
	private String createTime;
	/**
	 * 用户id
	 */
	private Long uid;
	/**
	 * msgid
	 */
	@TableId(type = IdType.AUTO)
	private Long msgid;

	private Integer msgType;

}
