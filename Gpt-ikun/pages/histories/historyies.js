Page({
  data: {
    listData: [],
    cur_page: 1,
    scrollViewHeight: 0
  },
  onShow: function () {
    // 初始化数据
    this.loadData();
    // 设置 scroll-view 的高度
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          scrollViewHeight: res.windowHeight - 100
        });
      }
    });
  },
  scrollToLower: function() {
    console.log('scrollToLower');
    this.loadData()
  },

  loadData: function () {
    var that = this;
    var app = getApp(); // 获取 App 实例
    var dataList = that.data.listData;
      //向服务器发送请求，获取结果
    wx.request({
      method: 'POST',
      url: 'http://127.0.0.1:88/api/ikunchat/histories/items',
      header: {
        'content-type': 'application/json'
      },
      data: {
        wid: app.globalData.wid,
        page: that.data.cur_page,
        limit: 10
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
          let totalCount = page.totalCount
          if(totalCount==0){
            wx.showToast({
              title: "没有更多了吖~",
              icon: 'error',
              duration: 2000
            })
          }
          for(var i=0;i<list.length;i++){
            dataList.push(list[i]);
          }
          that.setData({
            listData: dataList
          });
        }
      },
      fail(error) {
        // 处理请求失败情况
        wx.switchTab({
          url: "/pages/mycenter/mycenter"
        });
      }
    })

  },

  // 点击每个 item 时触发该函数
  tapItem: function (e) {
    // 获取当前 item 的 id 值
    var app = getApp();
    var id = e.currentTarget.dataset.id;
    // 跳转到指定页面，并将 id 作为参数传入
    
    app.globalData.curr_itemid = id
    app.globalData.is_new_chat = false

    wx.switchTab({
      url: '/pages/index/index'
    });

  }
})
