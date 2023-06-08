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
@TableName("w_user")
public class WUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 微信id
	 */
	private String wid;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 注册
	 */
	private String createTime;
	/**
	 * 用户id
	 */
//	@TableId(type = IdType.AUTO)
	@TableId()
	private Long uid;

}
