<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('question:list')"
            @click.native.prevent="getQuestionList"
            >刷新</el-button>
          <!-- <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('question:add')"
            @click.native.prevent="showAddRoleDialog"
            >添加问卷</el-button
          >

          <el-button
            type="primary"
            size="mini"
            icon="el-icon-download"
            v-if="hasPermission('question:list')"
            @click.native.prevent="downTmp"
            >下载模板</el-button
          >
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('question:list')"
            @click.native.prevent="showUploadDialog = true"
            >一键导入</el-button
          > -->
        </el-form-item>
      </el-form>
    </div>
    <el-table
      :data="questionList"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="题目列表" align="center" prop="testIds">
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="问卷介绍" align="center" prop="detail" />
      <!-- <el-table-column label="修改时间" align="center" prop="time">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.time) }}</template>
      </el-table-column> -->
      <el-table-column
        label="管理"
        align="center"
        v-if="
          hasPermission('question:detail') ||
          hasPermission('question:update') ||
          hasPermission('question:delete')
        "
      >
        <template slot-scope="scope">
          <!-- <el-button
            type="info"
            size="mini"
            v-if="hasPermission('question:detail')"
            @click.native.prevent="showRoleDialog(scope.$index)"
          >查看</el-button> -->
          <!-- <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('question:reset')"
            @click.native.prevent="resetPassword(scope.row.id)"
            >重置密码</el-button
          > -->
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('question:delete')"
            @click.native.prevent="removeQuestion(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.size"
      :total="total"
      :page-sizes="[9, 18, 36, 72]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>

    <!-- 问卷添加开始 -->
    <el-dialog
      :title="textMap[dialogStatus]"
      :visible.sync="dialogQuestionFormVisible"
    >
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left: 50px"
        :model="tempQuestion"
        :rules="createRules"
        ref="tempQuestion"
      >
        <el-form-item label="学号" prop="code" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempQuestion.code"
          ></el-input>
        </el-form-item>
        <el-form-item label="问卷名" prop="name" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempQuestion.name"
          ></el-input>
        </el-form-item>

        <el-form-item label="专业" prop="name" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempQuestion.specialty"
          ></el-input>
        </el-form-item>

        <el-form-item label="班级" prop="name" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempQuestion.clazz"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="tempQuestion.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="个人介绍" prop="name" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type=""
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempQuestion.detail"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogQuestionFormVisible = false"
          >取消</el-button
        >
        <el-button
          v-if="dialogStatus === 'add'"
          type="success"
          :loading="btnLoading"
          @click.native.prevent="addQuestion"
          >添加</el-button
        >
        <el-button
          v-if="dialogStatus === 'update'"
          type="primary"
          :loading="btnLoading"
          @click.native.prevent="updateRole"
          >更新</el-button
        >
      </div>
    </el-dialog>
    <!-- 问卷添加结束 -->

    <el-dialog title="文件上传" :visible.sync="showUploadDialog">
      <el-upload
        class="upload-demo"
        drag
        action="http://localhost:8080/question/importExcel"
        multiple
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">
          只能上传xls/xlsx文件，且不超过500kb
        </div>
      </el-upload>
    </el-dialog>
  </div>
