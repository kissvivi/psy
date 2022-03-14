<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="
              hasPermission('consult:list') || hasPermission('consult:dlist')
            "
            @click.native.prevent="initList"
            >刷新</el-button
          >

          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="
              hasPermission('consult:list') || hasPermission('consult:dlist')
            "
            @click.native.prevent="downTemp"
            >下载报告模板</el-button
          >
          <!-- <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('role:add')"
            @click.native.prevent="showAddConsultDialog"
          >添加咨询</el-button> -->
        </el-form-item>
      </el-form>
    </div>
    <el-table
      :data="consultList"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="#" align="center" width="80">
        <template slot-scope="scope">
          <span v-text="getTableIndex(scope.$index)"></span>
        </template>
      </el-table-column>
      <el-table-column label="咨询标题" align="center" prop="title" />
      <el-table-column label="咨询详情" align="center" prop="content" />

      <!-- 1:提交申请，2:通过申请，3：驳回申请，4：咨询中，5：咨询完成， -->

      <el-table-column label="咨询进度" align="center" prop="status">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === 1"
            type="primary"
            @click.native.prevent="showConsultDialog(scope.row)"
            disable-transitions
            >申请咨询（点击处理）</el-button
          >

          <el-button
            v-if="scope.row.status === 2"
            size="mini"
            @click.native.prevent="toChart(scope.row)"
            disable-transitions
            >通过申请（点击开始咨询）</el-button
          >
          <el-tag
            v-if="scope.row.status === 3"
            type="success"
            disable-transitions
            >已驳回</el-tag
          >

          <el-button
            v-if="scope.row.status === 4"
            size="mini"
            @click.native.prevent="toChart(scope.row)"
            disable-transitions
            >咨询中（点击继续咨询）</el-button
          >

          <el-button
            v-if="scope.row.status === 5"
            type="success"
            disable-transitions
            @click.native.prevent="uploadExcel"
            >咨询完成（上传报告）</el-button
          >

          <el-tag
            v-if="scope.row.status === 6"
            type="success"
            disable-transitions
            >咨询完成（已上传报告）</el-tag
          >
          <!-- <el-tag
                  :type="scope.row.status === 1 ? 'primary' : 'success'"
                  disable-transitions>{{scope.row.status === 1 ? '未发布' : '已发布'}}</el-tag> --></template
        >
      </el-table-column>

      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">{{
          unix2CurrentTime(scope.row.createTime)
        }}</template>
      </el-table-column>
      <el-table-column label="修改时间" align="center" prop="updateTime">
        <template slot-scope="scope">{{
          unix2CurrentTime(scope.row.updateTime)
        }}</template>
      </el-table-column>
      <!-- <el-table-column
        label="管理"
        align="center"
        v-if="
          hasPermission('role:detail') ||
          hasPermission('role:update') ||
          hasPermission('role:delete')
        "
      >
        <template slot-scope="scope">
          <el-button
            type="info"
            size="mini"
            v-if="hasPermission('role:detail')"
            @click.native.prevent="showConsultDialog(scope.$index)"
          >查看</el-button>
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('role:update') && scope.row.name !== '超级管理员'"
            @click.native.prevent="showUpdateConsultDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="
              hasPermission('role:delete') && scope.row.name !== '超级管理员'
            "
            @click.native.prevent="removeConsult(scope.$index)"
            >删除</el-button
          >
        </template>
      </el-table-column> -->
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      {{ tempConsult.title }}
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false"
          >取消</el-button
        >
        <el-button
          type="success"
          :loading="btnLoading"
          @click.native.prevent="updateConsultStatusTwo(row)"
          >通过申请</el-button
        >
        <el-button
          type="primary"
          :loading="btnLoading"
          @click.native.prevent="updateConsultStatusThree(row)"
          >驳回申请</el-button
        >
      </div>
    </el-dialog>
    <!-- 文件上传 -->
    <el-dialog title="文件上传" :visible.sync="showUploadDialog">
    <el-upload
      class="upload-demo"
      drag
      action="/psyapi/consult/upload/`${}`"
      multiple>
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件，且不超过500kb</div>
    </el-upload>
    </el-dialog>
  </div>
</template>
<script>
import {
  list as listConsult,
  listByDid as listByDidConsult,
  updateStatus as updateStatusConsult,
  add as addConsult,
  update as updateConsult,
  remove as removeConsult,
} from "@/api/consult";
import { unix2CurrentTime } from "@/utils";
import { mapGetters } from "vuex";
import { mapState } from "vuex";

