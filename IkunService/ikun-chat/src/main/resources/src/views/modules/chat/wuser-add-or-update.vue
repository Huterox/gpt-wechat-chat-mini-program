<template>
  <el-dialog
    :title="!dataForm.uid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="微信id" prop="wid">
      <el-input v-model="dataForm.wid" placeholder="微信id"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickName">
      <el-input v-model="dataForm.nickName" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item label="电话" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="电话"></el-input>
    </el-form-item>
    <el-form-item label="注册" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="注册"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          wid: '',
          nickName: '',
          phone: '',
          createTime: '',
          uid: 0,
        },
        dataRule: {
          wid: [
            { required: true, message: '微信id不能为空', trigger: 'blur' }
          ],
          nickName: [
            { required: true, message: '昵称不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '电话不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '注册不能为空', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.uid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.uid) {
            this.$http({
              url: this.$http.adornUrl(`/chat/wuser/info/${this.dataForm.uid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.wid = data.wUser.wid
                this.dataForm.nickName = data.wUser.nickName
                this.dataForm.phone = data.wUser.phone
                this.dataForm.createTime = data.wUser.createTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/chat/wuser/${!this.dataForm.uid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'wid': this.dataForm.wid,
                'nickName': this.dataForm.nickName,
                'phone': this.dataForm.phone,
                'createTime': this.dataForm.createTime,
                'uid': this.dataForm.uid || undefined,
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
