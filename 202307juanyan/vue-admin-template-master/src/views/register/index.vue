
<template>
    <div class="reg-container">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="reg-form" auto-complete="on" label-position="left">
            <div class="title-container">
                <h3 class="title">卷烟零售管理系统-用户注册</h3>
            </div>

            <el-form-item label="" prop="userName">
                <span class="svg-container">
                    <svg-icon icon-class="user" />
                </span>
                <el-input type="text" autocomplete="off" v-model="ruleForm.name"
                    placeholder="请输入收银员姓名"></el-input></el-form-item>

            <el-form-item label="" prop="userAge">
                <span class="svg-container">
                    <svg-icon icon-class="tree" />
                </span>
                <el-input type="text" autocomplete="off" v-model="ruleForm.userAge"
                    placeholder="请输入收银员年龄"></el-input></el-form-item>

            <el-form-item label="" prop="userBanci">
                <span class="svg-container">
                    <svg-icon icon-class="tree" />
                </span>
                <el-select v-model="userBanci" filterable placeholder="请选择对应班次:">
                  <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
            </el-form-item>

                
            <el-form-item label="" prop="userPassword">
                <span class="svg-container">
                    <svg-icon icon-class="password" />
                </span>
                <el-input type="password" autocomplete="off" v-model="ruleForm.userPassword"
                    placeholder="请输入密码"></el-input></el-form-item>
            <el-form-item label="" prop="checkPass">
                <span class="svg-container">
                    <svg-icon icon-class="password" />
                </span>
                <el-input type="password" autocomplete="off" v-model="ruleForm.checkPass"
                    placeholder="请再次输入密码"></el-input></el-form-item>

            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:20px;"
                @click.native.prevent="handleReg">Register</el-button>
            <el-button :loading="loading" style="width:100%;margin-left: 0;margin-bottom:20px;"
                @click.native.prevent="resetForm">Reset</el-button>

        </el-form>
    </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
    //import引入的组件需要注入到对象中才能使用
    components: {},
    data() {
        //这里存放数据
        var validatePass = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请输入密码"));
            } else {
                if (this.ruleForm.checkPass !== "") {
                    this.$refs.ruleForm.validateField("checkPass");
                }
                callback();
            }
        };

        var validatePass2 = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请再次输入密码"));
            } else if (value !== this.ruleForm.pass) {
                callback(new Error("两次输入密码不一致!"));
            } else {
                callback();
            }
        };
        return {
            activeName: "second",
            options: [{
              value: '101',
              label: '工作日上午'
            }, {
              value: '102',
              label: '工作日下午'
            }, {
              value: '103',
              label: '双休日上午'
            }, {
              value: '104',
              label: '双休日下午'
            }, {
              value: '105',
              label: '节假日上午'
            }, {
              value: '106',
              label: '节假日下午'
            }],            
            ruleForm: {
                userName: "",
                userPassword: "",
                userAge: "",
                userBanci: "",
                checkPass: "",
                userCategory: 1,
            },
            rules: {
                name: [
                    { required: true, message: "请输入您的名称", trigger: "blur" },
                    { min: 2, max: 5, message: "长度在 2 到 5 个字符", trigger: "blur" },
                ],
                pass: [{ required: true, validator: validatePass, trigger: "blur" }],
                checkPass: [
                    { required: true, validator: validatePass2, trigger: "blur" },
                ],
            },
        };
    },
    //监听属性 类似于data概念
    computed: {},
    //监控data中的数据变化
    watch: {},
    //方法集合
    methods: {
        toUser() {
            this.activeName = "first"
        },
        //展示密码
        showPwd() {
            if (this.passwordType === 'password') {
                this.passwordType = ''
            } else {
                this.passwordType = 'password'
            }
            this.$nextTick(() => {
                this.$refs.password.focus()
            })
        },
        handleReg(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$message({
                        type: "success",
                        message: "注册成功",
                    });
                    // this.activeName: 'first',
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },

        resetForm(formName) {
            this.$refs[formName].resetFields();
        },

    },
    //生命周期 - 创建完成（可以访问当前this实例）
    created() {

    },
    //生命周期 - 挂载完成（可以访问DOM元素）
    mounted() {

    },
    beforeCreate() { }, //生命周期 - 创建之前
    beforeMount() { }, //生命周期 - 挂载之前
    beforeUpdate() { }, //生命周期 - 更新之前
    updated() { }, //生命周期 - 更新之后
    beforeDestroy() { }, //生命周期 - 销毁之前
    destroyed() { }, //生命周期 - 销毁完成
    activated() { }, //如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .reg-container .el-input input {
        color: $cursor;
    }
}

/* reset element-ui css */
.reg-container {
    .el-input {
        display: inline-block;
        height: 47px;
        width: 85%;

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: $light_gray;
            height: 47px;
            caret-color: $cursor;

            &:-webkit-autofill {
                box-shadow: 0 0 0px 1500px $bg inset !important;
                -webkit-text-fill-color: $cursor !important;
            }
        }
    }

    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        background: rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        color: #454545;
    }
}
</style>
<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.reg-container {
    min-height: 100%;
    width: 100%;
    overflow: hidden;
    position: relative;

    .reg-form {
        position: relative;
        width: 520px;
        max-width: 100%;
        padding: 55px 55px 55px 55px;
        margin: 0 auto;
        overflow: hidden;
    }

    .tips {
        font-size: 14px;
        color: #fff;
        margin-bottom: 10px;

        span {
            &:first-of-type {
                margin-right: 16px;
            }
        }
    }

    .svg-container {
        padding: 6px 5px 6px 15px;
        color: $dark_gray;
        vertical-align: middle;
        width: 30px;
        display: inline-block;
    }

    .title-container {
        position: relative;

        .title {
            font-size: 26px;
            color: $light_gray;
            margin: 0px auto 40px auto;
            text-align: center;
            font-weight: bold;
            font-family: '楷体';
        }
    }

    .show-pwd {
        position: absolute;
        right: 10px;
        top: 7px;
        font-size: 16px;
        color: $dark_gray;
        cursor: pointer;
        user-select: none;
    }
}
</style>
