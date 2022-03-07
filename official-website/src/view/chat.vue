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
								<a href="#" class="list-group-item">你好，<span id="user">{{actions.from}}</span></a>
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
							<div class="well" id="log-container" style="height:400px;overflow-y:scroll">

								<div v-for="(item,index) in reActions" :key="index">
									<div class='bg-info'>
										<label class='text-danger'>{{item.from}}&nbsp;{{item.date}}</label>
										<div class='text-success'>{{item.text}}</div>
									</div>
									<br>
								</div>
							</div>

							<input type="text" v-model="input" id="myinfo" class="form-control col-md-12" /> <br>
							<button id="send" @click="sendMessage" type="button"
								class="btn btn-primary">发送</button>
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
	export default {
		name: 'test',
		data() {
			return {
				websock: null,
				input: "请输入聊天信息",
				actions: {
					from: "2",
					to: "1",
					text: "请输入聊天信息",
					date: ""
				},
				reActions: [{
					from: "",
					to: "",
					text: "",
					date: ""
				}]
			}
		},
		created() {
			this.initWebSocket();
		},
		destroyed() {
			this.websock.close() //离开路由之后断开websocket连接
		},
		// watch: {
		//   actions： 'changeDataSend' // 值可以为methods的方法名
		// },
		methods: {
			initWebSocket() { //初始化weosocket
				const wsuri = "ws://127.0.0.1:8080/webSocket/" + this.actions.from;
				this.websock = new WebSocket(wsuri);
				this.websock.onmessage = this.websocketonmessage;
				this.websock.onopen = this.websocketonopen;
				this.websock.onerror = this.websocketonerror;
				this.websock.onclose = this.websocketclose;
			},
			websocketonopen() { //连接建立之后执行send方法发送数据
				let actions = this.actions;
				this.websocketsend(JSON.stringify(actions));
			},
			websocketonerror() { //连接建立失败重连
				this.initWebSocket();
			},
			websocketonmessage(e) { //数据接收
				let redata = JSON.parse(e.data);
				this.reActions.push(redata);
			},
			websocketsend(Data) { //数据发送
				//this.input = this.actions.text;
				this.websock.send(Data);
			},
			websocketclose(e) { //关闭
				console.log('断开连接', e);
			},
			sendMessage() {
				this.actions.text=this.input
				this.reActions.push(actions);
				console.log(this.actions)
				websocketsend(this.actions)
			}
			// changeDataSend(){
			// 	this.input
			// 	websocketsend(this.actions);
			// }
		},
	}
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
		clear: both
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
</style>