</template>
<script>
import {
  list as getQuestionList,
  add as addQuestion,
  resetPassword,
  remove,
} from "@/api/question";
import {
  listRoleWithPermission,
  listResourcePermission,
  add as addRole,
  update as updateRole,
} from "@/api/role";
import { unix2CurrentTime } from "@/utils";
import { mapGetters } from "vuex";
export default {
  created() {
    // if (this.hasPermission("question:update")) {
    //   this.getPermissionList();
    // }
    // if (this.hasPermission("question:list")) {
      this.getQuestionList();
    // }
  },
  data() {
    /**
     * 验证问卷名
     * @param rule 规则
     * @param callback 回调
     */
    const validateQuestionName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("问卷名不能为空"));
      } else {
        callback();
      }
    };
    /**
     * 验证学号
     * @param rule 规则
     * @param callback 回调
     */
    const validateQuestionCode = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("学号不能为空"));
      } else {
        callback();
      }
    };
    return {
      questionList: [],
      permissionList: [],
      listLoading: false,
      total: 0,
      listQuery: {
        page: 1,
        size: 9,
      },
      dialogStatus: "add",
      dialogFormVisible: false,
      showUploadDialog: false,
      dialogQuestionFormVisible: false,
      textMap: {
        reset: "重置密码",
        add: "添加问卷",
      },
      btnLoading: false,
      tempQuestion: {
        id: "",
        name: "",
        code: "",
        password: "",
      },
      createRules: {
        name: [
          { required: true, trigger: "blur", validator: validateQuestionName },
        ],
        code: [
          { required: true, trigger: "blur", validator: validateQuestionCode },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["roleName"]),
  },
  methods: {
    unix2CurrentTime,
    /**
     * 获取x问卷列表
     */
    getQuestionList() {
      this.listLoading = true;
      getQuestionList(this.listQuery)
        .then((response) => {
          this.questionList = response.data.list;
          this.total = response.data.total;
          this.listLoading = false;
        })
        .catch((res) => {
          this.$message.error("加载问卷列表失败");
        });
    },
    /**
     * 改变每页数量
     * @param size 页大小
     */
    handleSizeChange(size) {
      this.listQuery.page = 1;
      this.listQuery.size = size;
      this.getQuestionList();
    },
    /**
     * 改变页码
     * @param page 页号
     */
    handleCurrentChange(page) {
      this.listQuery.page = page;
      this.getQuestionList();
    },
    /**
     * 表格序号
     * @param index 数据下标
     * @returns 表格序号
     */
    getTableIndex(index) {
      return (this.listQuery.page - 1) * this.listQuery.size + index + 1;
    },
    /**
     * 显示新增问卷对话框
     */
    showAddRoleDialog() {
      this.dialogQuestionFormVisible = true;
      this.dialogStatus = "add";
      this.tempQuestion.name = "";
      this.tempQuestion.code = "";
      this.tempQuestion.password = "";
    },
    downTmp() {
      window.location.href = process.env.BASE_API + "/question/exportTemp";
    },
    /**
     * 添加问卷
     */
    addQuestion() {
      this.$refs.tempQuestion.validate((valid) => {
        if (
          valid &&
          this.isQuestionNameUnique(this.tempQuestion.id, this.tempQuestion.code)
        ) {
          this.btnLoading = true;
          addQuestion(this.tempQuestion)
            .then(() => {
              this.$message.success("添加成功");
              this.getQuestionList();
              this.dialogQuestionFormVisible = false;
              this.btnLoading = false;
            })
            .catch((res) => {
              this.$message.error("添加问卷失败");
            });
        } else {
          console.log("表单无效");
        }
      });
    },
    // /**
    //  * 修改角色
    //  */
    // updateRole() {
    //   this.$refs.tempQuestion.validate(valid => {
    //     if (
    //       valid &&
    //       this.isRoleNameUnique(this.tempQuestion.id, this.tempQuestion.name)
    //     ) {
    //       this.btnLoading = true
    //       updateRole(this.tempQuestion).then(() => {
    //         this.$message.success('更新成功')
    //         this.getQuestionList()
    //         this.dialogFormVisible = false
    //         this.btnLoading = false
    //       }).catch(res => {
    //         this.$message.error('更新角色失败')
    //       })
    //     } else {
    //       console.log('表单无效')
    //     }
    //   })
    // },
    /**
     * 重置密码
     * @param {Object} id
     */
    resetPassword(id) {
      resetPassword(id)
        .then(() => {
          this.$message.success("重置密码成功");
          this.getQuestionList();
          this.btnLoading = false;
        })
        .catch((res) => {
          this.$message.error("重置密码失败");
        });
    },
    /**
     * 校验学号是否已经存在
     * @param id 问卷id
     * @param code 学号
     * @returns {boolean}
     */
    isQuestionNameUnique(id, code) {
      for (let i = 0; i < this.questionList.length; i++) {
        if (
          this.questionList[i].id !== id &&
          this.questionList[i].code === code
        ) {
          this.$message.error("学号已存在");
          return false;
        }
      }
      return true;
    },
    /**
     * 移除问卷
     * @param id 问卷id
     * @returns {boolean}
     */
    removeQuestion(row) {

      console.log("-------",row)

      this.$confirm("删除该问卷？", "警告", {
        confirmButtonText: "是",
        cancelButtonText: "否",
        type: "warning",
      })
        .then(() => {
          const code = Number(row.id);
          remove(code)
            .then(() => {
              this.$message.success("删除成功");
              this.getQuestionList();
            })
            .catch(() => {
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    /**
     * 判断角色菜单内的权限是否一个都没选
     * @param index 下标
     * @returns {boolean}
     */
    isMenuNone(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        if (this.tempQuestion.permissionIdList.indexOf(handleList[i].id) > -1) {
          return false;
        }
      }
      return true;
    },
    /**
     * 判断角色菜单内的权限是否全选了
     * @param index 下标
     * @returns {boolean}
     */
    isMenuAll(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        if (this.tempQuestion.permissionIdList.indexOf(handleList[i].id) < 0) {
          return false;
        }
      }
      return true;
    },
    /**
     * 根据菜单状态check所有checkbox
     * @param index 下标
     */
    checkAll(index) {
      if (this.isMenuAll(index)) {
        // 如果已经全选了,则全部取消
        this.cancelAll(index);
      } else {
        // 如果尚未全选,则全选
        this.selectAll(index);
      }
    },
    /**
     * checkbox全部选中
     * @param index 下标
     */
    selectAll(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        this.addUnique(handleList[i].id, this.tempQuestion.permissionIdList);
      }
    },
    /**
     * checkbox全部取消选中
     * @param index 下标
     */
    cancelAll(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        const idIndex = this.tempQuestion.permissionIdList.indexOf(
          handleList[i].id
        );
        if (idIndex > -1) {
          this.tempQuestion.permissionIdList.splice(idIndex, 1);
        }
      }
    },
    /**
     * 本方法会在勾选状态改变之后触发
     * @param item 选项
     * @param index 对应下标
     */
    handleChecked(item, index) {
      if (this.tempQuestion.permissionIdList.indexOf(item.id) > -1) {
        // 选中事件
        // 如果之前未勾选本权限,现在勾选完之后,tempQuestion里就会包含本id
        // 那么就要将必选的权限勾上
        this.makePermissionChecked(index);
      } else {
        // 取消选中事件
        this.cancelAll(index);
      }
    },
    /**
     * 将角色菜单必选的权限勾上（这里并没有做必选的数据库字段）
     * @param index 权限对应下标
     */
    makePermissionChecked(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        this.addUnique(handleList[i].id, this.tempQuestion.permissionIdList);
      }
    },
    /**
     * 数组内防重复地添加元素
     * @param val 值
     * @param arr 数组
     */
    addUnique(val, arr) {
      const _index = arr.indexOf(val);
      if (_index < 0) {
        arr.push(val);
      }
    },
  },
};
</script>