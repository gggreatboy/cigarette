<template>
  <div class="app-container">

    <!--搜索表单-->
    <el-form :inline="true" :model="searchPayment" class="demo-form-inline">
      <el-form-item label="订单号">
        <el-select v-model="searchPayment.billBillCode" filterable placeholder="请选择" style="width:100%">
            <el-option v-for="item in billList" :key="item.value" :label="item.billCode" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="支付方式">
        <el-select v-model="searchPayment.payStatus" style="width: 120px; margin-left: 20px" placeholder="请选择">
          <el-option label="全款支付" value="1"></el-option>
          <el-option label="分期支付" value="2"></el-option>
          <el-option label="货到付款" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建者">
        <el-select v-model="searchPayment.userId" filterable placeholder="请选择" style="width:100%">
            <el-option v-for="item in userList" :key="item.value" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="支付渠道">
        <el-select v-model="searchPayment.payWay" style="width: 150px; margin-left: 20px" placeholder="请选择">
          <el-option label="支付宝" value="1"></el-option>
          <el-option label="微信" value="2"></el-option>
          <el-option label="银联" value="3"></el-option>
          <el-option label="BTC/ETH" value="4"></el-option>
          <el-option label="数字人民币" value="5"></el-option>
          <el-option label="现金" value="6"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
         <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>



    <!--按钮-->
    <el-row>
      <el-button type="danger" size="medium" @click="deleteByIds">- 批量删除</el-button>
      <el-button type="primary" size="medium" @click="dialogVisible = true; payment = { billBillCode: ''};" >+ 新增支付单</el-button>
    </el-row>
     <!--添加数据对话框表单-->
     <el-dialog ref="form" title="编辑支付单" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form" :model="payment" label-width="80px" size="mini">
        <el-form-item label="订单号">
          <el-select v-model="payment.billBillCode" filterable placeholder="请选择" style="width:100%">
            <el-option v-for="item in billList" :key="item.value" :label="item.billCode" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="支付方式">
        <el-select v-model="payment.payStatus" placeholder="请选择">
          <el-option label="全款支付" value="1"></el-option>
          <el-option label="分期支付" value="2"></el-option>
          <el-option label="货到付款" value="3"></el-option>
        </el-select>
      </el-form-item>
        <el-form-item label="创建者">
          <el-select v-model="payment.userId" filterable placeholder="请选择" style="width:100%">
            <el-option v-for="item in userList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="支付渠道">
        <el-select v-model="payment.payWay"  placeholder="请选择">
          <el-option label="支付宝" value="1"></el-option>
          <el-option label="微信" value="2"></el-option>
          <el-option label="银联" value="3"></el-option>
          <el-option label="BTC/ETH" value="4"></el-option>
          <el-option label="数字人民币" value="5"></el-option>
          <el-option label="现金" value="6"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="总价">
          <el-input v-model="payment.billTotalPrice"></el-input>
        </el-form-item>
        <el-form-item label="支付时间">
          <el-input v-model="payment.createdTime" placeholder="支付时间不可修改！" :disabled="true" >{{ cleardate(payment) }}</el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="add">提交</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <br>
    <!--表格-->
    <template>
      <el-table :data="tableData" style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"  align="center"></el-table-column>
        <el-table-column  prop="pid"  label="pid" width="55" align="center"></el-table-column>
        <el-table-column  prop="billBillCode"  label="订单号"  align="center"></el-table-column>
        <el-table-column align="center" label="支付方式">
          <template slot-scope="scope">
            <span style="margin-right: 10px" v-if="scope.row.payStatus == 1">全款支付</span>
            <span style="margin-right: 10px" v-if="scope.row.payStatus == 2">分期付款</span>
            <span style="margin-right: 10px" v-if="scope.row.payStatus == 3">货到付款</span>
          </template>
        </el-table-column>
        <el-table-column  prop="userId"  label="创建者ID"  align="center"></el-table-column>
        <el-table-column  prop="userId"  label="创建者姓名"  align="center">
          <template slot-scope="scope">
            <template v-for="item in userList">
              <span style="margin-right: 10px" v-if="item.id === scope.row.userId" :key="item.id">{{ item.name }}</span>
            </template>
          </template>
        </el-table-column>
        <el-table-column  prop="billTotalPrice"  label="总价"  align="center"></el-table-column>
        <el-table-column align="center" label="支付渠道">
          <template slot-scope="scope">
            <span style="margin-right: 10px" v-if="scope.row.payWay == 1">支付宝</span>
            <span style="margin-right: 10px" v-if="scope.row.payWay == 2">微信</span>
            <span style="margin-right: 10px" v-if="scope.row.payWay == 3">银联</span>
            <span style="margin-right: 10px" v-if="scope.row.payWay == 4">BTC/ETH</span>
            <span style="margin-right: 10px" v-if="scope.row.payWay == 5">数字人民币</span>
            <span style="margin-right: 10px" v-if="scope.row.payWay == 6">现金</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="支付时间">
          <template slot-scope="scope">
            {{ scope.row.createdTime}}
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="update(scope.row.pid)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteById(scope.row.pid)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>

    <!--分页工具条-->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :background="background"
      :current-page="currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="5"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount">
    </el-pagination>
  </div>
