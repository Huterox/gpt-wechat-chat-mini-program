// release/components/chatbox
const app = getApp();
// 时间工具类
const timeutil = require('./timeutil');
const cx = Component({
  /**
   * 组件的一些选项
   */
  options: {
    addGlobalClass: true,
    multipleSlots: true
  },
  /**
   * 组件的属性列表
   */
  properties: {
    roomId: {
      type: Number,
      observer: function (newVal, oldVal) {
        if (newVal != undefined && newVal != null) {
          // console.log(newVal)
        }
      }
    }
  },
  /**
   * 组件注册页面生命周期
   */
  pageLifetimes: {
    show: function () {
      // 页面被展示
    },
  },
  lifetimes: {
    attached() {
      var that = this;
      that.initMessageHistory();
      //初始化监听器
      // that.initWatcher();
      wx.getSystemInfo({
        success: function (res) {
          that.setData({
            systemInfo: res
          })
        }
      })
    },
    detached() {
      try {
      } catch (error) {
        console.log('--消息监听器关闭失败--')
      }
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    openid: app.globalData.openid || wx.getStorageSync('openid'),
    scrollId: '',
    systemInfo: {},
    //消息记录列表
    chatList: [],
    //标记触顶事件
    isTop: false,
    cur_page: 2,
    limit: 10,
    pagesize: 0,
  },
  /**
   * 组件的方法列表
   */
  methods: {
    loadMsgs(wid,itemid){
      var that = this;
      var dataList = that.data.chatList;
      wx.request({
        method: 'POST',
        url: 'http://127.0.0.1:88/api/ikunchat/histories/msgs',
        header: {
          'content-type': 'application/json'
        },
        data: {
          wid: wid,
          itemid: itemid,
          page: that.data.cur_page,
          limit: that.data.limit
        },
        success(res) {
          // 处理响应数据
          let r_code = res.data.code
          if (r_code != 0) {
            wx.switchTab({
              url: "/pages/mycenter/mycenter"
            });
            wx.showToast({
              title: "请先登录",
              icon: 'error',
              duration: 2000
            })
          }else{
            let page = res.data.page
            that.setData({
              cur_page: that.data.cur_page+1
            })
            let list = page.list
            if(list.length==0){
              wx.showToast({
                title: "到顶了吖~",
                icon: 'error',
                duration: 2000
              })
            }
            for(var i=0;i<list.length;i++){
              dataList.unshift(list[i]);
            }
          }
        },
        fail(error) {
          // 处理请求失败情况
          wx.switchTab({
            url: "/pages/mycenter/mycenter"
          });
        },
      })
    },
    // 预览图片
    viewImage(e) {
      // console.log(e)
      let url = e.currentTarget.dataset.url;
      wx.previewImage({
        urls: [url],
      })
    },
    //触顶事件
    tapTop() {
      console.log('--触顶--')
      var that = this;
      that.setData({
        isTop: true
      }, () => {
        let current_itemid = app.globalData.curr_itemid
        let wid = app.globalData.wid
        that.loadMsgs(wid,current_itemid)
      })
    },
    //初始化
    initMessageHistory() {
      let is_new_chat = app.globalData.is_new_chat
      if(is_new_chat){
        //初始化消息历史
        var that = this;
        app.globalData.cht = that
        that.setData({
          chatList: [
            {
              "type":"robot",
              "avatarUrl":"../index/image/yu.png",
              "content":"你好！我是 羽雫，是由Huterox研发的智能虚拟女友，很高兴认识你~(测试阶段，会话自动保存)",
            }
          ]
        })
      }else{
        var that = this;
        let current_itemid = app.globalData.curr_itemid
        let wid = app.globalData.wid
        that.loadMsgs(wid,current_itemid)
      }
    },
  }
})