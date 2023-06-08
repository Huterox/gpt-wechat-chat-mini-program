const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    login: {
      show: false,
      avatar: 'https://img0.baidu.com/it/u=3204281136,1911957924&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500',
    },
    count: 10,
    wid: 0,
  },
  // 登录监听
  chooseAvatar(e) {
    console.log(e.detail.avatarUrl)
    var that = this;
    //请求后端服务器完成登录
    this.setData({
      login: {
        show: true,
        avatar: "../index/image/me.png",
      }
    })
    wx.request({
      method: 'POST',
      url: 'http://127.0.0.1:88/api/ikunchat/wcenter/login',
      header: {
        'content-type': 'application/json'
      },
      data: {
        wid: app.globalData.wid
      },
      success(res) {
        // 处理响应数据
        let r_code = res.data.code
        if(r_code==0){
          that.setData({
            login: {
              show: true,
              avatar: "../index/image/me.png",
            }
          })
          app.globalData.islogin = res.data.itemid
        }else{
          wx.showToast({
            title: res.data.msg,
            icon: 'error',
            duration: 2000
          })
        }
      },
      fail(error) {
        // 处理请求失败情况
        wx.showToast({
          title: error,
          icon: 'error',
          duration: 2000
        })
      }
    })
  },
  // 基本信息
  basicClick() {

    console.log(app.globalData.wid)
    var that = this;
    wx.request({
      method: 'POST',
      url: 'http://127.0.0.1:88/api/ikunchat/wcenter/count',
      header: {
        'content-type': 'application/json'
      },
      data: {
        wid: app.globalData.wid
      },
      success(res) {
        // 处理响应数据
        let r_code = res.data.code
        if(r_code==0){
          that.setData({
            count: res.data.msg
          })
          console.log(that.data.count)
          //显示
          wx.showModal({
            title: 'Hello ~',
            content: '剩余次数:' + that.data.count,
            success(res) {
              if (res.confirm) {
                console.log('用户点击了确定按钮')
              } else if (res.cancel) {
                console.log('用户点击了取消按钮')
              }
            }
          })
        }else{
          wx.showToast({
            title: res.data.msg,
            icon: 'error',
            duration: 2000
          })
        }
      },
      fail(error) {
        // 处理请求失败情况
        wx.showToast({
          title: error,
          icon: 'error',
          duration: 2000
        })
      }
    })
    console.log('基本信息监听');
  },
  // 匿名反馈
  feedbackClick() {
    wx.showModal({
      title: 'Huterox',
      content: '有问题随时email:3139541502@qq.com',
      success(res) {
        if (res.confirm) {
          console.log('用户点击了确定按钮')
        } else if (res.cancel) {
          console.log('用户点击了取消按钮')
        }
      }
    })
    console.log('匿名反馈监听');
  },
  // 关于我们
  aboutClick() {
    wx.showModal({
      title: 'Huterox',
      content: '嘿，我是一个练习时长一kun年的代码练习生',
      success(res) {
        if (res.confirm) {
          console.log('用户点击了确定按钮')
        } else if (res.cancel) {
          console.log('用户点击了取消按钮')
        }
      }
    })
    console.log('关于我们监听');
  },
  // 退出监听
  exitClick() {
    let that = this;
    wx.showModal({
      title: '提示',
      content: '确定退出登录吗？',
      success(res) {
        if (res.confirm) {
          that.setData({
            login: {
              show: false,
              avatar: 'https://img0.baidu.com/it/u=3204281136,1911957924&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500',
            }
          })
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var app = getApp(); // 获取 App 实例
    if (app.globalData.islogin){
      this.setData({
        login: {
          show: true,
          avatar: "../index/image/me.png",
        }
      })
      //获取剩余次数
      var that = this;
      wx.request({
        method: 'POST',
        url: 'http://127.0.0.1:88/api/ikunchat/wcenter/count',
        header: {
          'content-type': 'application/json'
        },
        data: {
          wid: app.globalData.wid
        },
        success(res) {
          // 处理响应数据
          let r_code = res.data.code
          if(r_code==0){
            that.setData({
              count: res.data.msg
            })
          }else{
            wx.showToast({
              title: res.data.msg,
              icon: 'error',
              duration: 2000
            })
          }
        },
        fail(error) {
          // 处理请求失败情况
          wx.showToast({
            title: error,
            icon: 'error',
            duration: 2000
          })
        }
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})