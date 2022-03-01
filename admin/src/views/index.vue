<template>
	<div>
		<el-container class="main">
			<el-header>

				<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
					<el-menu-item index="1">首页</el-menu-item>
					<el-menu-item index="2">医生列表</el-menu-item>
					<el-submenu index="3">
						<template slot="title">个人中心</template>
						<el-menu-item index="2-1">个人资料</el-menu-item>
						<el-menu-item index="2-2">选项2</el-menu-item>
						<el-menu-item index="2-3">选项3</el-menu-item>
					</el-submenu>
					<el-menu-item index="4">预约记录</el-menu-item>
				</el-menu>





			</el-header>
			<el-main>
				<el-carousel indicator-position="outside" height="380px">
					<el-carousel-item v-for="item in carousel" :key="item">
						<img class = "img1" :src="item.url"  alt="心理咨询" />
					</el-carousel-item>
				</el-carousel>


				<el-row :gutter="20">
					<el-col :span="6" v-for="item in doctor" :key="item">
						<div class="grid-content bg-purple" >
							<img class = "img2" :src="item.url"  alt="心理咨询" >  <el-button class="button-1">点击预约咨询</el-button></img>
							
						</div>
					</el-col>
				</el-row>


			</el-main>


			<el-footer class="footer">校园心理资讯平台</el-footer>

		</el-container>
	</div>
</template>
<script>
	export default {
		name: 'index',
		data() {
			return {
				carousel: [{
					url: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200219%2Fc189824b5f4348d1ba499f91083ea534.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648707563&t=93ce1abf8317973cab03911c28526f32"
				}, {
					url: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F14542112075%2F1000&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648707424&t=d8aa50d5b9c008dfffcf5a17d063448e"
				}],
				doctor: [{
					url: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202006%2F28%2F20200628102755_pmgzl.thumb.1000_0.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648708673&t=556b753ac531cabc25fe748ffdce78a3"
				}, {
					url: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic2.zhimg.com%2F80%2Fv2-278b0afb759d4aabb02e0482d86e7169_720w.jpg%3Fsource%3D1940ef5c&refer=http%3A%2F%2Fpic2.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648708820&t=05f515f2c629b650f8011e0eb6fdbac0"
				}]
			}
		},
		methods: {

		}
	}
</script>
<style>
	.main {

		margin: 36px 186px 36px 186px;
	}

	.footer {
		text-align: center;
	}

	.el-carousel__item h3 {
		color: #475669;
		font-size: 18px;
		opacity: 0.75;
		line-height: 300px;
		margin: 0;
	}

	.el-carousel__item:nth-child(2n) {
		background-color: #99a9bf;
	}

	.el-carousel__item:nth-child(2n+1) {
		background-color: #d3dce6;
	}

	.el-row {

		/* margin-bottom: 20px; */
		&:last-child {
			margin-bottom: 0;
		}
	}

	.el-col {
		/* border-radius: 4px; */
	}

	.bg-purple-dark {
		background: #99a9bf;
	}

	.bg-purple {
		/* background: #d3dce6; */
	}

	.bg-purple-light {
		background: #e5e9f2;
	}

	.grid-content {
		/* margin-top: 16px; */
		/* border-radius: 4px; */
		/* min-height: 256px; */
		
	}

	.row-bg {
		/* padding: 10px 0; */
		background-color: #f9fafc;
	}
	.img1{
		width: 100%;
		height: auto;
		max-width: 100%;
		max-height: 100%;	
	}
	.img2{
		width: auto;
		height: 100%;
		max-width: 100%;
		max-height: 100%;	
	}
</style>
