// 获取全局APP
const app = getApp();

// 获取计时器函数
Page({
  /**
   * 页面的初始数据
   */
  data: {
    wid: null,
    login: false,
    //输入框距离
    InputBottom: 0,
    roomId: 1,
    userInfo: {},
    content: '你好~',
    go: false,
    groups: [{
      text: '点歌',
      value: 1
    }, ]
  },
  InputFocus(e) {
    this.setData({
      InputBottom: e.detail.height
    })
  },
  InputBlur(e) {
    this.setData({
      InputBottom: 0
    })
  },

  clean() {
    // 清空
    const cht = app.globalData.cht
    cht.data.chatList = []
    cht.data.chatList.push({
      "type": "rob",
      "content": "hey~",
      "avatarUrl": "../index/image/yu.png",
    })

    cht.setData({
      chatList: cht.data.chatList
    })
    this.setData({
      content: ''
    })
  },

  loadMsgs(){
    var wid = app.globalData.wid
    var itemid = app.globalData.curr_itemid
    const cht = app.globalData.cht
    wx.request({
      method: 'POST',
      url: 'http://127.0.0.1:88/api/ikunchat/histories/msgs',
      header: {
        'content-type': 'application/json'
      },
      data: {
        wid: wid,
        itemid: itemid,
        page: 1,
        limit: 10
      },
      success(res) {
        // 处理响应数据
        let r_code = res.data.code
        if (r_code != 0) {
          wx.showToast({
            title: "请求异常！",
            icon: 'error',
            duration: 2000
          })
        }else{
          let page = res.data.page
          let list = page.list
          for(var i=0;i<list.length;i++){
            console.log(list[i])
            cht.data.chatList.unshift(list[i])
          }
          cht.setData({
            chatList: cht.data.chatList
          })
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

  submit() {
    var go = this.data.go
    if(!go){
      //发生请求接口
      var that = this;
      that.setData({
        go: true
      })
      const cht = app.globalData.cht
      const content = that.data.content
      console.log("q:", content)
      //保存本地消息
      cht.data.chatList.push({
        "type": "man",
        "content": that.data.content,
        "avatarUrl": "../index/image/me.png",
      })
      //后面这个请求我们的接口
      wx.request({
        method: 'POST',
        url: 'http://127.0.0.1:88/api/ikunchat/wchat/chat',
        header: {
          'content-type': 'application/json'
        },
        data: {
          "wid": app.globalData.wid,
          "msg": that.data.content,
          "itemid": app.globalData.curr_itemid
        },
        success(res) {
          // 处理响应数据
          let r_code = res.data.code
          if (r_code != 0) {
            wx.showToast({
              title: "网络异常",
              icon: 'error',
              duration: 2000
            })
          }else{
            //显示消息
            let rmsg = res.data.chatR.res
            cht.data.chatList.push({
              "type": "rob",
              "content": rmsg,
              "avatarUrl": "../index/image/yu.png",
            })
            cht.setData({
              chatList: cht.data.chatList
            })
            that.setData({
              content: ''
            })
          }
          that.setData({
            go: false
          })
        },
        fail(error) {
          // 处理请求失败情况
          wx.showToast({
            title: "响应异常，请稍后再试...",
            icon: 'error',
            duration: 2000
          })
          that.setData({
            going: false
          })
        }
      })
    }
   
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onShow(options) {
    var app = getApp();
    var id = app.globalData.curr_itemid
    var app = getApp(); // 获取 App 实例
    var is_new_chat = app.globalData.is_new_chat
    if(!is_new_chat){
      this.loadMsgs()
    }
    if (app.globalData.wid == null) {
      wx.login({
        success: function (res) {
          if (res.code) {
            wx.request({
              url: 'https://api.weixin.qq.com/sns/jscode2session',
              data: {
                appid: 'wxbde549988cb6c3b3',
                secret: '9d0dface8393b30a7dc020d41d1d7c7a',
                js_code: res.code,
                grant_type: 'authorization_code'
              },
              success: function (res) {
                var app = getApp(); // 获取 App 实例
                app.globalData.wid = res.data.openid; // 将 openid 存储到全局中  
                wx.request({
                  method: 'POST',
                  url: 'http://127.0.0.1:88/api/ikunchat/wcenter/isregist',
                  header: {
                    'content-type': 'application/json'
                  },
                  data: {
                    "wid": app.globalData.wid
                  },
                  success(res) {
                    // 处理响应数据
                    let r_code = res.data.code
                    if (r_code != 0) {
                      wx.switchTab({
                        url: "pages/mycenter/mycenter"
                      });
                  
                    }else{
                      app.globalData.islogin = true
                      app.globalData.curr_itemid = res.data.itemid

                    }
                  },
                  fail(error) {
                    // 处理请求失败情况
                    console.log('request failed', error);
                  }
                })
              }
            });
          } else {
            console.log('登录失败！' + res.errMsg);
          }
        }
      });
    } 
  },
})