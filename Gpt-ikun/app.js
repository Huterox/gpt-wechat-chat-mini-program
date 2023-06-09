//app.js
const config = require('config');
const Cloud = require('common/util/cloud-call');
App({
  globalData: {
    wid: null, // 初始化为 null
    islogin: false,
    curr_itemid: -1,
    is_new_chat: true,
  },
  onLaunch: function () {
    this.InitCloud(); //初始化云服务 / ESC
    this.InitCustom(); //初始化custom所需配置信息
  },
  InitCloud() {
    var that = this;
    wx.cloud.init({
      env: config.CloudID,
      traceUser: true
    })
  },
  InitCustom() {
    wx.getSystemInfo({
      success: e => {
        //console.log(e)
        this.globalData.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        // console.log(custom)
        this.globalData.Custom = custom;
        this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
      }
    })
  },
  config,
  Cloud,
})