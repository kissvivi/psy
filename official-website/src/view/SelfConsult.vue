<template>
  <div id="Software" class="main">
    <div class="cards">
      <el-card
        shadow="hover"
        class="cardItem"
        v-for="item in consultList.list"
        :key="item.id"
      >
        <div class="title">
          <div class="left">
            <div class="name">{{ item.title }}</div>
            <div class="time">{{ getLocalTime(item.createTime) }}</div>
          </div>
          <div class="right">
            <el-tag type="success" v-if="item.status">{{
              [
                "",
                "提交申请",
                "申请已通过",
                "驳回申请",
                "咨询中",
                "咨询完成",
                "咨询完成",
              ][item.status]
            }}</el-tag>
          </div>
        </div>
        <div class="content">
          {{ item.content }}
        </div>
        <div class="assess" v-if="assess">
          {{ item.assess }}
        </div>
        <el-button
          size="small"
          class="btn"
          @click="toChat(item)"
          v-if="item.status == 2 || item.status == 4"
          >点击咨询</el-button
        >

        <el-button
          size="small"
          class="btn"
          @click="openModal(item)"
          icon="el-icon-chat-dot-round"
          v-if="(item.status == 5 || item.status == 6) && item.assess == null"
          >评价</el-button
        >
        <el-button
          size="small"
          class="btn"
          type="warning"
          icon="el-icon-download"
          @click="downExcel(item)"
          v-if="item.status == 6"
          >下载报告</el-button
        >
      </el-card>
    </div>
    <el-dialog title="评价" :visible.sync="dialogVisible" width="30%">
      <el-input
        type="textarea"
        :autosize="{ minRows: 2, maxRows: 4 }"
        placeholder="请输入内容"
        v-model="textarea"
      >
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="sumbmitAssess" size="small"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { listBySid as listBySidConsult,update} from "@/api/consult";
export default {
  name: "Software",
  data() {
    return {
      
      listQuery: {
        page: 1,
        size: 9,
        sid: 5,
        did: 1,
      },
      consult:{
        consultId:0,
        assess:""
      },
      consultList: [],
      content:
        "这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容",
      assess: "",
      dialogVisible: false,
      textarea: "",
    };
  },
  created() {
    this.initConsult();
  },
  methods: {
    initConsult() {
      console.log(111);
      listBySidConsult(this.listQuery)
        .then((response) => {
          this.consultList = response.data;

          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("加载咨询列表失败");
        });
    },
    getLocalTime(timestamp) {
      //num:0 YYYY-MM-DD  num:1  YYYY-MM-DD hh:mm:ss // timestamp:时间戳
      //将时间戳转换成正常时间格式
      var date = new Date(timestamp); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + "-";
      var M =
        (date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1) + "-";
      var D = date.getDate() + " ";
      // var h = date.getHours() + ":";
      // var m =
      //   (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) +
      //   ":";
      // var s = date.getSeconds();
      return Y + M + D;
    },
    openModal(row) {
      this.dialogVisible = true;
      this.consult.consultId = row.id
    },
    //下载上传的文件
    downExcel(row) {
      window.location.href =
        //"http://localhost:8080/consult/upload/downLoadTemplateExcel";
        "http://localhost:8080/consult/upload/" + row.id + "/download";
    },
    sumbmitAssess() {
      this.assess = this.textarea;
      this.dialogVisible = false;
      this.consult.assess = this.textarea;

      update(this.consult)
        .then((response) => {
          this.consultList = response.data;

          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("评价失败");
        });

    },
    toChat(row) {
      this.$router.push({
        path: `/toChat/${row.id}/${row.did}`,
      });
    },
  },
};
</script>
<style scoped>
.main {
  min-height: 406px;
  height: fit-content;
}
.cards {
  width: auto;
  height: 600px;
  overflow-y: auto;
}
.cardItem {
  padding: 10px;
  margin: 10px 0;
  margin-right: 20px;
}
.title {
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
  text-align: center;
}
.name {
  font-size: 20px;
}
.time {
  font-size: 14px;
  color: #6d6b6b;
  margin: auto 0;
  margin-left: 16px;
}
.content {
  border: 1px solid #ebeef5;
  min-height: 100px;
  margin: 10px 0;
  padding: 8px;
  border-radius: 4px;
}
.btn {
  float: right;
  margin-left: 6px;
}
</style>
