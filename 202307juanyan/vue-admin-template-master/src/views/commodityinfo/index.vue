<template>
    <div class="app-container">
  
      <!--搜索表单-->
      <el-form :inline="true" :model="searchcommodity" class="demo-form-inline">
        <el-form-item label="商品名">
          <el-input
            v-model="searchcommodity.comName"
            placeholder="请输入商品名"
          ></el-input>
        </el-form-item>

        <el-form-item label="主条形码">
          <el-input
            v-model="searchcommodity.comCode1"
            placeholder="请输入主条形码"
          ></el-input>
        </el-form-item>

        <el-form-item label="副条形码">
          <el-input
            v-model="searchcommodity.comCode2"
            placeholder="请输入副条形码"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="商品大类">
          <el-select v-model="searchcommodity.comType1" placeholder="请选择">
            <el-option
              v-for="item in typeList"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId">
            </el-option>
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
        <el-button type="primary" size="medium" @click="dialogVisible = true; commodity = { comName: ''};" >+ 新增商品</el-button>
      </el-row>
  
      <!--添加数据对话框表单-->
      <el-dialog ref="form" title="新增商品" :visible.sync="dialogVisible" width="30%">
        <el-form ref="form" :model="commodity" label-width="80px" size="mini">
          <el-form-item label="商品名">
            <el-input v-model="commodity.comName"></el-input>
          </el-form-item>
          <el-form-item label="主条形码">
            <el-input v-model="commodity.comCode1"></el-input>
          </el-form-item>
          <el-form-item label="副条形码">
            <el-input v-model="commodity.comCode2"></el-input>
          </el-form-item>
          <el-form-item label="商品单位">
            <el-input v-model="commodity.comUnit"></el-input>
          </el-form-item>
          <el-form-item label="商品大类">
            <el-select v-model="commodity.comType1" placeholder="请选择">
              <el-option
              v-for="item in typeList"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品小类">
            <el-select v-model="commodity.comType2" placeholder="请选择">
              <el-option
              v-for="item in typeList"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId">
              </el-option>
            </el-select>
          </el-form-item>


          <el-form-item label="商品售价">
            <el-input v-model="commodity.comRetail"></el-input>
          </el-form-item> 
          
          <el-form-item label="商品成本">
            <el-input v-model="commodity.comCost"></el-input>
          </el-form-item>

          <el-form-item label="商品折扣">
            <el-input v-model="commodity.comDiscount"></el-input>
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
          <el-table-column  prop="comId"  label="id" width="55" align="center"></el-table-column>
          <el-table-column  prop="comName"  label="商品名"  align="center"></el-table-column>
          <el-table-column  prop="comCode1"  label="主条形码"  align="center"></el-table-column>
          <el-table-column  prop="comCode2"  label="副条形码"  align="center"></el-table-column>
          <el-table-column  prop="comUnit"  label="商品单位"  align="center"></el-table-column>
          <el-table-column  prop="comType1"  label="商品大类"  align="center"></el-table-column>
          <el-table-column  prop="comType2"  label="商品小类"  align="center"></el-table-column>
          <el-table-column  prop="comRetail"  label="商品售价"  align="center"></el-table-column>
          <el-table-column  prop="comCost"  label="商品成本"  align="center"></el-table-column>
          <el-table-column  prop="comDiscount"  label="商品折扣"  align="center"></el-table-column>
          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <el-button type="primary" size="small" @click="update(scope.row.id)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
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
  import { pageCom,addCom,updateCom,deleteCom,selectCom } from "@/api/commodity.js";
  import { pageComType } from "@/api/commodityType.js";
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
        searchcommodity: {
          comName: "",
          provider: "",
          isPayment:""
        },
 
        typeList: [],
        beginTime: "",
        endTime: "",
        creationDate: "",
        modifyDate:"",
        createdBy:"",
        commodity:{
        },
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
      this.page2(); //当页面加载完成后，发送异步请求，获取数据
      pageComType().then((result) => {
        this.typeList = result.data.data;
      });
    },
    
    methods: {

      // 查询分页数据
      page2() {
        pageCom(
          this.searchcommodity.comName,
          this.searchcommodity.comCode1,
          this.searchcommodity.comCode2,
          this.searchcommodity.comType1,
          this.currentPage,
          this.pageSize
        ).then((res) => {
          console.log(res);
          this.totalCount = parseInt(res.data.data.total);
          if(this.totalCount===0) this.$message({ message: "暂无数据", type: "warning" });
          console.log(res.data.data.rows.rows.length);
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
        this.page2();
  
      },
  
      clear(){
        this.searchcommodity = {comName: "", comCode1: "",comCode2:"", comType1:""};
        this.page2();
      },
      // 添加数据
      add() {
        let operator;
  
        if (this.commodity.id) {
          //修改
          operator = update(this.commodity);
        } else {
          operator = add(this.commodity);
        }
  
        operator.then((resp) => {
          if (resp.data.code == "1") {
            this.dialogVisible = false;
            this.page2();
            this.$message({ message: "恭喜你，保存成功", type: "success" });
            this.commodity = { comName:"" };
          } else {
            this.$message.error(resp.data.msg);
          }
        }).catch((error) => this.$message.error(error.response.data.error+"修改失败！"));
      },
      update(id) {
        //1. 打开窗口
        this.dialogVisible = true;
        //2. 发送请求
  
        selectById(id).then((result) => {
          if (result.data.code == 1) {
            this.commodity = result.data.data;
            this.commodity;
          }
        });
      },
  
      
      //分页
      handleSizeChange(val) {
        // 重新设置每页显示的条数
        this.pageSize = val;
        this.page2();
      },
  
      handleCurrentChange(val) {
        // 重新设置当前页码
        this.currentPage = val;
        this.page2();
      },
  
  
      //删除商品信息
      deleteById(id){
        this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
            //2. 发送AJAX请求
            deleteById(id).then((resp) => {
              if (resp.data.code == 1) {
                //删除成功
                this.$message.success("恭喜你，删除成功");
                this.page2();
              } else {
                this.$message.error(resp.data.msg);
              }
            });
        }).catch(() => {
            //用户点击取消按钮
            this.$message.info("已取消删除");
          });
      },
  
  
      // 批量删除商品信息
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
              this.selectedIds[i] = this.multipleSelection[i].id;
            }
  
            //2. 发送AJAX请求
            deleteById(this.selectedIds).then((resp) => {
              if (resp.data.code == "1") {
                //删除成功
                this.$message.success("恭喜你，删除成功");
                this.page2();
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
      creationDate(val) {
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
  