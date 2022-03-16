<template>
  <div id="smartTown" class="main">
    <div class="top">
      <div class="img">
        <el-image
          style="width: 100px; height: 100px; border-radius: 50%"
          src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
          fit="cover"
        >
        </el-image>
      </div>
      <div class="name">
        <h3 style="margin-left: 20px">{{student.name}}</h3>
        <h4 style="margin-left: 20px">{{student.clazz}}</h4>
      </div>
    </div>
    <div class="middle">
      <div class="title">基本信息</div>
      <div class="item">
        <div class="left">学号</div>
        <div class="right">{{student.uid}}</div>
      </div>
      <div class="item">
        <div class="left">性 &nbsp;&nbsp;&nbsp;&nbsp; 别</div>
        <div class="right">男</div>
      </div>
      <div class="item">
        <div class="left">邮箱地址</div>
        <div class="right">{{account.email}}</div>
      </div>
      <div class="item">
        <div class="left">个人简介</div>
        <div class="right">哈喽，我是来自湖北的张三，全网轰动的张三；</div>
      </div>
      <div class="item">
        <div class="left">学校名称</div>
        <div class="right">北京大学</div>
      </div>
    </div>
  </div>
</template>
<script>
import { ByUid as studentByUid } from "@/api/student";
import { mapState } from "vuex";
export default {
  name: "smartTown",
  computed: {
    ...mapState({
      account: (state) => state.account,
    }),
  },
  created(){
      console.log("idd:",this.account.accountId)
    this.initStudent();
  },
  data() {
    return {
        student:{
           

        }

    };
  },
  methods: {
      
    initStudent() {
        console.log("this.account:", this.account);
        //this.student.account = this.account
        let uid = this.account.accountId
      studentByUid(uid)
        .then((response) => {
          this.student = response.data;
          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("加载学生信息失败");
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
.top {
  display: flex;
}
.name {
  display: flex;
  flex-direction: column;
}
.middle {
  border: 2px solid #f3f3f3;
  margin-top: 20px;
  padding: 16px;
}
.title {
  font-size: 20px;
  font-weight: 600;
}
.item {
  display: flex;
  margin: 10px 0;
  padding: 16px;
  font-size: 16px;
}
.left {
  margin-right: 40px;
  min-width: 60px;
}
</style>
