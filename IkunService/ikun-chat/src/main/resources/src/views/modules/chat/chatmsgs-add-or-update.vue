<template>
  <el-dialog
    :title="!dataForm.msgid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="itemid" prop="itemsid">
      <el-input v-model="dataForm.itemsid" placeholder="itemid"></el-input>
    </el-form-item>
    <el-form-item label="内容" prop="message">
      <el-input v-model="dataForm.message" placeholder="内容"></el-input>
    </el-form-item>
    <el-form-item label="时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="时间"></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="uid">
      <el-input v-model="dataForm.uid" placeholder="用户id"></el-input>
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
          itemsid: '',
          message: '',
          createTime: '',
          uid: '',
          msgid: 0,
        },
        dataRule: {
          itemsid: [
            { required: true, message: 'itemid不能为空', trigger: 'blur' }
          ],
          message: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '时间不能为空', trigger: 'blur' }
          ],
          uid: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.msgid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.msgid) {
            this.$http({
              url: this.$http.adornUrl(`/chat/chatmsgs/info/${this.dataForm.msgid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.itemsid = data.chatMsgs.itemsid
                this.dataForm.message = data.chatMsgs.message
                this.dataForm.createTime = data.chatMsgs.createTime
                this.dataForm.uid = data.chatMsgs.uid
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
              url: this.$http.adornUrl(`/chat/chatmsgs/${!this.dataForm.msgid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'itemsid': this.dataForm.itemsid,
                'message': this.dataForm.message,
                'createTime': this.dataForm.createTime,
                'uid': this.dataForm.uid,
                'msgid': this.dataForm.msgid || undefined,
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
