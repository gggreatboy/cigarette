<!--  -->
<template>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
        label-position="left">

        <div class="title-container">
            <h3 class="title">卷烟零售管理系统-管理员登录</h3>
        </div>

        <el-form-item prop="userName">
            <span class="svg-container">
                <svg-icon icon-class="user" />
            </span>
            <el-input ref="username" v-model="loginForm.userName" placeholder="Username" name="请输入收银员用户名" type="text"
                tabindex="1" auto-complete="on" />
        </el-form-item>

        <el-form-item prop="userPassword">
            <span class="svg-container">
                <svg-icon icon-class="password" />
            </span>
            <el-input :key="passwordType" ref="password" v-model="loginForm.userPassword" :type="passwordType"
                placeholder="请输入密码" name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
            <span class="show-pwd" @click="showPwd">
                <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
            </span>
        </el-form-item>

        <el-form-item label="当前时间:">
            <span style="text-align: center;display:block;">{{ newDate }}</span>
        </el-form-item>
        <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:20px;"
            @click.native.prevent="handleLogin">Admin-Login</el-button>
        <el-button :loading="loading" style="width:100%;margin-left: 0;margin-bottom:20px;"
            @click.native.prevent="resetForm">Reset</el-button>
    </el-form>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
    //import引入的组件需要注入到对象中才能使用
    name: 'Login',
    components: {},
    data() {
        //用户名校验规则
        const validateUsername = (rule, value, callback) => {
            if (!validUsername(value)) {
                callback(new Error('请输入正确的用户名'))
            } else {
                callback()
            }
        }

        //用户名校验规则
        const validatePassword = (rule, value, callback) => {
            if (value.length < 6) {
                callback(new Error('密码长度至少为6位'))
            } else {
                callback()
            }
        }
        //这里存放数据
        return {
            newDate: new Date(),
            loginForm: {
                userName: 'jinyong',
                userPassword: '123456'
            },
            activeName: 'first',//默认显示登录页面

            loginRules: {
                username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                password: [{ required: true, trigger: 'blur', validator: validatePassword }]
            },
            loading: false,
            passwordType: 'password',
            redirect: undefined
        };
    },
    //监听属性 类似于data概念
    computed: {},
    //监控data中的数据变化
    watch: {},
    //方法集合
    methods: {
        toReg() {
            this.activeName = "second"
        },
        toUser() {
            this.activeName = "first"
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
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
        //登录方法
        handleLogin() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true

                    //调用登录后端接口
                    login(this.loginForm).then((result) => {
                        console.log(result)
                        if (result.data.code == 1) {
                            setToken(result.data.data);
                            console.log('login success');
                            this.$router.push('/');
                        } else {
                            this.$message.error(result.data.msg);
                            this.loading = false
                        }
                    });
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        },
        handleClick() { }
    },
    //生命周期 - 创建完成（可以访问当前this实例）
    created() {

    },
    //生命周期 - 挂载完成（可以访问DOM元素）
    mounted() {
        let that = this
        this.timer = setInterval(function () {
            that.newDate = new Date().toLocaleString()
        })
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
    .login-container .el-input input {
        color: $cursor;
    }
}

/* reset element-ui css */
.login-container {
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

.login-container {
    min-height: 100%;
    width: 100%;
    overflow: hidden;
    position: relative;

    .login-form {
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