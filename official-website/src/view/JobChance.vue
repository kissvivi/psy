<template>
  <div id="JobChance">
    <div class="banner container-fuild text-center">问卷调查</div>
    <div class="container">
      <div class="JobChance-container wow pulse">
        <h3 style="text-align: center; margin-bottom: 50px">
          关于校园心理咨询的问卷调查
        </h3>

        <el-form
          ref="form"
          :inline="true"
          :model="form"
          labelPosition="left"
          label-width="50px"
        >
          <el-form-item label="学号" prop="scode" label-position="left">
            <el-input v-model="form.scode"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="sname" label-position="left">
            <el-input v-model="form.sname"></el-input>
          </el-form-item>
        </el-form>
        <el-form
          ref="form"
          :model="form"
          labelPosition="top"
          label-width="80px"
        >
          <el-form-item
            v-for="(item, index) in questions.testsList"
            :key="index"
          >
            <h4>{{ index + 1 }}.{{ item.title }}</h4>
            <el-radio-group v-model="item.selectOk">
              <el-radio label="A">A: {{ item.selectA }}</el-radio
              ><br /><br />
              <el-radio label="B">B: {{ item.selectB }}</el-radio
              ><br /><br />
              <el-radio label="C">C: {{ item.selectC }}</el-radio
              ><br /><br />
              <el-radio label="D">D: {{ item.selectD }}</el-radio
              ><br /><br />
            </el-radio-group>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSubmit">提交问卷</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import { WOW } from "wowjs";
import { testsList as testsList } from "@/api/question";
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
      testsList()
        .then((response) => {
          this.questions = response.data;
          console.log("response", response.data);
        })
        .catch((res) => {
          this.$message.error("加载信息失败");
        });
    },

    onSubmit() {
      let tList = [ ...this.questions.testsList ];
      let checkes = [];
      let tbids = [];


      tList.forEach(function (element, index, array) {
        console.log(element);
        checkes.push(element.selectOk)
        tbids.push(element.id)


      });
      this.form.selfChecks = [...checkes].join(',')
      this.form.tbids = [...tbids].join(',')
      this.form.sid = this.account.accountId
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
  margin: 100px;
  padding: 30px;
  transition: all ease 0.5s;
  border: 1px dashed salmon;
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
</style>