export default {
  created() {

    this.initList()
  },
  data() {
    /**
     * 验证咨询名
     * @param rule 规则
     * @param value 咨询名
     * @param callback 回调
     */
    const validateConsultName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("咨询名不能为空"));
      } else {
        callback();
      }
    };
    return {
      consultList: [],
      // permissionList: [],
      listLoading: false,
      showUploadDialog: false,
      total: 0,
      listQuery: {
        page: 1,
        size: 9,
        sid: 1,
        did: 1,
      },
      statusQuery: {
        status: 0,
        id: 0,
      },
      dialogStatus: "apply",
      dialogFormVisible: false,
      textMap: {
        // update: '修改咨询',
        apply: "申请处理",
      },
      btnLoading: false,
      tempConsult: {
        id: "",
        title: "",
      },
      createRules: {
        name: [
          { required: true, trigger: "blur", validator: validateConsultName },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["roleName"]),
    ...mapState({
      account: (state) => state.account,
    }),
  },
  methods: {
    unix2CurrentTime,

    initList() {
      if (this.hasPermission("consult:list")) {
        this.getConsultList();
      } else if (this.hasPermission("consult:dlist")) {
        this.getConsultListByDid();
      }
    },
    // 申请处理框
    showConsultDialog(row) {
      this.dialogFormVisible = true;
      this.dialogStatus = "apply";
      console.log("title:", row.title);
      this.tempConsult.title = row.title;
      this.tempConsult.id = row.id;
      //
    },

    toChart(row) {
      //   直接调用$router.push 实现携带参数的跳转
      this.$router.push({
        path: `/toChat/${row.id}/${row.sid}`,
      });
    },

    /**
     * 获取咨询列表
     */
    getConsultList() {
      this.listLoading = true;
      listConsult(this.listQuery)
        .then((response) => {
          this.consultList = response.data.list;
          this.total = response.data.total;
          this.listLoading = false;
        })
        .catch((res) => {
          this.$message.error("加载咨询列表失败");
        });
    },

    /**
     * 获取咨询列表by医生
     */
    getConsultListByDid() {
      this.listLoading = true;
      this.listQuery.did = this.account.accountId;
      listByDidConsult(this.listQuery)
        .then((response) => {
          this.consultList = response.data.list;
          this.total = response.data.total;
          this.listLoading = false;
        })
        .catch((res) => {
          this.$message.error("加载咨询列表失败");
        });
    },

    updateConsultStatusTwo(row) {
      this.statusQuery.status = 2;
      this.statusQuery.id = row.id;

      this.updateConsultStatus();
      this.dialogFormVisible = false;
    },
    updateConsultStatusThree() {
      this.statusQuery.status = 3;
      this.statusQuery.id = row.id;

      this.updateConsultStatus();
      this.dialogFormVisible = false;
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

    //下载模板
    downTemp(){
    window.location.href = "http://localhost:8080/consult/upload/downLoadTemplateExcel";
    },

    uploadExcel(){
      this.showUploadDialog = true
    },
    /**
     * 改变每页数量
     * @param size 页大小
     */
    handleSizeChange(size) {
      this.listQuery.page = 1;
      this.listQuery.size = size;
      this.getConsultList();
    },
    /**
     * 改变页码
     * @param page 页号
     */
    handleCurrentChange(page) {
      this.listQuery.page = page;
      this.getConsultList();
    },
    /**
     * 表格序号
     * @param index 数据下标
     * @returns 表格序号
     */
    getTableIndex(index) {
      return (this.listQuery.page - 1) * this.listQuery.size + index + 1;
    },
    // /**
    //  * 显示新增咨询对话框
    //  */
    // showAddConsultDialog() {
    //   this.dialogFormVisible = true
    //   this.dialogStatus = 'add'
    //   this.tempConsult.name = ''
    //   this.tempConsult.id = ''
    //   this.tempConsult.permissionIdList = []
    // },
    // /**
    //  * 显示更新咨询的对话框
    //  * @param index 咨询下标
    //  */
    // showUpdateConsultDialog(index) {
    //   this.dialogFormVisible = true
    //   this.dialogStatus = 'update'
    //   const role = this.consultList[index]
    //   this.tempConsult.name = role.name
    //   this.tempConsult.id = role.id
    //   this.tempConsult.permissionIdList = []
    //   for (let i = 0; i < role.resourceList.length; i++) {
    //     const handleList = role.resourceList[i].handleList
    //     for (let j = 0; j < handleList.length; j++) {
    //       const handle = handleList[j]
    //       this.tempConsult.permissionIdList.push(handle.id)
    //     }
    //   }
    // },
    // /**
    //  * 显示咨询权限的对话框
    //  * @param index 咨询下标
    //  */
    // showConsultDialog(index) {
    //   this.dialogFormVisible = true
    //   this.dialogStatus = 'show'
    //   const role = this.consultList[index]
    //   this.tempConsult.name = role.name
    //   this.tempConsult.id = role.id
    //   this.tempConsult.permissionIdList = []
    //   let resourceList = []
    //   if (role.name === '超级管理员') {
    //     resourceList = this.permissionList
    //   } else {
    //     resourceList = role.resourceList
    //   }
    //   for (let i = 0; i < resourceList.length; i++) {
    //     const handleList = resourceList[i].handleList
    //     for (let j = 0; j < handleList.length; j++) {
    //       const handle = handleList[j]
    //       this.tempConsult.permissionIdList.push(handle.id)
    //     }
    //   }
    // },
    // /**
    //  * 添加新咨询
    //  */
    // addConsult() {
    //   this.$refs.tempConsult.validate(valid => {
    //     if (
    //       valid &&
    //       this.isConsultNameUnique(this.tempConsult.id, this.tempConsult.name)
    //     ) {
    //       this.btnLoading = true
    //       addConsult(this.tempConsult).then(() => {
    //         this.$message.success('添加成功')
    //         this.getConsultList()
    //         this.dialogFormVisible = false
    //         this.btnLoading = false
    //       }).catch(res => {
    //         this.$message.error('添加咨询失败')
    //       })
    //     } else {
    //       console.log('表单无效')
    //     }
    //   })
    // },
    // /**
    //  * 修改咨询
    //  */
    // updateConsult() {
    //   this.$refs.tempConsult.validate(valid => {
    //     if (
    //       valid &&
    //       this.isConsultNameUnique(this.tempConsult.id, this.tempConsult.name)
    //     ) {
    //       this.btnLoading = true
    //       updateConsult(this.tempConsult).then(() => {
    //         this.$message.success('更新成功')
    //         this.getConsultList()
    //         this.dialogFormVisible = false
    //         this.btnLoading = false
    //       }).catch(res => {
    //         this.$message.error('更新咨询失败')
    //       })
    //     } else {
    //       console.log('表单无效')
    //     }
    //   })
    // },
    // /**
    //  * 校验咨询名是否已经存在
    //  * @param id 咨询id
    //  * @param name 咨询名
    //  * @returns {boolean}
    //  */
    // isConsultNameUnique(id, name) {
    //   for (let i = 0; i < this.consultList.length; i++) {
    //     if (this.consultList[i].id !== id && this.consultList[i].name === name) {
    //       this.$message.error('咨询名已存在')
    //       return false
    //     }
    //   }
    //   return true
    // },
    /**
     * 移除咨询
     * @param index 咨询下标
     * @returns {boolean}
     */
    removeConsult(index) {
      this.$confirm("删除该咨询？", "警告", {
        confirmButtonText: "是",
        cancelButtonText: "否",
        type: "warning",
      })
        .then(() => {
          const roleId = this.consultList[index].id;
          removeConsult(roleId)
            .then(() => {
              this.$message.success("删除成功");
              this.getConsultList();
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
     * 判断咨询菜单内的权限是否一个都没选
     * @param index 下标
     * @returns {boolean}
     */
    isMenuNone(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        if (this.tempConsult.permissionIdList.indexOf(handleList[i].id) > -1) {
          return false;
        }
      }
      return true;
    },
    /**
     * 判断咨询菜单内的权限是否全选了
     * @param index 下标
     * @returns {boolean}
     */
    isMenuAll(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        if (this.tempConsult.permissionIdList.indexOf(handleList[i].id) < 0) {
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
        this.addUnique(handleList[i].id, this.tempConsult.permissionIdList);
      }
    },
    /**
     * checkbox全部取消选中
     * @param index 下标
     */
    cancelAll(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        const idIndex = this.tempConsult.permissionIdList.indexOf(
          handleList[i].id
        );
        if (idIndex > -1) {
          this.tempConsult.permissionIdList.splice(idIndex, 1);
        }
      }
    },
    /**
     * 本方法会在勾选状态改变之后触发
     * @param item 选项
     * @param index 对应下标
     */
    handleChecked(item, index) {
      if (this.tempConsult.permissionIdList.indexOf(item.id) > -1) {
        // 选中事件
        // 如果之前未勾选本权限,现在勾选完之后,tempConsult里就会包含本id
        // 那么就要将必选的权限勾上
        this.makePermissionChecked(index);
      } else {
        // 取消选中事件
        this.cancelAll(index);
      }
    },
    /**
     * 将咨询菜单必选的权限勾上（这里并没有做必选的数据库字段）
     * @param index 权限对应下标
     */
    makePermissionChecked(index) {
      const handleList = this.permissionList[index].handleList;
      for (let i = 0; i < handleList.length; i++) {
        this.addUnique(handleList[i].id, this.tempConsult.permissionIdList);
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
