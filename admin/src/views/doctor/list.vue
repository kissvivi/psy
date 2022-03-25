<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('doctor:list')"
            @click.native.prevent="getDoctorList"
            >刷新</el-button
          >
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('doctor:add')"
            @click.native.prevent="showAddRoleDialog"
            >添加医生</el-button
          >

          <el-button
            type="primary"
            size="mini"
            icon="el-icon-download"
            v-if="hasPermission('doctor:list')"
            @click.native.prevent="downTmp"
            >下载模板</el-button
          >
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('doctor:list')"
            @click.native.prevent="showUploadDialog = true"
            >一键导入</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <el-table
      :data="doctorList"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="教师编号" align="center" prop="code" />
      <el-table-column label="医生名" align="center" prop="name">
      </el-table-column>
      <!-- <el-table-column label="专业" align="center" prop="specialty" />
      <el-table-column label="班级" align="center" prop="clazz" />
      <el-table-column label="性别" align="center" prop="sex" /> -->
      <el-table-column label="个人介绍" align="center" prop="details" />
      <!-- <el-table-column label="修改时间" align="center" prop="time">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.time) }}</template>
      </el-table-column> -->
      <el-table-column
        label="管理"
        align="center"
        v-if="
          hasPermission('doctor:detail') ||
          hasPermission('doctor:update') ||
          hasPermission('doctor:delete')
        "
      >
        <template slot-scope="scope">
          <!-- <el-button
            type="info"
            size="mini"
            v-if="hasPermission('doctor:detail')"
            @click.native.prevent="showRoleDialog(scope.$index)"
          >查看</el-button> -->
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('doctor:reset')"
            @click.native.prevent="resetPassword(scope.row.id)"
            >重置密码</el-button
          >
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('doctor:delete')"
            @click.native.prevent="removeRole(scope.row.id)"
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

    <!-- 医生添加开始 -->
    <el-dialog
      :title="textMap[dialogStatus]"
      :visible.sync="dialogDoctorFormVisible"
    >
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left: 50px"
        :model="tempDoctor"
        :rules="createRules"
        ref="tempDoctor"
      >
        <el-form-item label="教师编号" prop="code" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempDoctor.code"
          ></el-input>
        </el-form-item>
        <el-form-item label="医生名" prop="name" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempDoctor.name"
          ></el-input>
        </el-form-item>

        <el-form-item label="个人介绍" prop="details" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type=""
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempDoctor.details"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogDoctorFormVisible = false"
          >取消</el-button
        >
        <el-button
          v-if="dialogStatus === 'add'"
          type="success"
          :loading="btnLoading"
          @click.native.prevent="addDoctor"
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
    <!-- 医生添加结束 -->

    <el-dialog title="文件上传" :visible.sync="showUploadDialog">
      <el-upload
        class="upload-demo"
        drag
        action="http://localhost:8080/doctor/importExcel"
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
  list as getDoctorList,
  add as addDoctor,
  resetPassword,
  remove,
} from "@/api/doctor";
import { register} from '@/api/account'
import { unix2CurrentTime } from "@/utils";
import { mapGetters } from "vuex";
export default {
  created() {
    // if (this.hasPermission("doctor:update")) {
    //   this.getPermissionList();
    // }
    // if (this.hasPermission("doctor:list")) {
      this.getDoctorList();
    // }
  },
  data() {
    /**
     * 验证医生名
     * @param rule 规则
     * @param callback 回调
     */
    const validateDoctorName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("医生名不能为空"));
      } else {
        callback();
      }
    };
    /**
     * 验证学号
     * @param rule 规则
     * @param callback 回调
     */
    const validateDoctorCode = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("学号不能为空"));
      } else {
        callback();
      }
    };
    return {
      doctorList: [],
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
      dialogDoctorFormVisible: false,
      textMap: {
        reset: "重置密码",
        add: "添加医生",
      },
      btnLoading: false,
      tmpAccount: {
        email: '',
        name: '',
        password: '',
        roleId: 4 // 对应后端数据库普通用户角色Id
      },
      tempDoctor: {
        id: "",
        name: "",
        code: "",
        avatar:"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fci.xiaohongshu.com%2F06facb5e-c5bb-4393-078a-45bc1e6c3302%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fci.xiaohongshu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1650354662&t=5440894448788633437073c6039caf04",
        password: "",
      },
      createRules: {
        name: [
          { required: true, trigger: "blur", validator: validateDoctorName },
        ],
        code: [
          { required: true, trigger: "blur", validator: validateDoctorCode },
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
     * 获取x医生列表
     */
    getDoctorList() {
      this.listLoading = true;
      getDoctorList(this.listQuery)
        .then((response) => {
          this.doctorList = response.data.list;
          this.total = response.data.total;
          this.listLoading = false;
        })
        .catch((res) => {
          this.$message.error("加载医生列表失败");
        });
    },
    /**
     * 改变每页数量
     * @param size 页大小
     */
    handleSizeChange(size) {
      this.listQuery.page = 1;
      this.listQuery.size = size;
      this.getDoctorList();
    },
    /**
     * 改变页码
     * @param page 页号
     */
    handleCurrentChange(page) {
      this.listQuery.page = page;
      this.getDoctorList();
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
     * 显示新增医生对话框
     */
    showAddRoleDialog() {
      this.dialogDoctorFormVisible = true;
      this.dialogStatus = "add";
      this.tempDoctor.name = "";
      this.tempDoctor.code = "";
      this.tempDoctor.password = "";
    },
    downTmp() {
      window.location.href = process.env.BASE_API + "/doctor/exportTemp";
    },
    /**
     * 添加医生
     */
    addDoctor() {
      this.$refs.tempDoctor.validate((valid) => {
        if (
          valid &&
          this.isDoctorNameUnique(this.tempDoctor.id, this.tempDoctor.code)
        ) {
          this.btnLoading = true;
          this.tempDoctor.uid = this.tempDoctor.code
          addDoctor(this.tempDoctor)
            .then(() => {
              this.$message.success("添加成功");
              this.getDoctorList();
              this.dialogDoctorFormVisible = false;
              this.btnLoading = false;

              this.tmpAccount.id = this.tempDoctor.code
              this.tmpAccount.name = this.tempDoctor.code
              this.tmpAccount.password = this.tempDoctor.code
              this.tmpAccount.email = this.tempDoctor.code+"@doctor.com"

              register(this.tmpAccount).then(() => {
                this.$message.success('添加成功')
                // this.getAccountList()
                // this.dialogFormVisible = false
                // this.btnLoading = false
              }).catch(res => {
                this.$message.error('添加医生账户失败')
                this.btnLoading = false
              })

            })
            .catch((res) => {
              this.$message.error("添加医生失败");
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
    //   this.$refs.tempDoctor.validate(valid => {
    //     if (
    //       valid &&
    //       this.isRoleNameUnique(this.tempDoctor.id, this.tempDoctor.name)
    //     ) {
    //       this.btnLoading = true
    //       updateRole(this.tempDoctor).then(() => {
    //         this.$message.success('更新成功')
    //         this.getDoctorList()
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
          this.getDoctorList();
          this.btnLoading = false;
        })
        .catch((res) => {
          this.$message.error("重置密码失败");
        });
    },
    /**
     * 校验学号是否已经存在
     * @param id 医生id
     * @param code 学号
     * @returns {boolean}
     */
    isDoctorNameUnique(id, code) {
      for (let i = 0; i < this.doctorList.length; i++) {
        if (
          this.doctorList[i].id !== id &&
          this.doctorList[i].code === code
        ) {
          this.$message.error("学号已存在");
          return false;
        }
      }
      return true;
    },
    /**
     * 移除医生
     * @param id 医生id
     * @returns {boolean}
     */
    removeRole(id) {
      this.$confirm("删除该角色？", "警告", {
        confirmButtonText: "是",
        cancelButtonText: "否",
        type: "warning",
      })
        .then(() => {
          const doctorId = id;
          remove(doctorId)
            .then(() => {
              this.$message.success("删除成功");
              this.getDoctorList();
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
        if (this.tempDoctor.permissionIdList.indexOf(handleList[i].id) > -1) {
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
        if (this.tempDoctor.permissionIdList.indexOf(handleList[i].id) < 0) {
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
        this.addUnique(handleList[i].id, this.tempDoctor.permissionIdList);
      }
    },
    /**
     * checkbox全部取消选中
     * @param index 下标
     */
    cancelAll(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        const idIndex = this.tempDoctor.permissionIdList.indexOf(
          handleList[i].id
        );
        if (idIndex > -1) {
          this.tempDoctor.permissionIdList.splice(idIndex, 1);
        }
      }
    },
    /**
     * 本方法会在勾选状态改变之后触发
     * @param item 选项
     * @param index 对应下标
     */
    handleChecked(item, index) {
      if (this.tempDoctor.permissionIdList.indexOf(item.id) > -1) {
        // 选中事件
        // 如果之前未勾选本权限,现在勾选完之后,tempDoctor里就会包含本id
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
        this.addUnique(handleList[i].id, this.tempDoctor.permissionIdList);
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