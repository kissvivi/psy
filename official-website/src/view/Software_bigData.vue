<template>
    <div id="Software" class="main">
        <div class="cards">
            <el-card shadow="hover" class="cardItem" v-for="item in 20" :key="item.id">
                <div class="title">
                    <div class="left">
                        <div class="name">标题</div>
                        <div class="time">2022-01-31</div>
                    </div>
                    <div class="right">
                        <el-tag type="success">状态1</el-tag>
                        <el-tag type="info">状态2</el-tag>
                        <el-tag type="warning">状态3</el-tag>
                        <el-tag type="danger">状态4</el-tag>
                    </div>
                </div>
                <div class="content" >
                    {{ content }}
                </div>
                <div class="assess" v-if="assess">
                    {{ assess }}
                </div>
                <el-button size="small" v-else class="btn" @click="openModal">评价</el-button>
            </el-card>
        </div>
        <el-dialog
        title="评价"
        :visible.sync="dialogVisible"
        width="30%">
            <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="textarea">
            </el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false" size="small">取 消</el-button>
                <el-button type="primary" @click="sumbmitAssess" size="small">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
export default {
    name: 'Software',
    data(){
        return{
            content: "这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容",
            assess: "",
            dialogVisible: false,
            textarea: "",
        }
    },
    methods:{
        openModal(){
            this.dialogVisible = true;
        },
        sumbmitAssess(){
            this.assess = this.textarea;
            this.dialogVisible = false;
        }
    }
}
</script>
<style scoped>
.main{
    min-height: 406px;
    height: fit-content;
}
.cards{
  width: auto;
  height: 600px;
  overflow-y: auto;
}
.cardItem{
    padding: 10px;
    margin: 10px 0;
    margin-right: 20px;
}
.title{
    display: flex;
    justify-content: space-between;
}
.left{
    display: flex;
    text-align: center;
}
.name{
    font-size: 20px;
}
.time{
    font-size: 14px;
    color: #6d6b6b;
    margin: auto 0;
    margin-left: 16px;
}
.content{
    border: 1px solid #EBEEF5;
    min-height: 100px;
    margin: 10px 0;
    padding: 8px;
    border-radius: 4px;
}
.btn{
    float: right;
}
</style>
