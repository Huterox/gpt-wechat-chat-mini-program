package com.huterox.ikun.chat.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huterox.ikun.chat.entity.WUserEntity;
import com.huterox.ikun.chat.service.WUserService;
import com.huterox.common.utils.PageUtils;
import com.huterox.common.utils.R;



/**
 * 
 *
 * @author Huterox
 * @email 3139541502@qq.com
 * @date 2023-05-01 09:55:08
 */
@RestController
@RequestMapping("chat/wuser")
public class WUserController {
    @Autowired
    private WUserService wUserService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    public R info(@PathVariable("uid") Long uid){
		WUserEntity wUser = wUserService.getById(uid);

        return R.ok().put("wUser", wUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WUserEntity wUser){
		wUserService.save(wUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WUserEntity wUser){
		wUserService.updateById(wUser);

        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] uids){
		wUserService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