</template>

<script>

import { getToken } from '@/utils/auth';

export default {
  data() {
    return {
      background: true,
      // 每页显示的条数
      pageSize: 5,
      // 总记录数
      totalCount: 100,
      // 当前页码
      currentPage: 1,
      // 添加数据对话框是否展示的标记
      dialogVisible: false,
      // 品牌模型数据
      searchPayment: {
        billBillCode: "",
        payStatus:"",
        userId: "",
        payWay:""
      },
      payment: {
        billBillCode: "",
      },
      vipList: [],
      userList:[],
      billList:[],
      beginTime: "",
      endTime: "",
      createdTime: "",
      
      // 被选中的id数组
      selectedIds: [],
      // 复选框选中数据集合
      multipleSelection: [],
      // 表格数据
      tableData: [],
      token: {token: getToken()}
    };
  },

  mounted() {
    this.page3(); //当页面加载完成后，发送异步请求，获取数据
    findAll().then((result) => {
      this.vipList = result.data.data;
    });
    findAll3().then((result) => {
      this.userList = result.data.data.rows.rows;
    });
    findAll1().then((result) => {
      this.billList = result.data.data.rows.rows;
    });
  },
  
  methods: {
    cleardate(payment){
      payment.createdTime=null;
      return null;
    },
    // 查询分页数据
    page3() {
      page3(
        this.searchPayment.billBillCode,
        this.searchPayment.payStatus,
        this.searchPayment.userId,
        this.searchPayment.payWay,
        this.currentPage,
        this.pageSize
      ).then((res) => {
        this.totalCount = parseInt(res.data.data.total);
        if(this.totalCount===0) this.$message({ message: "暂无数据", type: "warning" });
        this.tableData = res.data.data.rows.rows.length ? res.data.data.rows.rows : [res.data.data.rows.rows]
        console.log(this.tableData)
      });
    },

    // 复选框选中后执行的方法
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 查询方法
    onSubmit() {
      this.currentPage = 1;
      this.page3();
    },

    clear(){
      this.searchPayment = {billBillCode: "",payStatus:"", userId: "",payWay:""};
      this.beginTime = "",
      this.endTime = "";
      this.createdTime = "";
      this.page3();
    },
    // 添加数据
    add() {
      let operator;

      if (this.payment.pid) {
        //修改
        operator = update(this.payment);
      } else {
        operator = add(this.payment);
      }

      operator.then((resp) => {
        if (resp.data.code == "1") {
          this.dialogVisible = false;
          this.page3();
          this.$message({ message: "恭喜你，保存成功", type: "success" });
          this.user = {billBillCode: "" };
        } else {
          this.$message.error(resp.data.msg);
        }
      }).catch((error) => this.$message.error(error.response.data.error+"输入格式错误，请检查！"));
    },
    update(pid) {
      //1. 打开窗口
      this.dialogVisible = true;
      //2. 发送请求

      selectById(pid).then((result) => {
        if (result.data.code == 1) {
          this.payment = result.data.data;
          this.payment;
        }
      });
    },

    
    //分页
    handleSizeChange(val) {
      // 重新设置每页显示的条数
      this.pageSize = val;
      this.page3();
    },

    handleCurrentChange(val) {
      // 重新设置当前页码
      this.currentPage = val;
      this.page3();
    },


    //删除员工信息
    deleteById(pid){
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
          //2. 发送AJAX请求
          deleteById(pid).then((resp) => {
            if (resp.data.code == 1) {
              //删除成功
              this.$message.success("恭喜你，删除成功");
              this.page3();
            } else {
              this.$message.error(resp.data.msg);
            }
          });
      }).catch(() => {
          //用户点击取消按钮
          this.$message.info("已取消删除");
        });
    },
    // 批量删除员工信息
    deleteByIds() {
      // 弹出确认提示框
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
          //用户点击确认按钮
          //1. 创建id数组, 从 this.multipleSelection 获取即可
          for (let i = 0; i < this.multipleSelection.length; i++) {
            this.selectedIds[i] = this.multipleSelection[i].pid;
          }

          //2. 发送AJAX请求
          deleteById(this.selectedIds).then((resp) => {
            if (resp.data.code == "1") {
              //删除成功
              this.$message.success("恭喜你，删除成功");
              this.page3();
            } else {
              this.$message.error(resp.data.msg);
            }
          });
      }).catch(() => {
          //用户点击取消按钮
          this.$message.info("已取消删除");
        });
    },
  },


  watch: {
    entrydate(val) {
      if (val && val.length >= 2) {
        this.beginTime = val[0];
        this.endTime = val[1];
      } else {
        this.beginTime = "";
        this.endTime = "";
      }
    },
  },
};
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>
