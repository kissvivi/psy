<template>
  <div class="chat">
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <div class="panel panel-primary">
            <div class="panel-heading">
              <h3 class="panel-title">当前登录用户</h3>
            </div>
            <div class="panel-body">
              <div class="list-group">
                <a href="#" class="list-group-item"
                  >你好，<span id="user">{{ actions.fromName }}</span></a
                >
                <a href="logout" class="list-group-item">退出</a>
              </div>
            </div>
          </div>
          <!-- <div class="panel panel-primary" id="online">
				  <div class="panel-heading">
				    <h3 class="panel-title">当前在线的其他用户</h3>
				  </div>
				  <div class="panel-body">
				    <div class="list-group" id="users">
					</div>
				  </div>
				</div> -->
          <!-- <div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title">群发系统广播</h3>
				  </div>
				  <div class="panel-body">
				    <input type="text" class="form-control"  id="msg" /><br>
				    <button id="broadcast" type="button" class="btn btn-primary">发送</button>
				  </div>
				</div> -->
        </div>
        <div class="col-md-9">
          <div class="panel panel-primary">
            <div class="panel-heading">
              <h3 class="panel-title" id="talktitle"></h3>
            </div>
            <div class="panel-body">
              <div
                class="well"
                id="log-container"
                style="height: 400px; overflow-y: scroll"
              >
                <div v-for="(item, index) in reActions" :key="index">
                  <div class="bg-info">
                    <label class="text-danger"
                      >{{ item.fromName }}&nbsp;{{ item.date }}</label
                    >
                    <div class="text-success">{{ item.text }}</div>
                  </div>
                  <br />
                </div>
              </div>

              <input
                type="text"
                v-model="input"
                id="myinfo"
                class="form-control col-md-12"
                placeholder="请输入聊天信息"
              />
              <br />
              <div class="right-button">
                <button
                  id="send"
                  @click="sendMessage"
                  type="button"
                  class="btn btn-primary"
                >
                  发送
                </button>
                &nbsp;&nbsp;

                <button
                  id="send"
                  @click="cancelSendMessage"
                  type="button"
                  class="btn btn-primary"
                >
                  取消
                </button>
                &nbsp;&nbsp;
                <button
                  id="endsend"
                  @click="endSendMessage"
                  type="button"
                  class="btn btn-primary"
                >
                  结束咨询
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- <div class="left">
			<h3>{{actions.from}}</h3>
			<div>
				头像|{{actions.date}}
			</div>
			<div>
				内容:<textarea v-model="actions.text"></textarea>
			</div>
			
		</div>

		<div class="right">
			<h3>{{actions.from}}</h3>
			<div>
				头像|{{actions.date}}
			</div>
			<div>
				内容
			</div>
			<textarea v-model="reActions.text"></textarea>
		</div> -->
  </div>
</template>

