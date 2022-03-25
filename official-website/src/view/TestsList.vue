<template>
  <div id="JobChance">
    <div class="banner container-fuild text-center">问卷调查列表</div>
    <div class="container">
      <div class="JobChance-container wow pulse">
        <el-card class="box-card"  v-for="item in questionsList"
        :key="item.id">
          <div slot="header" class="clearfix">
            <span>{{item.title}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="toQuestion(item)"
              >点击答题</el-button
            >
          </div>
          <div  class="text item">
            {{item.detail}}
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>
<script>
import { WOW } from "wowjs";
import { testsList as testsList,list as questionList } from "@/api/question";
import { add as addtestStudent } from "@/api/testStudent";
import { mapState } from "vuex";
export default {
  name: "JobChance",
  data() {
    return {
      form: {
        sid: 1,
        sname: "",
        selfChecks: "",
        tbids: "",
      },
       questionsList:[],
      questions: {
        question: {},
        testsList: [
          {
            title: "zz",
            selectA: "2321",
            selectB: "321",
            selectC: "321",
            selectD: "321",
            selectOk: null,
          },
        ],
      },
    };
  },
  computed: {
    ...mapState({
      account: (state) => state.account,
    }),
  },
  created() {
    this.initQuestion();
  },
  methods: {
    initQuestion() {
      questionList()
        .then((response) => {
          this.questionsList = response.data.list;
          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("加载信息失败");
        });
    },

    toQuestion(row){
        this.$router.push({
        path: `/jobchance/${row.id}`,
      });
    },

    onSubmit() {
      let tList = [...this.questions.testsList];
      let checkes = [];
      let tbids = [];

      tList.forEach(function (element, index, array) {
        console.log(element);
        checkes.push(element.selectOk);
        tbids.push(element.id);
      });
      this.form.selfChecks = [...checkes].join(",");
      this.form.tbids = [...tbids].join(",");
      this.form.sid = this.account.accountId;
      console.log(this.form);

      addtestStudent(this.form)
        .then((response) => {
          this.$message.success("添加信息成功");
          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("添加信息失败");
        });
    },
  },
  mounted() {
    var wow = new WOW();
    wow.init();
  },
};
</script>
<style scoped>
.banner {
  color: #fff;
  font-size: 30px;
  height: 150px;
  line-height: 150px;
  background-image: url("../assets/img/banner_2.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: scroll;
  background-position: center center;
}
.JobChance-container {
  margin: 50px;
  padding: 30px;
  transition: all ease 0.5s;
}
.JobChance-container h2 {
  color: rgb(255, 102, 19);
  font-weight: bolder;
  text-align: center;
}
/* .JobChance-container p{
font-size: 20px;
  color: rgb(255, 102, 19);
  font-weight: 700;
} */
.JobChance-container ol {
  list-style-type: decimal;
  padding-left: 30px;
}
.JobChance-container ol > li {
  font-size: 14px;
  line-height: 2.7rem;
}
@media screen and (max-width: 997px) {
  .JobChance-container {
    margin: 20px 0;
    padding: 20px;
    border: 1px dashed salmon;
  }
}

.box-card {
    margin: 15px;
}


</style>

