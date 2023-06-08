package com.huterox.ikun.chat.entity;

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
@TableName("chat_items")
public class ChatItemsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long uid;
	/**
	 * itemid
	 */
	@TableId
	private String itemid;
	/**
	 * 保存时间
	 */
	private String saveTime;

}