<script>
import { mapState } from "vuex";
import { updateStatus as updateStatusConsult } from "@/api/consult";
import { ByUid as doctorByUid } from "@/api/doctor";
import { ByUid as studentByUid } from "@/api/student";
import { listByConsultId as chatLogListByConsultId } from "@/api/chatlog";
export default {
  name: "test",
  data() {
    return {
      websock: null,
      statusQuery: {
        status: 0,
        id: 0,
      },
      listQuery: {
        page: 1,
        size: 30,
        cid: 1,
      },
      input: "",
      actions: {
        fromID: "1",
        toID: "2",
        fromName: "",
        toName: "",
        text: "-----------------------------------进入聊天--------------------------------------------",
        date: "",
      },
      reActions: [
        {
          fromID: "1",
          toID: "2",
          fromName: "",
          toName: "",
          text: "",
          date: "",
        },
      ],
    };
  },
  created() {
    this.initWebSocket();
    this.initInfo();
    console.log("id:", this.$route.params.did);
  },
  destroyed() {
    this.websock.close(); //离开路由之后断开websocket连接
  },
  // watch: {
  //   actions： 'changeDataSend' // 值可以为methods的方法名
  // },
  computed: {
    ...mapState({
      account: (state) => state.account,
    }),
  },
  methods: {
    // 初始化基本信息

    initInfo() {
      this.actions.toID = this.$route.params.did;
      console.log("this.account:", this.account);
      this.actions.fromID = this.account.accountId;

      console.log("this.actions.fromID:", this.actions.fromID);

      doctorByUid(Number(this.actions.toID))
        .then((response) => {
          this.actions.toName = response.data.name;
          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("加载医生信息失败");
        });
      studentByUid(Number(this.actions.fromID))
        .then((response) => {
          this.actions.fromName = response.data.name;
          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("加载学生信息失败");
        });

        this.listQuery.uid = Number(this.$route.params.consultId)
        chatLogListByConsultId(this.listQuery)
        .then((response) => {
          this.reActions = response.data.list;
          this.reActions = this.reActions.reverse()
          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("加载聊天记录失败");
        });
    },

    initWebSocket() {
      //初始化weosocket
      const wsuri = "ws://127.0.0.1:8080/webSocket/" +this.$route.params.consultId+"/"+this.account.accountId;
      this.websock = new WebSocket(wsuri);
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
    },
    websocketsend(Data) {
      //数据发送
      //this.input = this.actions.text;
      this.websock.send(JSON.stringify(Data));
    },
    websocketonopen() {
      //连接建立之后执行send方法发送数据
      let actions = this.actions;
      console.log(JSON.stringify(actions), ":5555");
      this.websocketsend(actions);
    },
    websocketonerror() {
      //连接建立失败重连
      this.initWebSocket();
    },
    websocketonmessage(e) {
      //数据接收
      let redata = JSON.parse(e.data);
      this.reActions.push(redata);
    },
    websocketclose(e) {
      //关闭
      console.log("断开连接", e);
    },
    sendMessage() {
      this.actions.text = this.input;
      this.actions.date = this.CurentTime();
      let arr = {
        ...this.actions,
        text: this.input,
        date: this.CurentTime(),
      };
      this.reActions.push(arr);
      this.websocketsend(arr);
      this.input = "";
    },
    //取消咨询
    cancelSendMessage() {
      this.statusQuery.status = 4;
      this.statusQuery.id = this.$route.params.consultId;

      this.updateConsultStatus();

      this.$router.go(-1);
    },
    // 结束咨询
    endSendMessage() {
      this.statusQuery.status = 5;
      this.statusQuery.id = this.$route.params.consultId;

      this.updateConsultStatus();

      this.$router.go(-1);
    },
    // 更新状态
    updateConsultStatus() {
      updateStatusConsult(this.statusQuery)
        .then((response) => {
          this.$message.success("更新状态成功");
        })
        .catch((res) => {
          this.$message.error("更新状态失败");
        });
    },
    CurentTime() {
      var now = new Date();

      var year = now.getFullYear(); //年
      var month = now.getMonth() + 1; //月
      var day = now.getDate(); //日

      var hh = now.getHours(); //时
      var mm = now.getMinutes(); //分
      var ss = now.getSeconds(); //秒

      var clock = year + "-";

      if (month < 10) clock += "0";
      clock += month + "-";

      if (day < 10) clock += "0";
      clock += day + " ";

      if (hh < 10) clock += "0";
      clock += hh + ":";

      if (mm < 10) clock += "0";
      clock += mm + ":";

      if (ss < 10) clock += "0";
      clock += ss;

      return clock;
    },
    // changeDataSend(){
    // 	this.input
    // 	websocketsend(this.actions);
    // }
  },
};
</script>
<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
}

.chat {
  display: flex;
  flex-direction: column;
}

.left {
  display: flex;
  justify-content: flex-start;
}

.right {
  display: flex;
  justify-content: flex-end;
}
.right-button {
  padding-top: 26px;
  display: flex;
  justify-content: flex-end;
}
</style>
